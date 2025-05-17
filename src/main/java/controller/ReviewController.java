package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;

import model.Review;
import model.User;
import service.ReviewService;

@SuppressWarnings("serial")
@WebServlet("/ReviewController") 
public class ReviewController extends HttpServlet {

    private ReviewService reviewService = new ReviewService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        if ("delete".equals(action)) {
            // 🗑 買家刪除自己的評論
            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            int productId = Integer.parseInt(request.getParameter("productId"));

            Review review = reviewService.getReviewById(reviewId);

            if (review != null && review.getUserId() == user.getId()) {
                reviewService.deleteReview(reviewId);
            }

            response.sendRedirect("ProductController?action=detail&productId=" + productId);
            return;
        }

        // 📝 預設是新增評論
        int productId = Integer.parseInt(request.getParameter("productId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        Review review = new Review();
        review.setUserId(user.getId());
        review.setProductId(productId);
        review.setRating(rating);
        review.setComment(comment);

        reviewService.submitReview(review);

        response.sendRedirect("ProductController?action=detail&productId=" + productId);
    }
}
