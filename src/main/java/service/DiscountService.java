package service;

import dao.DiscountDAO;
import model.Discount;

/**
 * 折扣服務層
 * 負責處理折扣相關邏輯，並呼叫 DiscountDAO 操作資料庫
 */
public class DiscountService {

    private DiscountDAO discountDAO = new DiscountDAO();

    /**
     * 取得使用者折扣
     * 如果資料庫沒有記錄，回傳預設 Discount 物件（折扣值為 1.0）
     */
    public Discount getDiscountByUserId(int userId) {
        Discount discount = discountDAO.findDiscountByUserId(userId);
        if (discount == null) {
            discount = new Discount();
            discount.setUserId(userId);
            discount.setName("未設定");
            discount.setDescription("尚未有專屬折扣");
            discount.setDiscountValue(1.0); // 預設為無折扣
            discount.setActive(false);
        }
        return discount;
    }

    /**
     * 設定或更新使用者折扣數值
     */
    public void updateUserDiscount(int userId, double discountValue) {
        discountDAO.upsertDiscount(userId, discountValue);
    }

    /**
     * 更新折扣的啟用狀態（例如啟用或停用）
     */
    public boolean updateDiscountStatus(int userId, boolean isActive) {
        return discountDAO.updateDiscountStatus(userId, isActive);
    }
}
