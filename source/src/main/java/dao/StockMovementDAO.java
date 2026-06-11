package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.StockMovement;

public class StockMovementDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/c4?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
    private static final String USER = "root";
    private static final String PASS = "password";

    // 指定された日付が「通知日（notify_at）」に設定されている入出庫履歴を検索して取得します。
    public List<StockMovement> selectByNotifyDate(java.time.LocalDate targetDate) {
        Connection conn = null;
        List<StockMovement> movementList = new java.util.ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            String sql = "SELECT jancode, stock_id, reason, quantity, received_at, notify_at FROM stock_movements WHERE notify_at = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1, targetDate);

            java.sql.ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                StockMovement movement = new StockMovement(
                    rs.getString("jancode"),
                    rs.getInt("stock_id"),
                    rs.getString("reason"),
                    rs.getInt("quantity"),
                    rs.getObject("received_at", java.time.LocalDate.class),
                    rs.getObject("notify_at", java.time.LocalDate.class)
                );
                movementList.add(movement);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return movementList;
    }

    // 商品の入出庫が発生した際に、その履歴と通知日を記録します。
    public boolean insert(StockMovement movement) {
        Connection conn = null;
        boolean result = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            String sql = "INSERT INTO stock_movements (jancode, stock_id, reason, quantity, received_at, notify_at) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, movement.getJancode());
            pStmt.setInt(2, movement.getStockId());
            pStmt.setString(3, movement.getReason());
            pStmt.setInt(4, movement.getQuantity());
            pStmt.setObject(5, movement.getReceivedAt());
            pStmt.setObject(6, movement.getNotifyAt());

            if (pStmt.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return result;
    }
}