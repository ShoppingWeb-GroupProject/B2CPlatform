package controller;

import dao.CategoryDAO;
import model.Category;
import model.Product;
import service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
@WebServlet("/home")
public class HomeController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 取得啟用分類
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categories = categoryDAO.findActiveCategories();
        request.setAttribute("categories", categories);

        // 取得各分類第一個商品
        Map<Integer, Product> productMap = new HashMap<>();
        for (Category category : categories) {
            List<Product> products = ProductService.getProductsByCategory(category.getId());
            if (products != null && !products.isEmpty()) {
                productMap.put(category.getId(), products.get(0));
            }
        }
        request.setAttribute("productMap", productMap);

        // 轉交首頁 JSP
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
