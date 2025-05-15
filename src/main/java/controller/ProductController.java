package controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import dao.UserDAO;
import model.Product;
import service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
@WebServlet("/ProductController")
@MultipartConfig
public class ProductController extends HttpServlet {

    private ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String idParam = request.getParameter("productId");

        if ("detail".equals(action)) {
            if (idParam == null || idParam.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少 productId");
                return;
            }
            int productId = Integer.parseInt(idParam);
            Product product = ProductService.getProductById(productId);
            request.setAttribute("product", product);
            request.getRequestDispatcher("productDetail.jsp").forward(request, response);

        } else if ("modify".equals(action)) {
            if (idParam == null || idParam.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少 productId");
                return;
            }
            int productId = Integer.parseInt(idParam);
            Product product = ProductService.getProductById(productId);
            request.setAttribute("product", product);
            request.setAttribute("action", "modify");
            request.getRequestDispatcher("product-list.jsp").forward(request, response);

        } else if ("showForSeller".equals(action)) {
            HttpSession session = request.getSession(false);
            String username = (String) session.getAttribute("username");
            List<Product> products = ProductService.getSellerProducts(username);
            request.setAttribute("showProducts", products);
            request.setAttribute("action", "showForSeller");
            request.getRequestDispatcher("product-list.jsp").forward(request, response);

        } else {
            List<Product> products = ProductService.getAllProducts();
            request.setAttribute("showProducts", products);
            request.setAttribute("action", "show");
            request.getRequestDispatcher("product-list.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String categoryIdStr = request.getParameter("categoryId");
        String priceStr = request.getParameter("price");
        String stockStr = request.getParameter("stock");

        int categoryId = Integer.parseInt(categoryIdStr);
        double price = Double.parseDouble(priceStr);
        int stock = Integer.parseInt(stockStr);

        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        UserDAO userDAO = new UserDAO();
        int sellerId = userDAO.findUserIdByUsername(username);

        // 🔽 上傳圖片至 Cloudinary，並取得 imageUrl
        Part imagePart = request.getPart("image");
        String imageUrl = null;

        if (imagePart != null && imagePart.getSize() > 0) {
            try (InputStream inputStream = imagePart.getInputStream()) {
                Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "dsnzdecej",
                        "api_key", "588179336638767",
                        "api_secret", "8frJ3t9Cb_-CEPhDcNTVFNLZsAA"
                ));

                @SuppressWarnings("unchecked")
				Map<String, Object> uploadResult = cloudinary.uploader().upload(inputStream, ObjectUtils.emptyMap());
                imageUrl = (String) uploadResult.get("secure_url");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if ("add".equals(action)) {
            Product product = new Product();
            product.setSellerId(sellerId);
            product.setName(name);
            product.setDescription(description);
            product.setCategoryId(categoryId);
            product.setPrice(price);
            product.setStock(stock);
            product.setImageUrl(imageUrl); // 🔽 設定圖片網址

            productService.addProduct(product);

        } else if ("update".equals(action)) {
            int productId = Integer.parseInt(id);

            Product product = new Product();
            product.setId(productId);
            product.setSellerId(sellerId);
            product.setName(name);
            product.setDescription(description);
            product.setCategoryId(categoryId);
            product.setPrice(price);
            product.setStock(stock);
            product.setImageUrl(imageUrl); // 🔽 設定新圖片網址（若有上傳）

            productService.updateProduct(product);
        }

        response.sendRedirect("ProductController?action=showForSeller");
    }
}
