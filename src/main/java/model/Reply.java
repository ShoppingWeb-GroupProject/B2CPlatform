package model;

import java.sql.Timestamp;

public class Reply {
    private int id;
    private int reviewId;
    private int sellerId;
    private String reply;         // 實際內容在這裡
    private Timestamp repliedAt;

    // Constructors
    public Reply() {}

    public Reply(int id, int reviewId, int sellerId, String reply, Timestamp repliedAt) {
        this.id = id;
        this.reviewId = reviewId;
        this.sellerId = sellerId;
        this.reply = reply;
        this.repliedAt = repliedAt;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Timestamp getRepliedAt() {
        return repliedAt;
    }

    public void setRepliedAt(Timestamp repliedAt) {
        this.repliedAt = repliedAt;
    }

    // 可選的輔助欄位：格式化時間字串
    public String getFormattedRepliedAt() {
        return repliedAt != null ? repliedAt.toString() : "";
    }

	public void setCreatedAt(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}
}
