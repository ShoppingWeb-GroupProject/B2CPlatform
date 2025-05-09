package model;

/**
 * 訂單主檔類別
 * 對應資料表：orders
 * 用途：
 *   - 記錄使用者下的訂單資料（總金額、收件地址、狀態、建立時間）
 *   - 用於查詢訂單清單、顯示訂單詳情
 */
public class Order {
    private int id;                // 訂單ID（主鍵，自動編號）
    private int userId;            // 使用者ID（外鍵，對應 users 表）
    private double totalAmount;    // 訂單總金額
    private String address;        // 收件地址
    private String status;         // 訂單狀態（pending, paid, shipped, completed, cancelled）
    private String createdAt;      // 訂單建立時間（預設當前時間）

    // 額外欄位（查詢用）
    private String buyerName;      // 買家名稱（賣家查詢訂單時用）

    // Getter and Setter

    /** 取得訂單ID */
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    /** 取得使用者ID */
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    /** 取得訂單總金額 */
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    /** 取得收件地址 */
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    /** 取得訂單狀態 */
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    /** 取得訂單建立時間 */
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    /** 取得買家名稱（賣家用） */
    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }
}
