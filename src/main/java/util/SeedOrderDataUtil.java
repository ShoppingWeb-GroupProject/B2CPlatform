package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class SeedOrderDataUtil {

    public static void insertSampleOrders() {
        Random random = new Random();
        int userId = 1; // 假設買家是 id=1

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            for (int i = 0; i < 5; i++) { // 建立 5 筆訂單
                double totalAmount = 0.0;

                // 建立訂單
                String insertOrderSQL = "INSERT INTO orders (user_id, total_amount, address, status) VALUES (?, ?, ?, ?)";
                try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
                    orderStmt.setInt(1, userId);
                    orderStmt.setDouble(2, 0); // 先暫時填 0，後面補正確金額
                    orderStmt.setString(3, "測試地址 123號");
                    orderStmt.setString(4, "pending");
                    orderStmt.executeUpdate();

                    ResultSet generatedKeys = orderStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);

                        // 決定這筆訂單有幾個商品
                        int itemCount = random.nextInt(3) + 1; // 1~3個商品
                        for (int j = 0; j < itemCount; j++) {
                            int productId = random.nextInt(10) + 1; // 商品ID 1~10
                            int quantity = random.nextInt(3) + 1;   // 數量 1~3
                            double price = 100 + random.nextInt(100); // 價格100~199

                            // 插入訂單商品
                            String insertItemSQL = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
                            try (PreparedStatement itemStmt = conn.prepareStatement(insertItemSQL)) {
                                itemStmt.setInt(1, orderId);
                                itemStmt.setInt(2, productId);
                                itemStmt.setInt(3, quantity);
                                itemStmt.setDouble(4, price);
                                itemStmt.executeUpdate();
                            }

                            totalAmount += price * quantity;
                        }

                        // 更新訂單總金額
                        String updateOrderSQL = "UPDATE orders SET total_amount = ? WHERE id = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateOrderSQL)) {
                            updateStmt.setDouble(1, totalAmount);
                            updateStmt.setInt(2, orderId);
                            updateStmt.executeUpdate();
                        }
                    }
                }
            }

            conn.commit();
            System.out.println("✅ 假訂單資料成功新增完成！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 假訂單資料新增失敗！");
        }
    }

    public static void main(String[] args) {
        insertSampleOrders();
    }
}
