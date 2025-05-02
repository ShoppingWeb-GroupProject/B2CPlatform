package model;

/**
 * 訂單項目類別
 * 對應資料表：order_items
 * 用途：
 *   - 記錄每筆訂單中包含哪些商品、數量、單價
 *   - 搭配 JOIN 查詢時可帶出商品名稱，用於訂單詳情頁顯示
 */
public class OrderItem {
    private int id;               // 訂單項目ID（主鍵，自動編號）
    private int orderId;          // 訂單ID（外鍵，對應 orders 表）
    private int productId;        // 商品ID（外鍵，對應 products 表）
    private int quantity;         // 購買數量
    private double price;         // 商品單價（當時價格，用於保留歷史金額）

    // 額外欄位（查詢用）
    private String productName;   // 商品名稱（JOIN products 表時用）

    // Getter & Setter

    /** 取得訂單項目ID */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /** 取得訂單ID */
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /** 取得商品ID */
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /** 取得購買數量 */
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /** 取得商品單價 */
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    /** 取得商品名稱（查詢用） */
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
}
