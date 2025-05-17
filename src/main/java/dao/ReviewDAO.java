package dao;

import java.sql.*;
import java.util.*;
import model.Review;
import util.DBUtil;

public class ReviewDAO {

    // ✅ 查詢某個商品的所有留言 + 使用者名稱
    public List<Review> getReviewsByProductId(int productId) {
        List<Review> list = new ArrayList<>();

        String sql = "SELECT r.*, u.username FROM reviews r " +
                     "JOIN users u ON r.user_id = u.id " +
                     "WHERE r.product_id = ? ORDER BY r.created_at DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Review r = new Review();
                r.setId(rs.getInt("id"));
                r.setUserId(rs.getInt("user_id"));
                r.setProductId(rs.getInt("product_id"));
                r.setRating(rs.getInt("rating"));
                r.setComment(rs.getString("comment"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                r.setUsername(rs.getString("username")); // ✅ 取出留言者名稱
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ 新增一筆留言
    public void addReview(Review review) {
        String sql = "INSERT INTO reviews (user_id, product_id, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getProductId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ 查詢單筆評論（用於刪除前驗證是否為本人）
    public Review getReviewById(int reviewId) {
        Review r = null;
        String sql = "SELECT * FROM reviews WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reviewId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                r = new Review();
                r.setId(rs.getInt("id"));
                r.setUserId(rs.getInt("user_id"));
                r.setProductId(rs.getInt("product_id"));
                r.setRating(rs.getInt("rating"));
                r.setComment(rs.getString("comment"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    // ✅ 刪除一筆留言
    public void deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reviewId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
