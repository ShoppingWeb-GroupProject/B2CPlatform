package model;

import java.sql.Timestamp;
import java.util.List;

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
    private String username;    // 額外欄位：評論者的使用者名稱（來自 JOIN users）
    private String reply;       // 單筆回覆（舊的設計，已保留）
    private List<Reply> replies; // 多筆回覆

    // Getter & Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(Timestamp timestamp) {
        this.createdAt = timestamp.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }
}
