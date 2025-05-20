package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import util.DBUtil;

public class CategoryDAO {

	
    /**
     * 查詢所有分類
     */
    public List<Category> findAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name, description, state FROM categories";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setstate(rs.getBoolean("state"));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
    /**
     * 查詢啟用分類
     */
    public List<Category> findActiveCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name, description, state FROM categories WHERE state = true";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setstate(rs.getBoolean("state"));
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * 新增分類
     */
    public boolean addCategory(Category category) {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());

            int rows = stmt.executeUpdate();
            return rows > 0; // 有影響行數，代表新增成功

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新分類
     */
    public boolean updateCategory(Category category) {
        String sql = "UPDATE categories SET name = ?, description = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setInt(3, category.getId());

            int rows = stmt.executeUpdate();
            return rows > 0; // 有影響行數，代表更新成功

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 分類狀態修改
     */
    public boolean updateCategory(int id, boolean state) {
        String sql = "UPDATE categories SET state = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	
        	stmt.setBoolean(1, state);
            stmt.setInt(2, id);

            int rows = stmt.executeUpdate();
            return rows > 0; // 有影響行數，代表刪除成功

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
