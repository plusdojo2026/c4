package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Stock;

public class StockDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/c4?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
    private static final String USER = "root";
    private static final String PASS = "password";

    public List<Stock> selectAll() {
        Connection conn = null;
        List<Stock> stockList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            String sql = "SELECT id, jancode, stock_quantity, created_at, updated_at, stores FROM stocks ORDER BY id DESC";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                Stock stock = new Stock(
                    rs.getInt("id"),
                    rs.getString("jancode"),
                    rs.getInt("stock_quantity"),
                    rs.getObject("created_at", LocalDateTime.class),
                    rs.getObject("updated_at", LocalDateTime.class),
                    rs.getInt("stores")
                );
                stockList.add(stock);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return stockList;
    }

    public boolean insert(Stock stock) {
        Connection conn = null;
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            String sql = "INSERT INTO stocks (jancode, stock_quantity, stores) VALUES (?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, stock.getJancode());
            pStmt.setInt(2, stock.getStockQuantity());
            pStmt.setInt(3, stock.getStores());

            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException | ClassNotFoundException e) { e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
        return result;
    }

    public boolean updateQuantity(String janCode, int newQuantity) {
        Connection conn = null;
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            String sql = "UPDATE stocks SET stock_quantity = ? WHERE jancode = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, newQuantity);
            pStmt.setString(2, janCode);

            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException | ClassNotFoundException e) { e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
        return result;
    }
}