package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Stock;
import model.StockMovement;

public class StockDAO {
	// 全ての在庫情報を取得します。
	public List<Stock> selectAll() {
		Connection conn = null;
		List<Stock> stockList = new ArrayList<>();
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "SELECT id, jancode, product_name, stock_quantity, duration_days, stores FROM stocks CROSS JOIN products ON stocks.jancode = products.jan_code ORDER BY id DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				Stock stock = new Stock(
						rs.getInt("id"),
						rs.getString("jancode"),
						rs.getString("product_name"),
						rs.getInt("stock_quantity"),
						rs.getInt("duration_days"),
						rs.getInt("stores"));
				stockList.add(stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return stockList;
	}
	
	// 商品名またはJANコードによる在庫データのあいまい検索
	public List<Stock> search(String keyword) {
		Connection conn = null;
		List<Stock> stockList = new ArrayList<>();
		
		try {
			conn = DBConnection.getConnection();
			
			// productsテーブルと結合し、商品名またはJANコードで絞り込む
			String sql = "SELECT stocks.id, stocks.jancode, products.product_name, stocks.stock_quantity, products.duration_days, stocks.stores "
					+
					"FROM stocks " +
					"JOIN products ON stocks.jancode = products.jan_code " +
					"WHERE products.product_name LIKE ? OR stocks.jancode LIKE ? " +
					"ORDER BY stocks.id DESC";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			String searchKeyword = "%" + keyword + "%";
			pStmt.setString(1, searchKeyword);
			pStmt.setString(2, searchKeyword);
			
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				Stock stock = new Stock(
						rs.getInt("id"),
						rs.getString("jancode"),
						rs.getString("product_name"),
						rs.getInt("stock_quantity"),
						rs.getInt("duration_days"),
						rs.getInt("stores"));
				stockList.add(stock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return stockList;
	}
	
	// 新規在庫登録と同時に入出庫履歴も記録
	public boolean insert(Stock stock, StockMovement movement) {
		Connection conn = null;
		boolean result = false;
		PreparedStatement insertStockStmt = null;
		PreparedStatement insertMovementStmt = null;
		ResultSet generatedKeys = null;
		
		try {
			conn = DBConnection.getConnection();
			// トランザクション開始
			conn.setAutoCommit(false);
			
			// stocksテーブルへ新規登録
			String insertStockSql = "INSERT INTO stocks (jancode, stock_quantity, stores) VALUES (?, ?, ?)";
			// Statement.RETURN_GENERATED_KEYSを使用して自動採番されたIDを取得
			insertStockStmt = conn.prepareStatement(insertStockSql, Statement.RETURN_GENERATED_KEYS);
			insertStockStmt.setString(1, stock.getJancode());
			insertStockStmt.setInt(2, stock.getStockQuantity());
			insertStockStmt.setInt(3, stock.getStores());
			int stockCount = insertStockStmt.executeUpdate();
			
			// 自動採番された stocks の id を取得
			int generatedStockId = 0; // 初期値
			if (stockCount == 1) {
				generatedKeys = insertStockStmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					generatedStockId = generatedKeys.getInt(1);
				}
			}
			
			// 自動採番されたIDが正常に取得できたら、入出庫履歴(stock_movements)を登録
			int movementCount = 0;
			if (generatedStockId > 0) {
				String insertMovementSql = "INSERT INTO stock_movements (jancode, stock_id, reason, quantity, received_at, notify_at) VALUES (?, ?, ?, ?, ?, ?)";
				insertMovementStmt = conn.prepareStatement(insertMovementSql);
				insertMovementStmt.setString(1, stock.getJancode());
				insertMovementStmt.setInt(2, generatedStockId); // 採番されたIDをセット
				
				// 理由が空の場合はデフォルト値「初期登録」をセット
				String reason = movement.getReason();
				if (reason == null || reason.isBlank()) {
					reason = "初期登録";
				}
				insertMovementStmt.setString(3, reason);
				insertMovementStmt.setInt(4, stock.getStockQuantity()); // 初期登録時の数量をそのまま履歴に反映
				insertMovementStmt.setObject(5, movement.getReceivedAt()); // 入庫日
				insertMovementStmt.setObject(6, movement.getNotifyAt()); // 通知日（期限）
				movementCount = insertMovementStmt.executeUpdate();
			}
			
			// 両方のSQLが成功した場合のみコミット
			if (stockCount == 1 && movementCount == 1) {
				conn.commit();
				result = true;
			} else {
				// どちらかが失敗していればロールバック
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback(); // 例外発生時もロールバック
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			// リソース解放とオートコミットの復元
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	// 在庫数の更新と入出庫履歴の記録を同時に行う
	public boolean update(Stock stock, StockMovement movement) {
		Connection conn = null;
		boolean result = false;
		PreparedStatement updateStmt = null;
		PreparedStatement insertStmt = null;
		
		try {
			conn = DBConnection.getConnection();
			// トランザクション開始
			conn.setAutoCommit(false);
			
			// オブジェクトから id と stockQuantity を取り出してSQLにセットする
			String updateSql = "UPDATE stocks SET stock_quantity = ? WHERE id = ?";
			updateStmt = conn.prepareStatement(updateSql);
			updateStmt.setInt(1, stock.getStockQuantity()); // 総在庫数
			updateStmt.setInt(2, stock.getId()); // 在庫ID
			int updateCount = updateStmt.executeUpdate();
			
			String insertSql = "INSERT INTO stock_movements (jancode, stock_id, reason, quantity, received_at, notify_at) VALUES (?, ?, ?, ?, ?, ?)";
			insertStmt = conn.prepareStatement(insertSql);
			insertStmt.setString(1, stock.getJancode()); // JANコード
			insertStmt.setInt(2, stock.getId()); // 在庫ID
			insertStmt.setString(3, movement.getReason() != null ? movement.getReason() : "入出庫"); // 理由（空の場合は「入出庫」）
			insertStmt.setInt(4, movement.getQuantity()); // 入出庫数量     
			insertStmt.setObject(5, movement.getReceivedAt()); // 入庫日
			insertStmt.setObject(6, movement.getNotifyAt()); // 通知日（期限）
			int insertCount = insertStmt.executeUpdate();
			
			// 両方のSQLが成功した場合のみコミット
			if (updateCount == 1 && insertCount == 1) {
				conn.commit();
				result = true;
			} else {
				// どちらかが失敗していればロールバック
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback(); // SQL例外発生時もロールバック
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			//  リソース解放とオートコミットの復元
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}