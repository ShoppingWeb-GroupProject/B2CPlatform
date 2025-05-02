package model;

/**
 * 購物車項目類別
 * 對應資料表：cart_items
 * 用途：
 *   - 記錄使用者加入購物車的商品及數量
 *   - 用於結帳前的購物車畫面顯示
 *   - 搭配 JOIN products 表時，可以帶出商品名稱、價格
 */
public class CartItem {
    private int id;              // 購物車項目ID（主鍵，自動編號）
    private int userId;          // 使用者ID（外鍵，對應 users 表）
    private int productId;       // 商品ID（外鍵，對應 products 表）
    private int quantity;        // 購買數量
    private String addedAt;      // 加入購物車的時間（預設為當前時間）

    // 加入 JOIN 後用於顯示的額外欄位
    private String productName;  // 商品名稱（來自 products 表）
    private double price;        // 商品單價（來自 products 表）

    // Getter & Setter

    /** 取得購物車項目ID */
    public int getId() {
        return id;
    }

    /** 設定購物車項目ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 取得使用者ID */
    public int getUserId() {
        return userId;
    }

    /** 設定使用者ID */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** 取得商品ID */
    public int getProductId() {
        return productId;
    }

    /** 設定商品ID */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /** 取得購買數量 */
    public int getQuantity() {
        return quantity;
    }

    /** 設定購買數量 */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /** 取得加入時間 */
    public String getAddedAt() {
        return addedAt;
    }

    /** 設定加入時間 */
    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    /** 取得商品名稱 */
    public String getProductName() {
        return productName;
    }

    /** 設定商品名稱 */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /** 取得商品單價 */
    public double getPrice() {
        return price;
    }

    /** 設定商品單價 */
    public void setPrice(double price) {
        this.price = price;
    }

    /** 計算小計金額（單價 × 數量） */
    public double getSubtotal() {
        return price * quantity;
    }
}
