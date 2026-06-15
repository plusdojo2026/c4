package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;

public class AccountDAO {
    // ログインチェック (社員番号とパスワードで検索)
    public Account loginCheck(int id, String password) {
        Connection conn = null;
        Account account = null;

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT id, name, permissions_id FROM accounts WHERE id = ? AND password = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, id);
            pStmt.setString(2, password);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                account = new Account(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("permissions_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return account;
    }

    // アカウント作成 (登録)
    public boolean insert(Account account) {
        Connection conn = null;
        boolean result = false;

        try {
            conn = DBConnection.getConnection();

            String sql = "INSERT INTO accounts (name, birthday, password, permissions_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, account.getName());
            pStmt.setString(2, account.getBirthday());
            pStmt.setString(3, account.getPassword());
            pStmt.setInt(4, account.getPermissionsId());

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

    // パスワード再設定 (社員番号と生年月日が一致した場合に更新)
    public boolean updatePassword(int id, String birthday, String newPassword) {
        Connection conn = null;
        boolean result = false;

        try {
            conn = DBConnection.getConnection();

            String sql = "UPDATE accounts SET password = ? WHERE id = ? AND birthday = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, newPassword);
            pStmt.setInt(2, id);
            pStmt.setString(3, birthday);

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