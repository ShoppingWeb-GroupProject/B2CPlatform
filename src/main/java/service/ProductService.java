package service;

import java.util.ArrayList;
import java.util.List;

import dao.ProductDAO;
import dao.UserDAO;
import model.Product;
import util.UploadUtil;

/**
 * ProductService
 * 用途：
 *   - 負責商品相關的業務邏輯
 *   - 包含新增、更新、刪除、查詢商品等
 */
public class ProductService {

    private static ProductDAO productDAO = new ProductDAO();
    private static UserDAO userDAO = new UserDAO();
    private static List<Product> emptyList = new ArrayList<Product>();

    /**
     * 查詢所有商品
     */
    public static List<Product> getAllProducts() {
        try {
            return productDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return emptyList;
        }
    }
    
    public boolean deleteProductWithImage(int productId) {
        Product product = getProductById(productId);
        if (product == null) return false;

        // 1. 刪除 Cloudinary 圖片
        String imageUrl = product.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                UploadUtil.deleteImageByUrl(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 2. 刪除資料庫商品
        return productDAO.delete(productId);
    }

    /**
     * 查詢指定賣家的所有商品
     */
    public static List<Product> getSellerProducts(String username) {
        try {
            int sellerId = userDAO.findUserIdByUsername(username);
            if (sellerId == -1) return emptyList;
            return productDAO.getBySellerId(sellerId);
        } catch (Exception e) {
            e.printStackTrace();
            return emptyList;
        }
    }

    /**
     * 新增商品（包含 imageUrl）
     */
    public static boolean addProduct(Product product) {
        return productDAO.insert(product);
    }

    /**
     * 更新商品（包含 imageUrl）
     */
    public static boolean updateProduct(Product product) {
        return productDAO.update(product);
    }

    /**
     * 刪除商品
     */
    public static boolean deleteProduct(int productId) {
        return productDAO.delete(productId);
    }

    /**
     * 查詢單一商品
     */
    public static Product getProductById(int productId) {
        return productDAO.getById(productId);
    }

//    /**
//     * 查詢指定分類下的商品
//     */
    public static List<Product> getProductsByCategory(int categoryId) {
        return productDAO.findByCategory(categoryId);
    }

    /**
     * 🔽 新增方法：取得某位使用者最後新增的商品 ID
     * 用於圖片上傳後綁定正確的商品
     */
    public int findLastProductIdByUserId(int userId) {
        return productDAO.findLastInsertedProductIdByUserId(userId);
    }
}
