package dao;

import util.DBUtil;
import java.sql.*;

public class DiscountDAO {
    // 取得某使用者的折扣（若沒設定就回傳 1.0）
    public double findByUserId(int userId) {
        String sql = "SELECT discount_value FROM discount WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("discount_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1.0;
    }

    // 設定或更新使用者的折扣
    public void upsertDiscount(int userId, double discount) {
        String updateSql = "UPDATE discount SET discount_value = ? WHERE user_id = ?";
        String insertSql = "INSERT INTO discount(user_id, discount_value) VALUES(?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement psUp = conn.prepareStatement(updateSql)) {
            psUp.setDouble(1, discount);
            psUp.setInt(2, userId);
            int rows = psUp.executeUpdate();
            if (rows == 0) {
                try (PreparedStatement psIn = conn.prepareStatement(insertSql)) {
                    psIn.setInt(1, userId);
                    psIn.setDouble(2, discount);
                    psIn.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
