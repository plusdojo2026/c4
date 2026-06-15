package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.StockMovement;

public class StockMovementDAO {
    // 指定された日付が「通知日（notify_at）」に設定されている入出庫履歴を検索して取得します。
    public List<StockMovement> selectByNotifyDate(java.time.LocalDate targetDate) {
        Connection conn = null;
        List<StockMovement> movementList = new java.util.ArrayList<>();

        try {
            conn = DBConnection.getConnection();

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
        } catch (SQLException e) {
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
            conn = DBConnection.getConnection();

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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return result;
    }
}