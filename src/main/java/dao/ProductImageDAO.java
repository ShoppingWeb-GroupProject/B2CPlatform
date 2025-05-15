package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ProductImage;
import util.DBUtil;

/**
 * 商品圖片資料存取層
 * 用途：
 *   - 管理商品的圖片（主圖與附圖）
 *   - 提供新增圖片、查詢圖片、更新主圖、刪除圖片等功能
 */
public class ProductImageDAO {

    // 🔵 新增商品圖片
    public boolean addProductImage(ProductImage image) {
        String sql = "INSERT INTO products_img (product_id, image_url, is_main) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, image.getProductId());
            stmt.setString(2, image.getImageUrl());
            stmt.setBoolean(3, image.isMain());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 查詢指定商品的所有圖片
    public List<ProductImage> findImagesByProductId(int productId) {
        List<ProductImage> images = new ArrayList<>();
        String sql = "SELECT * FROM products_img WHERE product_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                images.add(mapResultSetToProductImage(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }

    // 🔵 查詢指定商品的主圖
    public ProductImage findMainImageByProductId(int productId) {
        String sql = "SELECT * FROM products_img WHERE product_id = ? AND is_main = TRUE LIMIT 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToProductImage(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔵 更新主圖（先將其他圖片設為 false，再設指定圖片為 true）
    public boolean setMainImage(int productId, int imageId) {
        String clearSql = "UPDATE products_img SET is_main = FALSE WHERE product_id = ?";
        String setSql = "UPDATE products_img SET is_main = TRUE WHERE id = ?";

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement clearStmt = conn.prepareStatement(clearSql)) {
                clearStmt.setInt(1, productId);
                clearStmt.executeUpdate();
            }

            try (PreparedStatement setStmt = conn.prepareStatement(setSql)) {
                setStmt.setInt(1, imageId);
                setStmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 刪除圖片
    public boolean deleteImage(int imageId) {
        String sql = "DELETE FROM products_img WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, imageId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔵 工具：將 ResultSet 封裝為 ProductImage 物件
    private ProductImage mapResultSetToProductImage(ResultSet rs) throws SQLException {
        ProductImage image = new ProductImage();
        image.setId(rs.getInt("id"));
        image.setProductId(rs.getInt("product_id"));
        image.setImageUrl(rs.getString("image_url"));
        image.setMain(rs.getBoolean("is_main"));
        image.setUploadedAt(rs.getString("uploaded_at"));
        return image;
    }
}
