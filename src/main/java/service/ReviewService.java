package service;

import java.util.List;
import dao.ReviewDAO;
import model.Review;

public class ReviewService {
    private ReviewDAO reviewDAO = new ReviewDAO();

    // ✅ 查詢某商品的所有評論
    public List<Review> getReviewsByProduct(int productId) {
        return reviewDAO.getReviewsByProductId(productId);
    }

    // ✅ 新增一筆評論
    public void submitReview(Review review) {
        reviewDAO.addReview(review);
    }

    // ✅ 根據評論 ID 查詢評論內容（給刪除功能用）
    public Review getReviewById(int reviewId) {
        return reviewDAO.getReviewById(reviewId);
    }

    // ✅ 根據評論 ID 刪除評論
    public void deleteReview(int reviewId) {
        reviewDAO.deleteReview(reviewId);
    }
}
