package dao;

import model.Product;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // 查詢全部商品
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.* FROM products p " +
        	    	 "JOIN categories c ON p.category_id = c.id " +
        	    	 "WHERE c.state = true";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    // 🔧 更新商品主圖（image_url）
    public boolean updateImageUrl(int productId, String imageUrl) {
        String sql = "UPDATE products SET image_url = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, imageUrl);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 依賣家 ID 查詢商品
    public List<Product> getBySellerId(int sellerId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE seller_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    // 查詢單一商品
    public Product getById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 新增商品
    public boolean insert(Product product) {
        String sql = "INSERT INTO products (seller_id, name, description, category_id, price, stock, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 🔍 印出要寫入的商品資料
            System.out.println("🚀 [insert] 寫入商品：");
            System.out.println("  sellerId: " + product.getSellerId());
            System.out.println("  name: " + product.getName());
            System.out.println("  price: " + product.getPrice());
            System.out.println("  stock: " + product.getStock());
            System.out.println("  imageUrl: " + product.getImageUrl());

            stmt.setInt(1, product.getSellerId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setInt(4, product.getCategoryId());
            stmt.setDouble(5, product.getPrice());
            stmt.setInt(6, product.getStock());
            stmt.setString(7, product.getImageUrl());

            int result = stmt.executeUpdate();
            System.out.println("✅ [insert] 寫入結果：" + result);
            return result > 0;

        } catch (Exception e) {
            System.out.println("❌ [insert] 發生例外");
            e.printStackTrace();
            return false;
        }
    }

    // 更新商品
    public boolean update(Product product) {
        String sql = "UPDATE products SET name=?, description=?, category_id=?, price=?, stock=?, image_url=? WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getCategoryId());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getStock());
            stmt.setString(6, product.getImageUrl());
            stmt.setInt(7, product.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 刪除商品
    public boolean delete(int productId) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔍 工具方法：將 ResultSet 映射為 Product 物件
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setSellerId(rs.getInt("seller_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setPrice(rs.getDouble("price"));
        product.setStock(rs.getInt("stock"));
        product.setImageUrl(rs.getString("image_url"));
        return product;
    }

    // 取得某位賣家最後一筆商品 ID（給圖片上傳用）
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
	}

	/**
	 * 扣除商品庫存
	 */
	public void decreaseStock(int productId, int quantity) throws SQLException {
	    String sql = "UPDATE products SET stock = stock - ? WHERE id = ?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, quantity);
	        stmt.setInt(2, productId);
	        int rows = stmt.executeUpdate();
	        System.out.println("扣庫存結果：productId=" + productId + ", 數量=" + quantity + ", 影響行數=" + rows);
	    }
	}
}
