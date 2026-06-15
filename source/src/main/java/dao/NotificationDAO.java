package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Notification;

public class NotificationDAO {
	// ユーザー画面に表示する有効な通知一覧を取得します。
	public List<Notification> selectActiveNotifications() {
		Connection conn = null;
		List<Notification> notificationList = new ArrayList<>();
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "SELECT id, message, created_at, is_read, is_deleted FROM notifications WHERE is_deleted = 0 ORDER BY id DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				Notification notification = new Notification(
						rs.getInt("id"),
						rs.getString("message"),
						rs.getObject("created_at", LocalDateTime.class),
						rs.getInt("is_read"),
						rs.getInt("is_deleted"));
				notificationList.add(notification);
			}
        } catch (SQLException e) {
		}
		return notificationList;
	}
	
	// 新しい通知をデータベースに登録します。
	public boolean insert(Notification notification) {
		Connection conn = null;
		boolean result = false;
		try {
			conn = DBConnection.getConnection();
			
			// is_read, is_deleted, created_at はDBのデフォルト値に任せる
			String sql = "INSERT INTO notifications (message) VALUES (?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, notification.getMessage());
			
			if (pStmt.executeUpdate() == 1) {
				result = true;
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
		return result;
	}
	
	// すべての未読通知を一括で「既読」状態（is_read = 1）に更新します。
	public boolean markAllAsRead() {
		Connection conn = null;
		boolean result = false;
		try {
			conn = DBConnection.getConnection();
			
			// is_read = 0 (未読) かつ is_deleted = 0 (未削除) のものを全て更新
			String sql = "UPDATE notifications SET is_read = 1 WHERE is_read = 0 AND is_deleted = 0";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// 実行（更新件数が0件でもエラーではないため、例外が発生しなければ成功とする）
			pStmt.executeUpdate();
			result = true;
			
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
		return result;
	}
}