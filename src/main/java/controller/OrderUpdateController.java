package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import service.OrderService;

/**
 * OrderUpdateController
 * 用途：
 *   - 處理訂單狀態更新請求
 *   - 包含取消、出貨、完成等動作
 */
@SuppressWarnings("serial")
@WebServlet("/OrderUpdateController")
public class OrderUpdateController extends HttpServlet {

    private OrderService orderService = new OrderService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 從請求取得訂單 ID 與操作動作
        String orderIdStr = request.getParameter("orderId");
        String action = request.getParameter("action"); // cancel / ship / complete

        if (orderIdStr == null || action == null) {
            // 缺少必要參數 → 導回訂單頁
            response.sendRedirect("orders.jsp");
            return;
        }

        int orderId = Integer.parseInt(orderIdStr);
        boolean success = false;

        // 根據動作決定要更新的狀態
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
            // 更新成功 → 跳回訂單列表頁重新查詢
            response.sendRedirect("OrderController");
        } else {
            // 更新失敗 → 輸出錯誤訊息
            response.getWriter().println("訂單狀態更新失敗！");
        }
    }
}
