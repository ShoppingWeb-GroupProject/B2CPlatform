package model;

/**
 * 商品主檔類別
 * 對應資料表：products
 * 用途：
 *   - 儲存平台上的商品基本資料，例如名稱、描述、分類、價格、庫存
 *   - 用於前台商品列表、商品詳情頁、後台商品管理
 */
public class Product {
    private int id;             // 商品ID（主鍵，自動編號）
    private int sellerId;       // 賣家ID（外鍵，對應 users 表）
    private String name;        // 商品名稱
    private String description; // 商品描述
    private int categoryId;     // 分類ID（外鍵，對應 categories 表）
    private double price;       // 商品價格
    private int stock;          // 商品庫存數量

    // Constructor（空參數）
    public Product() {}

    // Constructor（全參數）
    public Product(int id, int sellerId, String name, String description, int categoryId, double price, int stock) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.stock = stock;
    }

    // Getters

    /** 取得商品ID */
    public int getId() {
        return id;
    }

    /** 取得賣家ID */
    public int getSellerId() {
        return sellerId;
    }

    /** 取得商品名稱 */
    public String getName() {
        return name;
    }

    /** 取得商品描述 */
    public String getDescription() {
        return description;
    }

    /** 取得分類ID */
    public int getCategoryId() {
        return categoryId;
    }

    /** 取得商品價格 */
    public double getPrice() {
        return price;
    }

    /** 取得商品庫存數量 */
    public int getStock() {
        return stock;
    }

    // Setters

    /** 設定商品ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 設定賣家ID */
    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    /** 設定商品名稱 */
    public void setName(String name) {
        this.name = name;
    }

    /** 設定商品描述 */
    public void setDescription(String description) {
        this.description = description;
    }

    /** 設定分類ID */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /** 設定商品價格 */
    public void setPrice(double price) {
        this.price = price;
    }

    /** 設定商品庫存數量 */
    public void setStock(int stock) {
        this.stock = stock;
    }
}
