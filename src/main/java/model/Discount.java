package model;

/**
 * 折扣資料模型
 * 用來對應 discount 資料表的欄位
 */
public class Discount {
    private int id;               // 折扣 ID（主鍵）
    private int userId;           // 使用者 ID（外鍵，對應 users 表）
    private String name;          // 折扣名稱，例如「新會員優惠」
    private String description;   // 折扣描述，例如「首單九折」
    private double discountValue; // 折扣數值，例如 0.9 表示九折
    private boolean isActive;     // 是否啟用該折扣

    // === Getter & Setter ===

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
