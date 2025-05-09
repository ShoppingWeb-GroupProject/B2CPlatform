package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.OrderService;

import java.io.IOException;

/**
 * CheckoutController
 * 用途：
 *   - 負責處理結帳請求，將購物車轉換為訂單
 *   - 呼叫 OrderService 完成訂單建立
 */
@SuppressWarnings("serial")
@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        // 取得目前登入使用者的 session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // 呼叫訂單服務層進行建立訂單
        OrderService orderService = new OrderService();
        boolean success = orderService.createOrder(username);

        // 根據結果導向對應頁面（成功 → 訂單頁，失敗 → 結帳頁）
        if (success) {
            response.sendRedirect("orders.jsp?success=1");
        } else {
            response.sendRedirect("checkout.jsp?error=1");
        }
    }
}
