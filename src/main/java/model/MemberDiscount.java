package model;

public class MemberDiscount {
    private int userId;
    private double discountRate; // 如 0.9 表示 9 折

    public MemberDiscount(int userId, double discountRate) {
        this.userId = userId;
        this.discountRate = discountRate;
    }

    public int getUserId() {
        return userId;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
}