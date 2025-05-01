package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

import model.Order;
import service.OrderService;

@SuppressWarnings("serial")
@WebServlet("/OrderController")
public class OrderController extends HttpServlet {

    private OrderService orderService = new OrderService();

    // ✅ GET 用來查詢訂單
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        
        if (session == null || username == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        List<Order> orders = null;

        if ("buyer".equals(role)) {
            orders = orderService.findOrdersByBuyer(username);
        } else if ("seller".equals(role)) {
            orders = orderService.findOrdersBySeller(username);
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }

    // ✅ POST 用來建立訂單
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");

        boolean success = orderService.createOrder(username);

        if (success) {
            response.sendRedirect("OrderController"); // 建完直接GET跳回查詢訂單！
        } else {
            request.setAttribute("error", "訂單建立失敗！");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }
}
