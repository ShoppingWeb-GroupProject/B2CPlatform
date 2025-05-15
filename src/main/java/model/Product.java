package model;

public class Product {
    private int id;
    private int sellerId;
    private String name;
    private String description;
    private int categoryId;
    private double price;
    private int stock;

    // 🔽 新增：商品主圖網址欄位
    private String imageUrl;

    // 建構子
    public Product() {}

    public Product(int id, int sellerId, String name, String description, int categoryId, double price, int stock) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.stock = stock;
    }

    // Getter 和 Setter 區塊

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // 🔽 取得商品主圖網址
    public String getImageUrl() {
        return imageUrl;
    }

    // 🔽 設定商品主圖網址
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
