package service;

import java.util.List;

import dao.OrderDAO;
import dao.ProductDAO;
import dao.UserDAO;
import model.Order;
import model.OrderItem;
import model.User;
import util.LineUtil;

/**
 * OrderService
 * 功能：
 * - 建立訂單並通知買家
 * - 更新訂單狀態並推播
 * - 查詢訂單與明細
 */
public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final UserDAO userDAO = new UserDAO();
    private final ProductDAO productDAO = new ProductDAO(); // 加上欄位
    /**
     * 建立新訂單（結帳）
     */
    public boolean createOrder(String username, String address, double frontendAmount) {
        try {
            // 🧑‍💻 查使用者 ID
            int userId = userDAO.findUserIdByUsername(username);
            if (userId == -1) {
                System.out.println("❌ 找不到使用者：" + username);
                return false;
            }

            // 📦 查購物車商品
            List<OrderItem> cartItems = orderDAO.getCartItems(userId);
            if (cartItems.isEmpty()) {
                System.out.println("❌ 購物車為空，無法結帳");
                return false;
            }

            // 🧾 後端重新計算總金額
            double backendAmount = cartItems.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();

            // 💥 前端金額驗證
            if (Math.abs(backendAmount - frontendAmount) > 0.01) {
                System.out.printf("❌ 前端金額 (%.2f) 與後端計算金額 (%.2f) 不一致！%n", frontendAmount, backendAmount);
                return false;
            }

         // 📮 處理收件地址
            if (address == null) {
                address = userDAO.findUserAddressByUsername(username);
            }

            if (address == null) {
                System.out.println("❌ 找不到任何收件地址");
                return false;
            }

            address = address.trim();

            if (address.isEmpty()) {
                System.out.println("❌ 收件地址為空白字串");
                return false;
            }

            // ✅ 建立訂單物件
            Order order = new Order();
            order.setUserId(userId);
            order.setTotalAmount(backendAmount);
            order.setAddress(address);
            order.setStatus("pending");

            // 📝 寫入資料庫
            boolean success = orderDAO.createOrder(order, cartItems);

            // ✅ 如果成功就推播通知
            if (success) {
                User user = userDAO.findByUsername(username);
                if (user != null && user.getLineId() != null && !user.getLineId().isEmpty()) {
                    String message = "\u2705 您的訂單已成立，總金額 $" + backendAmount + "，我們將盡快處理！";
                    LineUtil.sendPushMessage(user.getLineId(), message);
                }
            }

            return success;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 更新訂單狀態並推播
     */
    /**
     * 更新訂單狀態並推播與庫存處理
     */
    public boolean updateOrderStatus(int orderId, String newStatus) {
        boolean success = orderDAO.updateOrderStatus(orderId, newStatus);

        if (!success) return false;

        try {
            // 出貨時扣庫存
            if ("shipped".equals(newStatus)) {
                List<OrderItem> items = orderDAO.findOrderItemsByOrderId(orderId);
                for (OrderItem item : items) {
                    productDAO.decreaseStock(item.getProductId(), item.getQuantity());
                }
            }

            // 查找用戶並推播
            Order order = orderDAO.findOrderById(orderId);
            int userId = order.getUserId();

            User user = userDAO.getAllUsers().stream()
                    .filter(u -> u.getId() == userId)
                    .findFirst().orElse(null);

            if (user != null && user.getLineId() != null && !user.getLineId().isEmpty()) {
                String message = "\uD83D\uDCE6 您的訂單狀態已更新為：" + convertStatusToMessage(newStatus);
                LineUtil.sendPushMessage(user.getLineId(), message); // 呼叫 util
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    /**
     * 狀態代碼轉換為中文訊息
     */
    private String convertStatusToMessage(String status) {
        return switch (status) {
            case "pending" -> "等待付款";
            case "paid" -> "已付款";
            case "shipped" -> "已出貨";
            case "completed" -> "已完成";
            case "cancelled" -> "已取消";
            default -> status;
        };
    }

    /**
     * 查詢買家訂單
     */
    public List<Order> findOrdersByBuyer(String username) {
        int userId = userDAO.findUserIdByUsername(username);
        return orderDAO.findOrdersByUserId(userId);
    }

    /**
     * 查詢賣家訂單
     */
    public List<Order> findOrdersBySeller(String username) {
        int sellerId = userDAO.findUserIdByUsername(username);
        return orderDAO.findOrdersBySellerId(sellerId);
    }

    /**
     * 查詢訂單明細
     */
    public List<OrderItem> findOrderItemsByOrderId(int orderId) {
        return orderDAO.findOrderItemsByOrderId(orderId);
    }
}
