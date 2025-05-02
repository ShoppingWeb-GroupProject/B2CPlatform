package dao;

import model.Discount;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 折扣資料存取層
 * 用途：
 *   - 管理折扣與促銷活動
 *   - 提供新增、查詢、更新、刪除折扣等功能
 */
public class DiscountDAO {

    // 🔵 新增折扣
    public boolean addDiscount(Discount discount) {
        String sql = "INSERT INTO discount (name, description, discount_value, is_active) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, discount.getName());
            stmt.setString(2, discount.getDescription());
            stmt.setDouble(3, discount.getDiscountValue());
            stmt.setBoolean(4, discount.isActive());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 查詢所有折扣
    public List<Discount> findAllDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        String sql = "SELECT * FROM discount";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                discounts.add(mapResultSetToDiscount(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discounts;
    }

    // 🔵 查詢目前啟用的折扣
    public List<Discount> findActiveDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        String sql = "SELECT * FROM discount WHERE is_active = TRUE";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                discounts.add(mapResultSetToDiscount(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discounts;
    }

    // 🔵 更新折扣資訊
    public boolean updateDiscount(Discount discount) {
        String sql = "UPDATE discount SET name = ?, description = ?, discount_value = ?, is_active = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, discount.getName());
            stmt.setString(2, discount.getDescription());
            stmt.setDouble(3, discount.getDiscountValue());
            stmt.setBoolean(4, discount.isActive());
            stmt.setInt(5, discount.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 刪除折扣
    public boolean deleteDiscount(int id) {
        String sql = "DELETE FROM discount WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 工具：將 ResultSet 封裝為 Discount 物件
    private Discount mapResultSetToDiscount(ResultSet rs) throws SQLException {
        Discount discount = new Discount();
        discount.setId(rs.getInt("id"));
        discount.setName(rs.getString("name"));
        discount.setDescription(rs.getString("description"));
        discount.setDiscountValue(rs.getDouble("discount_value"));
        discount.setActive(rs.getBoolean("is_active"));
        return discount;
    }
}
