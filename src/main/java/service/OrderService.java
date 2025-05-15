package service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.OrderDAO;
import dao.UserDAO;
import model.Order;
import model.OrderItem;
import model.User;

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

    /**
     * 建立新訂單（結帳）
     */
    public boolean createOrder(String username) {
        try {
            int userId = userDAO.findUserIdByUsername(username);
            if (userId == -1) {
                System.out.println("找不到使用者：" + username);
                return false;
            }

            // 查詢購物車商品
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
            order.setAddress("暫時地址");
            order.setStatus("pending");

            boolean success = orderDAO.createOrder(order, cartItems);

            // ✅ 訂單成立推播
            if (success) {
                User user = userDAO.findByUsername(username);
                if (user != null && user.getLineId() != null && !user.getLineId().isEmpty()) {
                    String message = "\u2705 您的訂單已成立，總金額 $" + totalAmount + "，我們將盡快處理！";
                    sendLineNotification(user.getLineId(), message);
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
    public boolean updateOrderStatus(int orderId, String newStatus) {
        boolean success = orderDAO.updateOrderStatus(orderId, newStatus);

        if (success) {
            try {
                Order order = orderDAO.findOrderById(orderId);
                int userId = order.getUserId();

                User user = userDAO.getAllUsers().stream()
                        .filter(u -> u.getId() == userId)
                        .findFirst().orElse(null);

                if (user != null && user.getLineId() != null && !user.getLineId().isEmpty()) {
                    String message = "\uD83D\uDCE6 您的訂單狀態已更新為：" + convertStatusToMessage(newStatus);
                    sendLineNotification(user.getLineId(), message);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return success;
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
     * 發送 LINE 推播訊息
     */
    private void sendLineNotification(String lineId, String message) {
        try {
            String accessToken = "nZwO7jWEPnaKr19in4pFq2HdkbC916jCKc7lbR2YLU/DaIubtPhhcs3aDR/wZhRrm41SII3sZE1UXdvJt/AmgxPaJGsSAiSTPbR5m2DMxCAM4KdOZcaJyKz5drJ2CXPOsRVW/HBdHT4L3rpQJoSkcgdB04t89/1O/w1cDnyilFU="; // ← 替換為你的實際 Token

            @SuppressWarnings("deprecation")
			URL url = new URL("https://api.line.me/v2/bot/message/push");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            JSONObject body = new JSONObject()
                    .put("to", lineId)
                    .put("messages", new JSONArray()
                            .put(new JSONObject()
                                    .put("type", "text")
                                    .put("text", message)));

            conn.getOutputStream().write(body.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println("LINE 推播回應碼：" + conn.getResponseCode());
            conn.getInputStream().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
