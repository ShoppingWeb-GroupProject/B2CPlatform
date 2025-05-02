package model;

/**
 * 折扣活動類別
 * 對應資料表：discount
 * 用途：儲存折扣或促銷活動的設定，例如折扣名稱、折扣值、是否啟用
 */
public class Discount {
    private int id;                  // 折扣ID（主鍵，自動編號）
    private String name;             // 折扣名稱（例如：春季特賣）
    private String description;      // 折扣描述（簡述活動內容）
    private double discountValue;    // 折扣值（例如：0.1 表示 10% 折扣）
    private boolean isActive;        // 是否啟用（true=啟用，false=停用）

    // Getter & Setter

    /** 取得折扣ID */
    public int getId() {
        return id;
    }

    /** 設定折扣ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 取得折扣名稱 */
    public String getName() {
        return name;
    }

    /** 設定折扣名稱 */
    public void setName(String name) {
        this.name = name;
    }

    /** 取得折扣描述 */
    public String getDescription() {
        return description;
    }

    /** 設定折扣描述 */
    public void setDescription(String description) {
        this.description = description;
    }

    /** 取得折扣值 */
    public double getDiscountValue() {
        return discountValue;
    }

    /** 設定折扣值 */
    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    /** 是否啟用折扣 */
    public boolean isActive() {
        return isActive;
    }

    /** 設定是否啟用折扣 */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
