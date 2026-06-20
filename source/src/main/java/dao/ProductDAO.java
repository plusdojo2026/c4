package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductDAO {
    // 登録されている全商品の一覧を取得します。
    public List<Product> selectAll() {
        Connection conn = null;
        List<Product> productList = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT jan_code, product_name, base_product_id, case_quantity, photo_path, duration_days, created_at, updated_at FROM products ORDER BY jan_code ASC";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                    rs.getString("jan_code"),
                    rs.getString("product_name"),
                    rs.getString("base_product_id"),
                    rs.getInt("case_quantity"),
                    rs.getString("photo_path"),
                    rs.getInt("duration_days"),
                    rs.getObject("created_at", LocalDateTime.class),
                    rs.getObject("updated_at", LocalDateTime.class)
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return productList;
    }

    private int insertAndReturnId(Product product) throws SQLException {
        String sql = "INSERT INTO products (jan_code, product_name, base_product_id, case_quantity, photo_path, duration_days) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getJanCode());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getBaseProductId());
            stmt.setInt(4, product.getCaseQuantity());
            stmt.setString(5, product.getPhotoPath());
            stmt.setInt(6, product.getDurationDays());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    // base_product_id を更新（内部用）
    private void updateBaseProductId(int id, int baseId) throws SQLException {
        String sql = "UPDATE products SET base_product_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, baseId);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    // ★ 単品登録（サーブレットから呼ぶ）
    public void insertSingle(String jan, String name, int term, String photo) {
        try {
            Product p = new Product(jan, name, "0", 1, photo, term, null, null);

            int id = insertAndReturnId(p);
            updateBaseProductId(id, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ★ ケース＋バラの2商品を登録（サーブレットから呼ぶ）
    public void insertCasePair(
            String caseJan, String caseName, int caseTerm, int caseQty, String photo,
            String baraJan, String baraName, int baraTerm
    ) {
        try {
            // ① バラ商品（単品）を登録（case_quantity = 1）
            Product bara = new Product(baraJan, baraName, null, 1, photo, baraTerm, null, null);
            int baraId = insertAndReturnId(bara);

            // ② バラ商品の base_product_id を自分自身に更新
            updateBaseProductId(baraId, baraId);

            // ③ ケース商品を登録（case_quantity = caseQty）
            Product kase = new Product(caseJan, caseName, String.valueOf(baraId), caseQty, photo, caseTerm, null, null);
            insert(kase);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 新しい商品を商品マスターに登録します。
    public boolean insert(Product product) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO products (jan_code, product_name, base_product_id, case_quantity, photo_path, duration_days) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, product.getJanCode());
            pStmt.setString(2, product.getProductName());
            pStmt.setString(3, product.getBaseProductId());
            pStmt.setInt(4, product.getCaseQuantity());
            pStmt.setString(5, product.getPhotoPath());
            pStmt.setInt(6, product.getDurationDays());

            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException e) { e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
        return result;
    }

    // 既存の商品情報を更新します（JANコードをキーに書き換え）。
    public boolean update(Product product) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE products SET product_name = ?, base_product_id = ?, case_quantity = ?, photo_path = ?, duration_days = ? WHERE jan_code = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, product.getProductName());
            pStmt.setString(2, product.getBaseProductId());
            pStmt.setInt(3, product.getCaseQuantity());
            pStmt.setString(4, product.getPhotoPath());
            pStmt.setInt(5, product.getDurationDays());
            pStmt.setString(6, product.getJanCode());

            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException e) { e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
        return result;
    }

    // 指定されたJANコードの商品をマスターから削除します。
    public boolean delete(String janCode) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM products WHERE jan_code = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, janCode);
            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException e) { e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
        return result;
    }

    // 商品名またはJANコードによるあいまい検索
    public List<Product> search(String keyword) {
        Connection conn = null;
        List<Product> productList = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();

            // product_name または jan_code に部分一致するデータを取得
            String sql = "SELECT jan_code, product_name, base_product_id, case_quantity, photo_path, duration_days, created_at, updated_at " +
                        "FROM products " +
                        "WHERE product_name LIKE ? OR jan_code LIKE ? " +
                        "ORDER BY jan_code ASC";
            
            PreparedStatement pStmt = conn.prepareStatement(sql);
            
            // "%" でキーワードを囲むことであいまい検索
            String searchKeyword = "%" + keyword + "%";
            pStmt.setString(1, searchKeyword);
            pStmt.setString(2, searchKeyword);
            
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                    rs.getString("jan_code"),
                    rs.getString("product_name"),
                    rs.getString("base_product_id"),
                    rs.getInt("case_quantity"),
                    rs.getString("photo_path"),
                    rs.getInt("duration_days"),
                    rs.getObject("created_at", LocalDateTime.class),
                    rs.getObject("updated_at", LocalDateTime.class)
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return productList;
    }
}