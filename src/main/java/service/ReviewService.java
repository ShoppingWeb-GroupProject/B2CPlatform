package service;

import java.util.List;
import dao.ReviewDAO;
import model.Review;

public class ReviewService {
    private ReviewDAO reviewDAO = new ReviewDAO();

    public List<Review> getReviewsByProduct(int productId) {
        return reviewDAO.getReviewsByProductId(productId);
    }

    public void submitReview(Review review) {
        reviewDAO.addReview(review);
    }
}
