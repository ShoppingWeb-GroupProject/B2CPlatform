package service;

import dao.OrderDAO;
import dao.UserDAO;
import model.Order;
import model.OrderItem;

import java.util.List;

public class OrderService {

    private OrderDAO orderDAO = new OrderDAO();
    private UserDAO userDAO = new UserDAO();

    // ✅ 建立訂單（結帳）
    public boolean createOrder(String username) {
        try {
            int userId = userDAO.findUserIdByUsername(username);

            if (userId == -1) {
                return false;
            }

            List<OrderItem> cartItems = orderDAO.getCartItems(userId);

            if (cartItems.isEmpty()) {
                return false;
            }

            double totalAmount = cartItems.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();

            Order order = new Order();
            order.setUserId(userId);
            order.setTotalAmount(totalAmount);
            order.setAddress("暫時地址");
            order.setStatus("pending");

            return orderDAO.createOrder(order, cartItems);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ 查詢買家自己的訂單
    public List<Order> findOrdersByBuyer(String username) {
        int userId = userDAO.findUserIdByUsername(username);
        return orderDAO.findOrdersByUserId(userId);
    }

    // ✅ 查詢賣家接到的訂單
    public List<Order> findOrdersBySeller(String username) {
        int sellerId = userDAO.findUserIdByUsername(username);
        return orderDAO.findOrdersBySellerId(sellerId);
    }
    
 // ✅ 更新訂單狀態
    public boolean updateOrderStatus(int orderId, String newStatus) {
        return orderDAO.updateOrderStatus(orderId, newStatus);
    }
    
    public List<OrderItem> findOrderItemsByOrderId(int orderId) {
        return orderDAO.findOrderItemsByOrderId(orderId);
    }


}
