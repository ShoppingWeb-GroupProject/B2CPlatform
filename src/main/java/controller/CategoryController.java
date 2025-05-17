package controller;

import java.io.IOException;
import java.util.List;

import dao.CategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;

@WebServlet("/CategoryController")
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 查詢所有分類
        List<Category> categories = categoryDAO.findAllCategories();
        request.setAttribute("categories", categories);

        // 導向 JSP
        request.getRequestDispatcher("Category.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // 新增分類
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            categoryDAO.addCategory(category);
            request.setAttribute("message", "已完成新增");

        } else if ("update".equals(action)) {
            // 修改分類
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            category.setDescription(description);
            categoryDAO.updateCategory(category);
            request.setAttribute("message", "已完成修改");

        } else if ("delete".equals(action)) {
            // 刪除分類
            int id = Integer.parseInt(request.getParameter("id"));
            categoryDAO.deleteCategory(id);
        }

        // 完成操作後重導回分類頁，避免重複提交
        List<Category> categories = categoryDAO.findAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("Category.jsp").forward(request, response);
    }
}
