package controller;

import model.Product;
import model.Review;
import service.ProductService;
import service.ReviewService;
import util.UploadUtil;

import dao.ReplyDAO;
import dao.CategoryDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
@WebServlet("/ProductController")
@MultipartConfig
public class ProductController extends HttpServlet {

    private ReplyDAO replyDAO = new ReplyDAO(); // ✅ 改用 ReplyDAO

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        @SuppressWarnings("unused")
		String role = (String) session.getAttribute("role");
        String action = request.getParameter("action");
        String productId = request.getParameter("productId");

        CategoryDAO categoryDAO = new CategoryDAO();
        List<model.Category> categories = categoryDAO.findActiveCategories();

        if (action == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        if ("show".equals(action)) {
            List<Product> showProducts = ProductService.getAllProducts();
            request.setAttribute("showProducts", showProducts);
            request.setAttribute("action", "show");
            request.setAttribute("categories", categoryDAO.findActiveCategories());


        } else if ("showForSeller".equals(action)) {
            List<Product> showProducts = ProductService.getAllProducts();
            request.setAttribute("showProducts", showProducts);
            request.setAttribute("categories", categories);
            request.setAttribute("action", "showForSeller");

        } else if ("modify".equals(action)) {
            int theProductId = productId != null ? Integer.parseInt(productId) : -1;
            if (theProductId != -1) {
                Product product = ProductService.getProductById(theProductId);
                request.setAttribute("product", product);
            }
            request.setAttribute("categories", categories);
            request.setAttribute("action", "modify");
            request.getRequestDispatcher("product-list.jsp").forward(request, response);
            return;

        } else if ("delete".equals(action)) {
            int theProductId = Integer.parseInt(productId);
            ProductService.deleteProduct(theProductId);
            response.sendRedirect("ProductController?action=showForSeller");
            return;

        } else if ("detail".equals(action)) {
            int theProductId = Integer.parseInt(productId);
            Product product = ProductService.getProductById(theProductId);
            request.setAttribute("product", product);

            ReviewService reviewService = new ReviewService();
            List<Review> reviews = reviewService.getReviewsByProduct(theProductId);
            for (Review r : reviews) {
                r.setReplies(replyDAO.findRepliesByReviewId(r.getId())); // ✅ 改用 replyDAO 查詢回覆
            }

            request.setAttribute("product", product);
            request.setAttribute("action", "show");
            request.setAttribute("categories", categories);
            request.setAttribute("reviews", reviews);
            request.getRequestDispatcher("product-detail.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("product-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("add".equals(action) || "update".equals(action)) {
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String description = request.getParameter("description");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            Part imagePart = request.getPart("imageFile");
            String imageUrl = null;
            if (imagePart != null && imagePart.getSize() > 0) {
                try {
                    imageUrl = UploadUtil.uploadImage(imagePart);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            HttpSession session = request.getSession();
            Integer sellerId = (Integer) session.getAttribute("userId");
            if (sellerId == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setDescription(description);
            product.setCategoryId(categoryId);
            product.setSellerId(sellerId);

            if ("add".equals(action)) {
                product.setImageUrl(imageUrl);
                ProductService.addProduct(product);
            } else {
                int id = Integer.parseInt(request.getParameter("id"));
                product.setId(id);
                if (imageUrl != null) {
                    product.setImageUrl(imageUrl);
                } else {
                    Product existing = ProductService.getProductById(id);
                    product.setImageUrl(existing.getImageUrl());
                }
                ProductService.updateProduct(product);
            }

            response.sendRedirect("ProductController?action=showForSeller");
        }
    }
}
