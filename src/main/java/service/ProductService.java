package service;

import dao.ProductDAO;
import model.Product;

import java.util.List;

/**
 * ProductService
 * 用途：
 *   - 負責商品相關的業務邏輯
 *   - 包含新增、更新、刪除、查詢商品等
 */
public class ProductService {

    private ProductDAO productDAO = new ProductDAO();

    /**
     * 新增商品
     * @param product 傳入要新增的 Product 物件
     * @return true = 新增成功；false = 新增失敗
     */
    public boolean addProduct(Product product) {
        return productDAO.addProduct(product);
    }

    /**
     * 更新商品資料
     * @param product 傳入要更新的 Product 物件
     * @return true = 更新成功；false = 更新失敗
     */
    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }

    /**
     * 刪除商品（依 ID）
     * @param productId 商品 ID
     * @return true = 刪除成功；false = 刪除失敗
     */
    public boolean deleteProduct(int productId) {
        return productDAO.deleteProduct(productId);
    }

    /**
     * 查詢所有商品
     * @return 商品列表
     */
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    /**
     * 查詢指定賣家的商品
     * @param sellerUsername 賣家帳號
     * @return 商品列表
     */
    public List<Product> getSellerProducts(String sellerUsername) {
        return productDAO.findBySeller(getSellerIdByUsername(sellerUsername));
    }

    /**
     * 查詢單一商品（依 ID）
     * @param productId 商品 ID
     * @return Product 物件（找不到回傳 null）
     */
    public Product getProductById(int productId) {
        return productDAO.findById(productId);
    }

    /**
     * 查詢指定分類下的商品
     * @param categoryId 分類 ID
     * @return 商品列表
     */
    public List<Product> getProductsByCategory(int categoryId) {
        return productDAO.findByCategory(categoryId);
    }

    /**
     * 工具：查詢賣家 ID（依帳號）
     * 注意：需要搭配 UserService 或 UserDAO
     * @param sellerUsername 賣家帳號
     * @return 賣家 ID
     */
    private int getSellerIdByUsername(String sellerUsername) {
        // 這裡直接使用 UserDAO，如果要更乾淨可以注入 UserService
        dao.UserDAO userDAO = new dao.UserDAO();
        return userDAO.findUserIdByUsername(sellerUsername);
    }
}
