package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Discount;
import service.DiscountService;
import service.UserService;

import java.io.IOException;

/**
 * 折扣控制器
 * 負責處理折扣相關的顯示與更新操作
 */
@WebServlet("/DiscountController")
public class DiscountController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DiscountService discountService;
    private UserService userService;

    @Override
    public void init() {
        discountService = new DiscountService();
        userService = new UserService();
    }

    // 處理 GET 請求：顯示折扣資料頁面
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");

        if (username != null && !username.isEmpty()) {
            int userId = userService.getUserIdByUsername(username);
            if (userId != -1) {
                Discount discount = discountService.getDiscountByUserId(userId);
                request.setAttribute("username", username);
                request.setAttribute("discount", discount);
            } else {
                request.setAttribute("error", "查無此會員。");
            }
        }

        request.getRequestDispatcher("discountForm.jsp").forward(request, response);
    }

    // 處理 POST 請求：更新折扣資料
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String discountStr = request.getParameter("discount");

        if (username == null || username.isEmpty() || discountStr == null || discountStr.isEmpty()) {
            request.setAttribute("error", "請輸入會員帳號與折扣值。");
            request.setAttribute("username", username);
            request.getRequestDispatcher("discountForm.jsp").forward(request, response);
            return;
        }

        try {
            double discountValue = Double.parseDouble(discountStr);
            int userId = userService.getUserIdByUsername(username);

            if (userId == -1) {
                request.setAttribute("error", "查無此會員。");
                request.setAttribute("username", username);
                request.getRequestDispatcher("discountForm.jsp").forward(request, response);
                return;
            }

            discountService.updateUserDiscount(userId, discountValue);

            request.setAttribute("message", "折扣設定成功！");
            request.setAttribute("username", username);
            request.setAttribute("discount", discountService.getDiscountByUserId(userId));
            request.getRequestDispatcher("discountForm.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "折扣值格式錯誤，請輸入數字。");
            request.setAttribute("username", username);
            request.getRequestDispatcher("discountForm.jsp").forward(request, response);
        }
    }
}
