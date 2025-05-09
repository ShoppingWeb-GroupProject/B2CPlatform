package service;

import dao.CartItemDAO;
import dao.UserDAO;
import model.CartItem;

import java.util.List;

public class CartItemService {
	private final CartItemDAO cartDAO = new CartItemDAO();
	private UserDAO userDAO = new UserDAO();
	@SuppressWarnings("unused")
	private CartItem theCart = new CartItem();
	
	// ✅ 根據 username 查詢購物車內容
    public List<CartItem> getCartItemsByUsername(String username) {
        int userId = userDAO.findUserIdByUsername(username);
        return cartDAO.getCartItemsByUserId(userId);
    }

    // ✅ 加入或更新購物車項目
    public boolean addOrUpdateCartItem(String username, int productId, int quantity) {
    	int userId = userDAO.findUserIdByUsername(username);
		if (userId == -1)
			return false;

        // 檢查是否已有該項目，已有則更新數量
        List<CartItem> existingItems = cartDAO.getCartItemsByUserId(userId);
        for (CartItem item : existingItems) {
            if (item.getProductId() == productId) {
                int newQuantity = item.getQuantity() + quantity;
                return cartDAO.updateCartItemQuantity(userId, productId, newQuantity);
            }
        }

        // 沒有則新增項目
        CartItem newItem = new CartItem();
        newItem.setUserId(userId);
        newItem.setProductId(productId);
        newItem.setQuantity(quantity);
        return cartDAO.addCartItem(newItem);
    }

	public boolean removeItem(String username, int productId) {
		int userId = userDAO.findUserIdByUsername(username);
		if (userId == -1)
			return false;
		return cartDAO.removeCartItem(userId, productId);
	}

	public List<CartItem> getUserCart(int userId) {
		return cartDAO.getCartItemsByUserId(userId);
	}

	public boolean clearCart(int userId) {
		return cartDAO.clearCartByUserId(userId);
	}
}
