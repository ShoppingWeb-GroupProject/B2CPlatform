package service;

import dao.CategoryDAO;
import model.Category;

import java.util.List;

public class CategoryService {

    private CategoryDAO categoryDAO = new CategoryDAO();

    /**
     * 查詢所有分類
     */
    public List<Category> getAllCategories() {
        return categoryDAO.findAllCategories();
    }

    /**
     * 新增分類
     */
    public boolean addCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return categoryDAO.addCategory(category);
    }

    /**
     * 更新分類
     */
    public boolean updateCategory(int id, String name, String description) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setDescription(description);
        return categoryDAO.updateCategory(category);
    }

    /**
     * 刪除分類
     */
    public boolean deleteCategory(int id) {
        return categoryDAO.deleteCategory(id);
    }
}
