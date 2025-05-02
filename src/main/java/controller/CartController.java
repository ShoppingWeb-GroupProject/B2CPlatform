package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import service.CartService;
import model.CartItem;

import java.io.IOException;
import java.util.List;

/**
 * CartController
 * 負責處理購物車相關操作：
 * - 查看購物車
 * - 加入商品到購物車
 * - 更新商品數量
 * - 刪除商品
 */

@WebServlet("/CartController")
public class CartController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("✅ 進入 CartController.doGet()");
        // ✅ 查看購物車內容
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<CartItem> cartItems = cartService.getCartItems(username);
        double totalAmount = cartItems.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalAmount", totalAmount);
        System.out.println("cartItems size: " + cartItems.size());

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            switch (action) {
                case "add":
                    // ✅ 加入商品到購物車
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    cartService.addToCart(username, productId, quantity);
                    break;

                case "update":
                    // ✅ 更新購物車項目數量
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    int newQuantity = Integer.parseInt(request.getParameter("newQuantity"));
                    cartService.updateCartItemQuantity(cartItemId, newQuantity);
                    break;

                case "delete":
                    // ✅ 刪除購物車項目
                    int deleteCartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    cartService.deleteCartItem(deleteCartItemId);
                    break;

                case "clear":
                    // ✅ 清空購物車
                    cartService.clearCart(username);
                    break;

                default:
                    System.out.println("未知操作：" + action);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // 操作完成後重新導向回購物車頁
        response.sendRedirect("CartController");
    }
}
