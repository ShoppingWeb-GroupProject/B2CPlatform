package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import service.OrderService;

@SuppressWarnings("serial")
@WebServlet("/OrderUpdateController")
public class OrderUpdateController extends HttpServlet {

    private OrderService orderService = new OrderService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdStr = request.getParameter("orderId");
        String action = request.getParameter("action"); // cancel / ship / complete

        if (orderIdStr == null || action == null) {
            response.sendRedirect("orders.jsp");
            return;
        }

        int orderId = Integer.parseInt(orderIdStr);
        boolean success = false;

        switch (action) {
            case "cancel":
                success = orderService.updateOrderStatus(orderId, "cancelled");
                break;
            case "ship":
                success = orderService.updateOrderStatus(orderId, "shipped");
                break;
            case "complete":
                success = orderService.updateOrderStatus(orderId, "completed");
                break;
            default:
                break;
        }

        if (success) {
            response.sendRedirect("OrderController"); // 更新完跳回訂單列表
        } else {
            response.getWriter().println("訂單狀態更新失敗！");
        }
    }
}
