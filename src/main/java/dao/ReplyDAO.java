package dao;

import model.Reply;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReplyDAO {

    // 新增回覆
    public boolean addReply(Reply reply) {
        String sql = "INSERT INTO replies (review_id, seller_id, reply, created_at) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reply.getReviewId());
            stmt.setInt(2, reply.getSellerId());
            stmt.setString(3, reply.getReply());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 查詢某則評論的所有回覆
    public List<Reply> findRepliesByReviewId(int reviewId) {
        List<Reply> replies = new ArrayList<>();
        String sql = "SELECT id, review_id, seller_id, reply, created_at FROM replies WHERE review_id = ? ORDER BY created_at ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reviewId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reply r = new Reply();
                r.setId(rs.getInt("id"));
                r.setReviewId(rs.getInt("review_id"));
                r.setSellerId(rs.getInt("seller_id"));
                r.setReply(rs.getString("reply"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                replies.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return replies;
    }

    // 根據 reviewId 刪除該則評論的所有回覆（常用於刪除整則評論時）
    public boolean deleteRepliesByReviewId(int reviewId) {
        String sql = "DELETE FROM replies WHERE review_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 賣家刪除自己的某一筆回覆
    public boolean deleteReplyById(int replyId, int sellerId) {
        String sql = "DELETE FROM replies WHERE id = ? AND seller_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, replyId);
            stmt.setInt(2, sellerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
