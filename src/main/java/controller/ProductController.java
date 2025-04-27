package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import model.Product;
import service.ProductService;

@SuppressWarnings("serial")
@WebServlet("/ProductController")
public class ProductController extends HttpServlet {

    private ProductService productService = new ProductService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String action = request.getParameter("action");
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if ("add".equals(action)) {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            productService.addProduct(product);

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            productService.updateProduct(product);
        }

        response.sendRedirect("product-list.jsp");
    }
}
