package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.OrderItem;
import service.OrderService;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
@WebServlet("/OrderDetailController")
public class OrderDetailController extends HttpServlet {

    private OrderService orderService = new OrderService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null) {
            response.sendRedirect("orders.jsp");
            return;
        }

        int orderId = Integer.parseInt(orderIdStr);

        List<OrderItem> orderItems = orderService.findOrderItemsByOrderId(orderId);

        request.setAttribute("orderItems", orderItems);
        request.setAttribute("orderId", orderId);

        request.getRequestDispatcher("order-detail.jsp").forward(request, response);
    }
}
