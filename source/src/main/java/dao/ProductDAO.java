package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Product;

public class ProductDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/c4?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
    private static final String USER = "root";
    private static final String PASS = "password";

    // 登録されている全商品の一覧を取得します。
    public List<Product> selectAll() {
        Connection conn = null;
        List<Product> productList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return productList;
    }

    // 新しい商品を商品マスターに登録します。
    public boolean insert(Product product) {
        Connection conn = null;
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            String sql = "INSERT INTO products (jan_code, product_name, base_product_id, case_quantity, photo_path, duration_days) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, product.getJanCode());
            pStmt.setString(2, product.getProductName());
            pStmt.setString(3, product.getBaseProductId());
            pStmt.setInt(4, product.getCaseQuantity());
            pStmt.setString(5, product.getPhotoPath());
            pStmt.setInt(6, product.getDurationDays());

            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException | ClassNotFoundException e) { e.printStackTrace();
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            String sql = "UPDATE products SET product_name = ?, base_product_id = ?, case_quantity = ?, photo_path = ?, duration_days = ? WHERE jan_code = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, product.getProductName());
            pStmt.setString(2, product.getBaseProductId());
            pStmt.setInt(3, product.getCaseQuantity());
            pStmt.setString(4, product.getPhotoPath());
            pStmt.setInt(5, product.getDurationDays());
            pStmt.setString(6, product.getJanCode());

            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException | ClassNotFoundException e) { e.printStackTrace();
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            String sql = "DELETE FROM products WHERE jan_code = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, janCode);
            if (pStmt.executeUpdate() == 1) { result = true; }
        } catch (SQLException | ClassNotFoundException e) { e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }
        return result;
    }
}