package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    @Override
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
     * POST：建立新訂單（僅允許從 PaymentController forward 而來）
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ 驗證登入
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ⛔ 檢查是否來自付款流程
        Object fromPayment = request.getAttribute("fromPayment");
        if (!Boolean.TRUE.equals(fromPayment)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "不允許直接建立訂單，請先完成付款。");
            return;
        }

        // ✅ 付款通過，準備建立訂單
        String username = (String) session.getAttribute("username");
        String address = (String) request.getAttribute("address");
        String amountStr = (String) request.getAttribute("amount"); // 🟢 加上這行
        double frontendAmount = 0;

        try {
            frontendAmount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            System.out.println("❌ 金額格式錯誤: " + amountStr);
            response.sendRedirect("cart.jsp");
            return;
        }

        boolean success = orderService.createOrder(username, address, frontendAmount);

        if (success) {
            response.sendRedirect("OrderController");
        } else {
            request.setAttribute("error", "訂單建立失敗！");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }

}
