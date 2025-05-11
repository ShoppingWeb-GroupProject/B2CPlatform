package dao;

import model.Order;
import model.OrderItem;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderDAO
 * 用途：
 *   - 處理訂單資料庫操作，包括新增、查詢、更新
 */
public class OrderDAO {
	
	public Order findOrderById(int orderId) {
	    String sql = "SELECT * FROM orders WHERE id = ?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, orderId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            Order order = new Order();
	            order.setId(rs.getInt("id"));
	            order.setUserId(rs.getInt("user_id"));
	            order.setTotalAmount(rs.getDouble("total_amount"));
	            order.setAddress(rs.getString("address"));
	            order.setStatus(rs.getString("status"));
	            order.setCreatedAt(rs.getString("created_at"));
	            return order;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}


    /**
     * 建立新訂單（含訂單項目）
     */
    public boolean createOrder(Order order, List<OrderItem> items) {
        String insertOrderSQL = "INSERT INTO orders (user_id, total_amount, address, status) VALUES (?, ?, ?, ?)";
        String insertOrderItemSQL = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            // 插入訂單主表
            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getUserId());
                orderStmt.setDouble(2, order.getTotalAmount());
                orderStmt.setString(3, order.getAddress());
                orderStmt.setString(4, order.getStatus());
                orderStmt.executeUpdate();

                ResultSet generatedKeys = orderStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);

                    // 插入訂單項目表
                    try (PreparedStatement itemStmt = conn.prepareStatement(insertOrderItemSQL)) {
                        for (OrderItem item : items) {
                            itemStmt.setInt(1, orderId);
                            itemStmt.setInt(2, item.getProductId());
                            itemStmt.setInt(3, item.getQuantity());
                            itemStmt.setDouble(4, item.getPrice());
                            itemStmt.addBatch();
                        }
                        itemStmt.executeBatch();
                    }
                }
            }

            // 下單成功後，清空購物車
            try (PreparedStatement clearCartStmt = conn.prepareStatement("DELETE FROM cart_items WHERE user_id = ?")) {
                clearCartStmt.setInt(1, order.getUserId());
                clearCartStmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查詢使用者購物車中的商品項目（用於建立訂單）
     */
    public List<OrderItem> getCartItems(int userId) {
        List<OrderItem> items = new ArrayList<>();

        String sql = "SELECT c.product_id, p.name, p.price, c.quantity " +
                     "FROM cart_items c " +
                     "JOIN products p ON c.product_id = p.id " +
                     "WHERE c.user_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    /**
     * 查詢買家自己的訂單
     */
    public List<Order> findOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, total_amount, address, status, created_at FROM orders WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setAddress(rs.getString("address"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getString("created_at"));
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * 查詢賣家接到的訂單
     */
    public List<Order> findOrdersBySellerId(int sellerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.id, o.total_amount, o.address, o.status, o.created_at, u.username AS buyer_name " +
                     "FROM orders o " +
                     "JOIN order_items oi ON o.id = oi.order_id " +
                     "JOIN products p ON oi.product_id = p.id " +
                     "JOIN users u ON o.user_id = u.id " +
                     "WHERE p.seller_id = ? " +
                     "GROUP BY o.id " +
                     "ORDER BY o.created_at DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setAddress(rs.getString("address"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getString("created_at"));
                order.setBuyerName(rs.getString("buyer_name"));
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * 更新訂單狀態
     */
    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查詢單一訂單的明細項目
     */
    public List<OrderItem> findOrderItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT p.name, oi.quantity, oi.price " +
                     "FROM order_items oi " +
                     "JOIN products p ON oi.product_id = p.id " +
                     "WHERE oi.order_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setProductName(rs.getString("name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
