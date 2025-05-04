package dao;

import java.sql.*;
import java.util.*;
import model.User;
import util.DBUtil;

public class MemberDAO {
    private Connection conn;

    public MemberDAO(Connection conn) {
        this.conn = conn;
    }

    public List<User> getAllMembers() throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setBlacklisted(rs.getBoolean("is_blacklisted"));
                user.setDiscount(new DiscountDAO().findByUserId(user.getId()));

                list.add(user);
            }
        }
        return list;
    }

    public void blacklistMember(int userId, boolean blacklist) throws SQLException {
        String sql = "UPDATE users SET is_blacklisted = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, blacklist);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public void updateUserRole(int userId, String role) throws SQLException {
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public void updateDiscount(int userId, double discountRate) throws SQLException {
        String sql = "UPDATE users SET discount = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, discountRate);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public boolean updateBlacklistStatus(int userId, boolean isBlacklisted) {
        String sql = "UPDATE users SET is_blacklisted = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isBlacklisted);
            stmt.setInt(2, userId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

