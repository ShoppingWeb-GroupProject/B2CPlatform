package service;

import dao.ProductDAO;
import dao.UserDAO;
import model.Product;
import java.util.List;
import java.util.ArrayList;
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
     * @return 商品列表
     */
	public static List<Product> getAllProducts() {
		try {
			return productDAO.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return emptyList;
		}
	}
	
    /**
     * 查詢指定賣家的商品
     * @param sellerUsername 賣家帳號
     * @return 商品列表
     */
	public static List<Product> getSellerProducts(String username) {
		try {
			int sellerId = userDAO.findUserIdByUsername(username);
			if (sellerId == -1) {
                return emptyList;
            }
			return productDAO.getBySellerId(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return emptyList;
		}
	}

    /**
     * 新增商品
     * @param product 傳入要新增的 Product 物件
     * @return true = 新增成功；false = 新增失敗
     */
    public boolean addProduct(Product product) {
        return productDAO.insert(product);
    }

    /**
     * 更新商品資料
     * @param product 傳入要更新的 Product 物件
     * @return true = 更新成功；false = 更新失敗
     */
    public boolean updateProduct(Product product) {
        return productDAO.update(product);
    }

    /**
     * 刪除商品（依 ID）
     * @param productId 商品 ID
     * @return true = 刪除成功；false = 刪除失敗
     */
    public static boolean deleteProduct(int productId) {
        return productDAO.delete(productId);
    }


    /**
     * 查詢單一商品（依 ID）
     * @param productId 商品 ID
     * @return Product 物件（找不到回傳 null）
     */
    public static Product getProductById(int productId) {
        return productDAO.getById(productId);
    }

    /**
     * 查詢指定分類下的商品
     * @param categoryId 分類 ID
     * @return 商品列表
     */
    public List<Product> getProductsByCategory(int categoryId) {
        return productDAO.findByCategory(categoryId);
    }
}
