package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.UserDAO;

import java.io.IOException;

@WebServlet("/DiscountController")
public class DiscountController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    // GET: show discount by username
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");

        if (username != null && !username.isEmpty()) {
            int userId = userDAO.findUserIdByUsername(username);
            if (userId != -1) {
                double currentDiscount = userDAO.getUserDiscount(userId);
                request.setAttribute("username", username);
                request.setAttribute("currentDiscount", currentDiscount);
            } else {
                request.setAttribute("error", "User not found.");
            }
        }

        request.getRequestDispatcher("discountForm.jsp").forward(request, response);
    }

    // POST: update discount by username
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String discountStr = request.getParameter("discount");

        if (username == null || username.isEmpty() || discountStr == null || discountStr.isEmpty()) {
            request.setAttribute("error", "Username and discount are required.");
            request.setAttribute("username", username);
            request.getRequestDispatcher("discountForm.jsp").forward(request, response);
            return;
        }

        try {
            double discount = Double.parseDouble(discountStr);
            int userId = userDAO.findUserIdByUsername(username);

            if (userId == -1) {
                request.setAttribute("error", "User not found.");
                request.setAttribute("username", username);
                request.getRequestDispatcher("discountForm.jsp").forward(request, response);
                return;
            }

            userDAO.updateUserDiscount(userId, discount);

            // ✅ 折扣設定成功後跳轉回會員管理頁面
            response.sendRedirect("MemberController?action=list");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid discount format.");
            request.setAttribute("username", username);
            request.getRequestDispatcher("discountForm.jsp").forward(request, response);
        }
    }
}
