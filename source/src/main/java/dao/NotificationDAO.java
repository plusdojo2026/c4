package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Notification;

public class NotificationDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/c4?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
    private static final String USER = "root";
    private static final String PASS = "password";

    // ユーザー画面に表示する有効な通知一覧を取得します。
    public List<Notification> selectActiveNotifications() {
        Connection conn = null;
        List<Notification> notificationList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

            String sql = "SELECT id, message, created_at, is_read, is_deleted FROM notifications WHERE is_deleted = 0 ORDER BY id DESC";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification(
                    rs.getInt("id"),
                    rs.getString("message"),
                    rs.getObject("created_at", LocalDateTime.class),
                    rs.getInt("is_read"),
                    rs.getInt("is_deleted")
                );
                notificationList.add(notification);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return notificationList;
    }

    // 新しい通知をデータベースに登録します。
    public boolean insert(Notification notification) {
        Connection conn = null;
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            
            // is_read, is_deleted, created_at はDBのデフォルト値に任せる
            String sql = "INSERT INTO notifications (message) VALUES (?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, notification.getMessage());

            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException | ClassNotFoundException e) { e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
        return result;
    }

    // 指定されたIDの通知を「既読」状態（is_read = 1）に更新します。
    public boolean markAsRead(int id) {
        Connection conn = null;
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            String sql = "UPDATE notifications SET is_read = 1 WHERE id = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, id);

            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException | ClassNotFoundException e) { e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
        return result;
    }
}