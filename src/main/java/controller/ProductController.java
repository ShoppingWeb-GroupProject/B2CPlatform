package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;

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
	        // 取得所有商品
	        List<Product> showProducts = ProductService.getAllProducts();
	        request.setAttribute("showProducts", showProducts);
	        request.setAttribute("action", "show");

	    } else if (action.equals("showForSeller")) {
	        // 取得賣家上架的商品
	        List<Product> showProducts = ProductService.getSellerProducts(username);
	        request.setAttribute("showProducts", showProducts);
	        request.setAttribute("action", "showForSeller");

	    } else if (action.equals("modify")) {
	        // 商品修改頁：讀取商品資料與分類清單
	        int theProductId = productId != null ? Integer.parseInt(productId) : -1;
	        if (theProductId != -1) {
	            Product product = ProductService.getProductById(theProductId);
	            request.setAttribute("product", product);
	        }

	        request.setAttribute("categories", categories);
	        request.setAttribute("action", "modify");

	        // ✅ 已 forward，結束執行
	        request.getRequestDispatcher("product-list.jsp").forward(request, response);
	        return;

	    } else if (action.equals("delete")) {
	        // 刪除商品後重新導向
	        int theProductId = Integer.parseInt(productId);
	        ProductService.deleteProduct(theProductId);
	        response.sendRedirect("ProductController?action=showForSeller");
	        return;

	    } else if (action.equals("detail")) {
	        // 商品詳情頁
	        int theProductId = Integer.parseInt(productId);
	        Product product = ProductService.getProductById(theProductId);
	        request.setAttribute("product", product);
	        request.setAttribute("action", "show");

	        // ✅ forward 完後 return，避免下方再次 forward
	        request.getRequestDispatcher("product-detail.jsp").forward(request, response);
	        return;
	    }

	    // 若為 show 或 showForSeller，統一轉發到列表頁
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
            try {
                Part imagePart = request.getPart("image");
                if (imagePart != null && imagePart.getSize() > 0) {
                    InputStream fileStream = imagePart.getInputStream();

                    com.cloudinary.Cloudinary cloudinary = new com.cloudinary.Cloudinary(com.cloudinary.utils.ObjectUtils.asMap(
                        "cloud_name", "dsnzdecej",
                        "api_key", "588179336638767",
                        "api_secret", "8frJ3t9Cb_-CEPhDcNTVFNLZsAA"
                    ));

                    @SuppressWarnings("unchecked")
					Map<String, Object> uploadResult = cloudinary.uploader().upload(fileStream, com.cloudinary.utils.ObjectUtils.emptyMap());
                    String imageUrl = (String) uploadResult.get("secure_url");

                    model.ProductImage image = new model.ProductImage();
                    image.setProductId(productService.findLastProductIdByUserId(userId)); // 取剛新增商品 ID
                    image.setImageUrl(imageUrl);
                    image.setMain(true);

                    new dao.ProductImageDAO().addProductImage(image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


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