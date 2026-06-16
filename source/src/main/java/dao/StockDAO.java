package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Stock;

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
                    rs.getInt("stores")
                );
                stockList.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return stockList;
    }

    // 新しい商品の在庫枠を初期登録します。
    public boolean insert(Stock stock) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO stocks (jancode, stock_quantity, stores) VALUES (?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, stock.getJancode());
            pStmt.setInt(2, stock.getStockQuantity());
            pStmt.setInt(3, stock.getStores());

            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException e) { e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
        return result;
    }

    // 特定の商品の総在庫数を更新します（入出庫や棚卸し時）。
    public boolean updateQuantity(String janCode, int newQuantity) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE stocks SET stock_quantity = ? WHERE jancode = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, newQuantity);
            pStmt.setString(2, janCode);

            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException e) { e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
        return result;
    }
}