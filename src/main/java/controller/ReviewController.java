package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import java.io.IOException;

import dao.ReplyDAO;
import model.Reply;
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

        try {
            // === 🗑 刪除單筆回覆（賣家可刪自己留言）
            if ("deleteReply".equals(action)) {
                int replyId = Integer.parseInt(request.getParameter("replyId"));
                int productId = Integer.parseInt(request.getParameter("productId"));

                new ReplyDAO().deleteReplyById(replyId, user.getId()); // 僅可刪自己的留言
                response.sendRedirect("ProductController?action=detail&productId=" + productId);
                return;
            }

            // === 🗑 刪除評論（買家或賣家皆可）
            if ("delete".equals(action)) {
                String reviewIdStr = request.getParameter("reviewId");
                String productIdStr = request.getParameter("productId");

                if (reviewIdStr != null && productIdStr != null) {
                    int reviewId = Integer.parseInt(reviewIdStr);
                    int productId = Integer.parseInt(productIdStr);

                    Review review = reviewService.getReviewById(reviewId);
                    if (review != null &&
                            (user.getId() == review.getUserId() || "seller".equalsIgnoreCase(user.getRole()))) {
                        reviewService.deleteReview(reviewId);
                    }

                    response.sendRedirect("ProductController?action=detail&productId=" + productId);
                    return;
                }
            }

            // === ✍️ 賣家新增一筆回覆
            if ("reply".equals(action)) {
                String replyText = request.getParameter("reply");
                String reviewIdStr = request.getParameter("reviewId");
                String productIdStr = request.getParameter("productId");

                if (replyText != null && reviewIdStr != null && productIdStr != null &&
                        !replyText.trim().isEmpty() && "seller".equalsIgnoreCase(user.getRole())) {

                    int reviewId = Integer.parseInt(reviewIdStr);
                    int productId = Integer.parseInt(productIdStr);

                    Reply reply = new Reply();
                    reply.setReviewId(reviewId);
                    reply.setSellerId(user.getId());
                    reply.setReply(replyText.trim());

                    new ReplyDAO().addReply(reply);
                    response.sendRedirect("ProductController?action=detail&productId=" + productId);
                    return;
                }
            }

            // === 📝 買家新增評論
            if ("addReview".equals(action) || action == null) {
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

        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
