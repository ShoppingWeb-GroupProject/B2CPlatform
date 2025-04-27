package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SeedDataUtil {

    public static void insertSampleProducts() {
        String sql = "INSERT INTO products (seller_id, name, description, price, stock) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 1; i <= 10; i++) {
                stmt.setInt(1, 1); // 假設都是 seller_id=1 的賣家
                stmt.setString(2, "假商品 " + i);
                stmt.setString(3, "這是第 " + i + " 個假商品的描述。");
                stmt.setDouble(4, 100 + i * 10); // 價格100起跳，每個+10
                stmt.setInt(5, 50 + i * 5); // 庫存50起跳，每個+5
                stmt.addBatch();
            }

            stmt.executeBatch();
            System.out.println("✅ 假商品資料成功新增 10 筆！");
        } catch (SQLException e) {
            System.out.println("❌ 插入假資料失敗: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        insertSampleProducts();
    }
}
