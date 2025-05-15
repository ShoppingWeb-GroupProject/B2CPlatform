package controller;

import model.Product;
import service.ProductService;
import util.UploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

import dao.CategoryDAO;

@SuppressWarnings({ "serial", "unused" })
@WebServlet("/ProductController")
@MultipartConfig // 🟡 確保支援 multipart/form-data
public class ProductController extends HttpServlet {
//	private ProductService productService = new ProductService();

	@Override
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

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");


        if ("add".equals(action) || "update".equals(action)) {
            // 讀取基本欄位
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String description = request.getParameter("description");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            // 🟡 圖片上傳
            Part imagePart = request.getPart("imageFile");
            String imageUrl = null;
            if (imagePart != null && imagePart.getSize() > 0) {
                try {
                    imageUrl = UploadUtil.uploadImage(imagePart);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 🟡 從 session 取得 sellerId（登入用戶）
            HttpSession session = request.getSession();
            Integer sellerId = (Integer) session.getAttribute("userId");

            if (sellerId == null) {
                response.sendRedirect("login.jsp"); // 尚未登入，導回登入頁
                return;
            }

            // 🟡 組成 Product 物件
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setDescription(description);
            product.setCategoryId(categoryId);
            product.setSellerId(sellerId); // ✅ 設定外鍵欄位

            ProductService productService = new ProductService();

            if ("add".equals(action)) {
                product.setImageUrl(imageUrl); // 新增時直接設定圖片
                ProductService.addProduct(product);

            } else {
                int id = Integer.parseInt(request.getParameter("id"));
                product.setId(id);

                if (imageUrl != null) {
                    product.setImageUrl(imageUrl); // 有新圖則設定
                } else {
                    // 沒上傳新圖片，保留原圖
                    Product existing = ProductService.getProductById(id);
                    product.setImageUrl(existing.getImageUrl());
                }

                ProductService.updateProduct(product);
            }
            response.sendRedirect("ProductController?action=showForSeller");
        }
    }
}