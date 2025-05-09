package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

import model.Order;
import service.OrderService;

/**
 * OrderController
 * 用途：
 *   - 處理訂單查詢與建立
 *   - GET：查詢訂單清單（依買家或賣家角色）
 *   - POST：從購物車建立新訂單
 */
@SuppressWarnings("serial")
@WebServlet("/OrderController")
public class OrderController extends HttpServlet {

    private OrderService orderService = new OrderService();

    /**
     * GET：查詢訂單清單
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 取得 session 資料
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        // 如果未登入，導回登入頁

        if (session == null || username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Order> orders = null;

        // 根據角色查詢不同訂單資料
        if ("buyer".equals(role)) {
            orders = orderService.findOrdersByBuyer(username);
        } else if ("seller".equals(role)) {
            orders = orderService.findOrdersBySeller(username);
        }

        // 將訂單清單放入 request，轉交到 JSP 顯示
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }

    /**
     * POST：建立新訂單
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 驗證登入
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");

        // 呼叫服務層建立訂單
        boolean success = orderService.createOrder(username);

        if (success) {
            // 建立成功 → 重新查詢訂單
            response.sendRedirect("OrderController");
        } else {
            // 建立失敗 → 顯示錯誤訊息並回到購物車頁
            request.setAttribute("error", "訂單建立失敗！");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }
}
