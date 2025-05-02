package dao;

import model.CartItem;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 購物車資料存取層
 * 用途：
 *   - 管理使用者購物車內容
 *   - 提供加入購物車、修改數量、刪除、查詢等功能
 */
public class CartItemDAO {

    // 🔵 加入購物車（如果已存在則更新數量）
    public boolean addOrUpdateCartItem(int userId, int productId, int quantity) {
        String checkSql = "SELECT id, quantity FROM cart_items WHERE user_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateSql = "UPDATE cart_items SET quantity = quantity + ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, productId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int cartItemId = rs.getInt("id");
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, quantity);
                    updateStmt.setInt(2, cartItemId);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setInt(2, productId);
                    insertStmt.setInt(3, quantity);
                    insertStmt.executeUpdate();
                }
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 查詢購物車項目（帶商品名稱與單價）
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT c.id, c.user_id, c.product_id, c.quantity, c.added_at, p.name AS product_name, p.price " +
                     "FROM cart_items c JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getInt("id"));
                item.setUserId(rs.getInt("user_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setAddedAt(rs.getString("added_at"));
                item.setProductName(rs.getString("product_name"));
                item.setPrice(rs.getDouble("price"));
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // 🔵 更新購物車項目數量
    public boolean updateCartItemQuantity(int cartItemId, int newQuantity) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, cartItemId);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 刪除購物車項目
    public boolean deleteCartItem(int cartItemId) {
        String sql = "DELETE FROM cart_items WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartItemId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 清空購物車
    public boolean clearCart(int userId) {
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
