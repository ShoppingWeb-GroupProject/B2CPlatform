package model;

/**
 * 商品評論類別
 * 對應資料表：reviews
 * 用途：儲存使用者對購買商品的評價與留言
 */
public class Review {
    private int id;             // 評論ID（主鍵，自動編號）
    private int productId;      // 商品ID（外鍵，對應被評價的商品）
    private int userId;         // 使用者ID（外鍵，誰寫的評論）
    private int rating;         // 評分（1~5分）
    private String comment;     // 評論內容（可選填）
    private String createdAt;   // 評論時間（預設當前時間）

    // Getter & Setter

    /** 取得評論ID */
    public int getId() {
        return id;
    }

    /** 設定評論ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 取得商品ID */
    public int getProductId() {
        return productId;
    }

    /** 設定商品ID */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /** 取得使用者ID */
    public int getUserId() {
        return userId;
    }

    /** 設定使用者ID */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** 取得評分 */
    public int getRating() {
        return rating;
    }

    /** 設定評分 */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /** 取得評論內容 */
    public String getComment() {
        return comment;
    }

    /** 設定評論內容 */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /** 取得評論時間 */
    public String getCreatedAt() {
        return createdAt;
    }

    /** 設定評論時間 */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
