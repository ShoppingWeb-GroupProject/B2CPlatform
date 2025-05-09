package dao;

import model.Discount;
import util.DBUtil;

import java.sql.*;

public class DiscountDAO {

    /**
     * 根據 userId 查詢折扣資料
     * 如果資料庫沒資料，回傳 null（由 Service 層處理預設值）
     */
    public Discount findDiscountByUserId(int userId) {
        String sql = "SELECT * FROM discount WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Discount discount = new Discount();
                discount.setId(rs.getInt("id"));
                discount.setUserId(rs.getInt("user_id"));
                discount.setName(rs.getString("name"));
                discount.setDescription(rs.getString("description"));
                discount.setDiscountValue(rs.getDouble("discount_value"));
                discount.setActive(rs.getBoolean("is_active"));
                return discount;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增或更新使用者的折扣資料
     * 如果資料存在則更新，不存在則插入
     */
    public void upsertDiscount(int userId, double discountValue) {
        String updateSql = "UPDATE discount SET discount_value = ? WHERE user_id = ?";
        String insertSql = "INSERT INTO discount (user_id, discount_value) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {

            psUpdate.setDouble(1, discountValue);
            psUpdate.setInt(2, userId);
            int rows = psUpdate.executeUpdate();

            if (rows == 0) {
                try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                    psInsert.setInt(1, userId);
                    psInsert.setDouble(2, discountValue);
                    psInsert.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新折扣的啟用狀態（例如啟用或停用折扣）
     */
    public boolean updateDiscountStatus(int userId, boolean isActive) {
        String sql = "UPDATE discount SET is_active = ? WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, isActive);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
