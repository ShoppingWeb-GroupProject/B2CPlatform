package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

import dao.UserDAO;
import model.Product;
import service.ProductService;

/**
 * ProductController
 * 用途：
 *   - 處理商品相關操作，包括新增、修改、刪除、查詢
 *   - GET：顯示商品列表、賣家商品、單一商品、刪除商品
 *   - POST：新增或更新商品
 */
@SuppressWarnings("serial")
@WebServlet("/ProductController")
public class ProductController extends HttpServlet {

    private ProductService productService = new ProductService();

    /**
     * GET：處理商品查詢與刪除
     */
    @SuppressWarnings({ "unused" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        String action = request.getParameter("action");
        String productId = request.getParameter("productId");

        if (action == null) {
            // 沒有 action → 導回首頁
            response.sendRedirect("index.jsp");
            return;
        }

        if (action.equals("show")) {
            // 顯示所有商品
            List<Product> showProducts = productService.getAllProducts();
            request.setAttribute("showProducts", showProducts);
            request.setAttribute("action", "show");

        } else if (action.equals("showForSeller")) {
            // 顯示賣家上架的商品
            List<Product> showProducts = productService.getSellerProducts(username);
            request.setAttribute("showProducts", showProducts);
            request.setAttribute("action", "show");

        } else if (action.equals("modify")) {
            // 準備修改商品（此段可擴充傳入 Product 物件）
            int theProductId = productId != null ? Integer.parseInt(productId) : -1;
            if (theProductId != -1) {
                Product product = productService.getProductById(theProductId);
                request.setAttribute("product", product);
            }
            request.setAttribute("action", "modify");

        } else if (action.equals("delete")) {
            // 刪除商品後 → 導回賣家商品列表
            int theProductId = Integer.parseInt(productId);
            productService.deleteProduct(theProductId);
            response.sendRedirect("ProductController?action=showForSeller");
            return;
        }

        // 將資料傳遞到 JSP 頁面
        request.setAttribute("action", action);

        // 轉發請求到商品列表頁
        request.getRequestDispatcher("product-list.jsp").forward(request, response);
    }

    /**
     * POST：處理新增或更新商品
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        // 透過 UserDAO 查出 userId
        UserDAO userDAO = new UserDAO();
        int userId = userDAO.findUserIdByUsername(username);

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String stockStr = request.getParameter("stock");
        String categoryIdStr = request.getParameter("categoryId");
        String idStr = request.getParameter("id");

        double price = 0.0;
        int stock = 0;
        int categoryId = 0;
        int id = 0;

        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (action.equals("add")) {
            // 新增商品
            Product product = new Product(0, userId, name, description, categoryId, price, stock);
            productService.addProduct(product);

        } else if (action.equals("update")) {
            // 更新商品
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            Product product = new Product(id, userId, name, description, categoryId, price, stock);
            productService.updateProduct(product);
        }

        // 完成後導回賣家商品列表
        response.sendRedirect("ProductController?action=showForSeller");
    }
}
