package dao;

import model.Product;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品資料存取層
 * 用途：
 *   - 提供 Product 的 CRUD 操作
 *   - 查詢商品列表、依分類查詢、依賣家查詢
 */
public class ProductDAO {

    // 🔵 新增商品
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (seller_id, name, description, category_id, price, stock) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, product.getSellerId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setInt(4, product.getCategoryId());
            stmt.setDouble(5, product.getPrice());
            stmt.setInt(6, product.getStock());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 查詢單一商品（依 ID）
    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔵 查詢所有商品
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // 🔵 查詢依分類
    public List<Product> findByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // 🔵 查詢依賣家
    public List<Product> findBySeller(int sellerId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE seller_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // 🔵 更新商品
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, category_id = ?, price = ?, stock = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getCategoryId());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getStock());
            stmt.setInt(6, product.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 刪除商品
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 工具：將 ResultSet 封裝為 Product 物件
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setSellerId(rs.getInt("seller_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setPrice(rs.getDouble("price"));
        product.setStock(rs.getInt("stock"));
        return product;
    }
}
