package dao;

import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.CartItem;

public class CartItemDAO {

    public boolean addCartItem(CartItem item) {
        String sql = "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getUserId());
            stmt.setInt(2, item.getProductId());
            stmt.setInt(3, item.getQuantity());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean updateCartItemQuantity(int userId, int productId, int quantity) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeCartItem(int userId, int productId) {
        String sql = "DELETE FROM cart_items WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, productId);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CartItem> getCartItemsByUserId(int userId) {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT c.id, c.product_id, c.quantity, c.added_at, p.name, p.price " +
                     "FROM cart_items c " +
                     "JOIN products p ON c.product_id = p.id " +
                     "WHERE c.user_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getInt("id"));
                item.setUserId(userId);
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setAddedAt(rs.getString("added_at"));
                item.setProductName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public boolean clearCartByUserId(int userId) {
        String sql = "DELETE FROM cart_items WHERE user_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
