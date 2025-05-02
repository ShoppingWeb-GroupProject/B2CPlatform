package service;

import dao.CartItemDAO;
import dao.UserDAO;
import model.CartItem;

import java.util.List;

/**
 * CartService
 * 用途：
 *   - 處理購物車相關的業務邏輯
 *   - 包含加入購物車、修改數量、刪除項目、查詢購物車
 */
public class CartService {
	
	
    private CartItemDAO cartItemDAO = new CartItemDAO();
    private UserDAO userDAO = new UserDAO();
    
    /**
     * 加入購物車（若已存在則增加數量）
     * @param username 使用者帳號
     * @param productId 商品 ID
     * @param quantity 數量
     * @return true = 成功；false = 失敗
     */
    public boolean addToCart(String username, int productId, int quantity) {
        int userId = userDAO.findUserIdByUsername(username);
        if (userId == -1) {
            System.out.println("找不到使用者：" + username);
            return false;
        }
        return cartItemDAO.addOrUpdateCartItem(userId, productId, quantity);
    }

    /**
     * 查詢購物車內容
     * @param username 使用者帳號
     * @return 購物車項目列表
     */
    public List<CartItem> getCartItems(String username) {
    	
        int userId = userDAO.findUserIdByUsername(username);

        if (userId == -1) {
            System.out.println("找不到使用者：" + username);
            return List.of();
        }
        return cartItemDAO.getCartItems(userId);
    }

    /**
     * 更新購物車項目數量
     * @param cartItemId 購物車項目 ID
     * @param newQuantity 新的數量
     * @return true = 更新成功；false = 更新失敗
     */
    public boolean updateCartItemQuantity(int cartItemId, int newQuantity) {
        return cartItemDAO.updateCartItemQuantity(cartItemId, newQuantity);
    }

    /**
     * 刪除購物車項目
     * @param cartItemId 購物車項目 ID
     * @return true = 刪除成功；false = 刪除失敗
     */
    public boolean deleteCartItem(int cartItemId) {
        return cartItemDAO.deleteCartItem(cartItemId);
    }

    /**
     * 清空購物車
     * @param username 使用者帳號
     * @return true = 清空成功；false = 清空失敗
     */
    public boolean clearCart(String username) {
        int userId = userDAO.findUserIdByUsername(username);
        if (userId == -1) {
            System.out.println("找不到使用者：" + username);
            return false;
        }
        return cartItemDAO.clearCart(userId);
    }
    
}
