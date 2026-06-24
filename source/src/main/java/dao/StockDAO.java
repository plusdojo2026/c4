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
		PreparedStatement checkProductStmt = null;
		ResultSet checkProductRs = null;
		PreparedStatement checkStockStmt = null;
		ResultSet checkStockRs = null;
		PreparedStatement saveStockStmt = null;
		PreparedStatement insertMovementStmt = null;
		ResultSet generatedKeys = null;
		
		try {
			conn = DBConnection.getConnection();
			
			// ケース商品の場合、バラのJANと入数を取得する
			String checkProductSql = "SELECT base_product_id, case_quantity FROM products WHERE jan_code = ?";
			checkProductStmt = conn.prepareStatement(checkProductSql);
			checkProductStmt.setString(1, stock.getJancode());
			checkProductRs = checkProductStmt.executeQuery();
			
			if (checkProductRs.next()) {
				String baseProductId = checkProductRs.getString("base_product_id");
				// 紐づけ先（バラのJAN）が存在する場合はケース商品として扱う
				if (baseProductId != null && !baseProductId.isEmpty()) {
					int caseQuantity = checkProductRs.getInt("case_quantity");
					
					// 登録するJANコードと在庫数をバラのものに書き換える
					stock.setJancode(baseProductId);
					stock.setStockQuantity(stock.getStockQuantity() * caseQuantity);
				}
			}
			
			// トランザクション開始
			conn.setAutoCommit(false);
			
			// すでに同じJANコードの在庫レコードが存在するか確認
			String checkStockSql = "SELECT id FROM stocks WHERE jancode = ? AND stores = ?";
			checkStockStmt = conn.prepareStatement(checkStockSql);
			checkStockStmt.setString(1, stock.getJancode());
			checkStockStmt.setInt(2, stock.getStores());
			checkStockRs = checkStockStmt.executeQuery();
			
			int stockId = 0;
			int stockCount = 0;
			boolean isExisting = false;
			
			if (checkStockRs.next()) {
				// 既に在庫が存在する場合：既存レコードを UPDATE
				stockId = checkStockRs.getInt("id");
				isExisting = true;
				
				String updateStockSql = "UPDATE stocks SET stock_quantity = stock_quantity + ? WHERE id = ?";
				saveStockStmt = conn.prepareStatement(updateStockSql);
				saveStockStmt.setInt(1, stock.getStockQuantity());
				saveStockStmt.setInt(2, stockId);
				stockCount = saveStockStmt.executeUpdate();
			} else {
				// 在庫が存在しない場合：新規 INSERT
				String insertStockSql = "INSERT INTO stocks (jancode, stock_quantity, stores) VALUES (?, ?, ?)";
				saveStockStmt = conn.prepareStatement(insertStockSql, Statement.RETURN_GENERATED_KEYS);
				saveStockStmt.setString(1, stock.getJancode());
				saveStockStmt.setInt(2, stock.getStockQuantity());
				saveStockStmt.setInt(3, stock.getStores());
				stockCount = saveStockStmt.executeUpdate();
				
				// stocks の id を取得
				if (stockCount == 1) {
					generatedKeys = saveStockStmt.getGeneratedKeys();
					if (generatedKeys.next()) {
						stockId = generatedKeys.getInt(1);
					}
				}
			}
			
			// 入出庫履歴(stock_movements)を登録
			int movementCount = 0;
			if (stockId > 0) {
				String insertMovementSql = "INSERT INTO stock_movements (jancode, stock_id, reason, quantity, received_at, notify_at) VALUES (?, ?, ?, ?, ?, ?)";
				insertMovementStmt = conn.prepareStatement(insertMovementSql);
				insertMovementStmt.setString(1, stock.getJancode());
				insertMovementStmt.setInt(2, stockId);
				
				// 理由が空の場合のデフォルト値設定
				String reason = movement.getReason();
				if (reason == null || reason.isBlank()) {
					reason = isExisting ? "入庫" : "初期登録"; 
				}
				insertMovementStmt.setString(3, reason);
				insertMovementStmt.setInt(4, stock.getStockQuantity());
				insertMovementStmt.setObject(5, movement.getReceivedAt());
				insertMovementStmt.setObject(6, movement.getNotifyAt());
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
			
			String insertSql = "INSERT INTO stock_movements (jancode, stock_id, reason, quantity) VALUES (?, ?, ?, ?)";
			insertStmt = conn.prepareStatement(insertSql);
			insertStmt.setString(1, stock.getJancode()); // JANコード
			insertStmt.setInt(2, stock.getId()); // 在庫ID
			insertStmt.setString(3, movement.getReason() != null ? movement.getReason() : "入出庫"); // 理由（空の場合は「入出庫」）
			insertStmt.setInt(4, movement.getQuantity()); // 入出庫数量     
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