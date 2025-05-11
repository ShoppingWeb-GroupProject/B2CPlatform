package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Locale.Category;

import dao.CategoryDAO;
import dao.UserDAO;

import java.util.ArrayList;

import model.Product;
import service.ProductService;

@SuppressWarnings({ "serial", "unused" })
@WebServlet("/ProductController")
public class ProductController extends HttpServlet {
//	private ProductService productService = new ProductService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		String role = (String) session.getAttribute("role");

		// 先確認 action 是否為 null 避免 NullPointerException
		String action = request.getParameter("action");
		String productId = request.getParameter("productId");
		
		// 查詢分類列表
	    CategoryDAO categoryDAO = new CategoryDAO();
	    List<model.Category> categories = categoryDAO.findAllCategories();
	    

		if (action == null) {
			response.sendRedirect("index.jsp");
			return;
		}

		if (action.equals("show")) {
			// 確保 subject 為 "show" 時才抓取資料
			List<Product> showProducts = ProductService.getAllProducts();
			request.setAttribute("showProducts", showProducts);
			
		} else if (action.equals("showForSeller")) {
			List<Product> showProducts = ProductService.getSellerProducts(username);
			request.setAttribute("showProducts", showProducts);

		} else if (action.equals("modify")) {
		    int theProductId = productId != null ? Integer.parseInt(productId) : -1;
		    if (theProductId != -1) {
		        Product product = ProductService.getProductById(theProductId);
		        request.setAttribute("product", product);
		    }

		    // 查詢分類列表
		    request.setAttribute("categories", categories);
		    request.setAttribute("action", "modify");
		    request.getRequestDispatcher("product-list.jsp").forward(request, response);
		    return;

		} else if (action.equals("delete")) {
			int theProductId = Integer.parseInt(productId);
			ProductService.deleteProduct(theProductId);
			// 重新導向顯示列表
			response.sendRedirect("ProductController?action=showForSeller");
			return;
		} else if (action.equals("detail")) {
			int theProductId = Integer.parseInt(productId);
		    Product product = ProductService.getProductById(theProductId);
		    request.setAttribute("categories", categories);
		    request.setAttribute("product", product);
		    request.setAttribute("action", "show");
		    request.getRequestDispatcher("product-detail.jsp").forward(request, response);
			return;
		}

		// 將資料傳遞到 JSP 頁面
		request.setAttribute("action", action);

		// 轉發請求給 JSP 顯示
		request.getRequestDispatcher("product-list.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		UserDAO theUserDAO = new UserDAO();
		int userId = theUserDAO.findUserIdByUsername(username);
		
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
			e.printStackTrace(); // 可改為錯誤訊息回傳給前端
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

		ProductService productService = new ProductService();

		if (action.equals("add")) {

			Product product = new Product(0, userId, name, description, categoryId, price, stock);
			productService.addProduct(product);

		} else if (action.equals("update")) {
			try {
				id = Integer.parseInt(idStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			Product product = new Product(id, userId, name, description, categoryId, price, stock);
			productService.updateProduct(product);
		}

		response.sendRedirect("ProductController?action=showForSeller"); // 可改為你要導向的頁面
	}

}