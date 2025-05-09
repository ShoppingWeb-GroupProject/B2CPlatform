package service;

import dao.OrderDAO;
import dao.UserDAO;
import model.Order;
import model.OrderItem;

import java.util.List;

/**
 * OrderService
 * 用途：
 *   - 處理訂單相關的業務邏輯
 *   - 包含建立訂單、查詢訂單、更新訂單狀態、查詢訂單明細
 */
public class OrderService {

    private OrderDAO orderDAO = new OrderDAO();
    private UserDAO userDAO = new UserDAO();

    /**
     * 建立新訂單（結帳）
     * @param username 買家帳號
     * @return true = 成功；false = 失敗
     */
    public boolean createOrder(String username) {
        try {
            int userId = userDAO.findUserIdByUsername(username);

            if (userId == -1) {
                System.out.println("找不到使用者：" + username);
                return false;
            }

            // 從購物車中取出商品項目
            List<OrderItem> cartItems = orderDAO.getCartItems(userId);

            if (cartItems.isEmpty()) {
                System.out.println("購物車為空，無法結帳");
                return false;
            }

            // 計算總金額
            double totalAmount = cartItems.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();

            // 建立訂單物件
            Order order = new Order();
            order.setUserId(userId);
            order.setTotalAmount(totalAmount);
            order.setAddress("暫時地址"); // TODO：未來可從表單或使用者資料取得
            order.setStatus("pending");

            // 呼叫 DAO 建立訂單與訂單項目
            return orderDAO.createOrder(order, cartItems);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查詢買家自己的訂單
     * @param username 買家帳號
     * @return 訂單列表
     */
    public List<Order> findOrdersByBuyer(String username) {
        int userId = userDAO.findUserIdByUsername(username);
        return orderDAO.findOrdersByUserId(userId);
    }

    /**
     * 查詢賣家接到的訂單
     * @param username 賣家帳號
     * @return 訂單列表
     */
    public List<Order> findOrdersBySeller(String username) {
        int sellerId = userDAO.findUserIdByUsername(username);
        return orderDAO.findOrdersBySellerId(sellerId);
    }

    /**
     * 更新訂單狀態
     * @param orderId 訂單 ID
     * @param newStatus 新狀態（pending、paid、shipped、completed、cancelled）
     * @return true = 更新成功；false = 更新失敗
     */
    public boolean updateOrderStatus(int orderId, String newStatus) {
        return orderDAO.updateOrderStatus(orderId, newStatus);
    }

    /**
     * 查詢指定訂單的明細項目
     * @param orderId 訂單 ID
     * @return 訂單項目列表
     */
    public List<OrderItem> findOrderItemsByOrderId(int orderId) {
        return orderDAO.findOrderItemsByOrderId(orderId);
    }
}
