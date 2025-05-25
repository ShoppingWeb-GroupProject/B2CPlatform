package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
import model.User;
import service.CartItemService;
import service.UserService;

@SuppressWarnings("serial")
@WebServlet("/CartItemController")
public class CartItemController extends HttpServlet {

    private CartItemService cartItemService = new CartItemService();
    private UserService userService = new UserService();

    // ✅ 顯示購物車內容（GET）
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;
        User theUser = userService.getUserByUsername(username);
        
        String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) {
            request.setAttribute("paymentError", error);
        }

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<CartItem> cartItems = cartItemService.getCartItemsByUsername(username);

        // 計算總金額
        double total = 0;
        for (CartItem item : cartItems) {
        	total += item.getPrice() * item.getQuantity();
        }
        
        // 折扣後金額
        double discount = theUser.getDiscount(); // 假設 1.0 為原價、0.9 為九折
        double discountedTotal = total * discount;

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("discountedTotal", (int)discountedTotal);
        request.setAttribute("address", theUser.getAddress());
        
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        String action = request.getParameter("action");

        if ("remove".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            cartItemService.removeItem(username, productId);
            response.sendRedirect("CartItemController");
            return;
        }

        // 預設：加入購物車
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        boolean success = cartItemService.addOrUpdateCartItem(username, productId, quantity);

        if (success) {
            response.sendRedirect("CartItemController");
        } else {
            request.setAttribute("error", "加入購物車失敗！");
            request.getRequestDispatcher("product.jsp").forward(request, response);
        }
    }

}
