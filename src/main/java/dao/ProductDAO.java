package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import util.DBUtil;

/**
 * 商品資料存取層
 * 用途：
 *   - 提供 Product 的 CRUD 操作
 *   - 查詢商品列表、依分類查詢、依賣家查詢
 */
public class ProductDAO {

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

	// 🔵 查詢依賣家
	public List<Product> getBySellerId(int sellerId) {
	    List<Product> products = new ArrayList<>();
	    String sql = "SELECT * FROM products WHERE seller_id = ?";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, sellerId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                String description = rs.getString("description");
	                int categoryId = rs.getInt("category_id");
	                double price = rs.getDouble("price");
	                int stock = rs.getInt("stock");

	                Product product = new Product(id, sellerId, name, description, categoryId, price, stock);
	                products.add(product);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return products;
	}

	// 🔵 查詢單一商品（依 ID）
	public Product getById(int id) {
	    String sql = "SELECT * FROM products WHERE id = ?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return new Product(
	                    rs.getInt("id"),
	                    rs.getInt("seller_id"),
	                    rs.getString("name"),
	                    rs.getString("description"),
	                    rs.getInt("category_id"),
	                    rs.getDouble("price"),
	                    rs.getInt("stock")
	                );
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

    // 🔵 新增商品
	public boolean insert(Product product) {
	    String sql = "INSERT INTO products (seller_id, name, description, category_id, price, stock) " +
	                 "VALUES (?, ?, ?, ?, ?, ?)";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, product.getSellerId());
	        stmt.setString(2, product.getName());
	        stmt.setString(3, product.getDescription());
	        stmt.setInt(4, product.getCategoryId());
	        stmt.setDouble(5, product.getPrice());
	        stmt.setInt(6, product.getStock());
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	    return true;
	}
	
    // 🔵 更新商品
	public boolean update(Product product) {
	    String sql = "UPDATE products SET seller_id=?, name=?, description=?, category_id=?, price=?, stock=? " +
	                 "WHERE id=?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, product.getSellerId());
	        stmt.setString(2, product.getName());
	        stmt.setString(3, product.getDescription());
	        stmt.setInt(4, product.getCategoryId());
	        stmt.setDouble(5, product.getPrice());
	        stmt.setInt(6, product.getStock());
	        stmt.setInt(7, product.getId());

	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	    return true;
	}
	
    // 🔵 刪除商品
	public boolean delete(int id) {
	    String sql = "DELETE FROM products WHERE id = ?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, id);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	    return true;
	}

	public int findLastInsertedProductIdByUserId(int userId) {
	    int lastId = -1;
	    String sql = "SELECT id FROM products WHERE seller_id = ? ORDER BY id DESC LIMIT 1";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            lastId = rs.getInt("id");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lastId;
	}

}
