package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderItem;
import service.OrderService;

/**
 * OrderDetailController
 * 用途：
 *   - 根據訂單 ID 查詢並顯示該訂單的詳細項目（商品清單）
 */
@SuppressWarnings("serial")
@WebServlet("/OrderDetailController")
public class OrderDetailController extends HttpServlet {

    private OrderService orderService = new OrderService();

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 從請求參數取得 orderId
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null) {
            // 若未提供 orderId，導回訂單清單頁
            response.sendRedirect("orders.jsp");
            return;
        }

        int orderId = Integer.parseInt(orderIdStr);

        // 呼叫服務層查詢訂單項目清單
        List<OrderItem> orderItems = orderService.findOrderItemsByOrderId(orderId);

        // 將資料放入 request
        request.setAttribute("orderItems", orderItems);
        request.setAttribute("orderId", orderId);

        // 導向訂單明細頁面
        request.getRequestDispatcher("order-detail.jsp").forward(request, response);
    }
}
