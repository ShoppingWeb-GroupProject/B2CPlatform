package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import util.DBUtil;

public class ProductDAO {
	
	public List<Product> getAll() {
	    List<Product> products = new ArrayList<>();
	    String sql = "SELECT * FROM products";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            int id = rs.getInt("id");
	            int sellerId = rs.getInt("seller_id");
	            String name = rs.getString("name");
	            String description = rs.getString("description");
	            int categoryId = rs.getInt("category_id");
	            double price = rs.getDouble("price");
	            int stock = rs.getInt("stock");

	            Product product = new Product(id, sellerId, name, description, categoryId, price, stock);
	            products.add(product);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return products;
	}
	
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


	public void insert(Product product) {
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
	    }
	}

	public void update(Product product) {
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
	    }
	}
	
	public void delete(int id) {
	    String sql = "DELETE FROM products WHERE id = ?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, id);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
