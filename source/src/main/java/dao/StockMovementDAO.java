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