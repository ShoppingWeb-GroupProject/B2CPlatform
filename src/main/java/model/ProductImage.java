package model;

/**
 * 商品圖片類別
 * 對應資料表：products_img
 * 用途：儲存每個商品的圖片，支援主圖/多圖顯示
 */
public class ProductImage {
    private int id;             // 圖片ID（主鍵，自動編號）
    private int productId;      // 對應的商品ID（外鍵）
    private String imageUrl;    // 圖片URL路徑（存放圖片位址）
    private boolean isMain;     // 是否為主圖片（true=主圖，false=附圖）
    private String uploadedAt;  // 上傳時間（預設當前時間）

    // Getter & Setter

    /** 取得圖片ID */
    public int getId() {
        return id;
    }

    /** 設定圖片ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 取得對應商品ID */
    public int getProductId() {
        return productId;
    }

    /** 設定對應商品ID */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /** 取得圖片URL */
    public String getImageUrl() {
        return imageUrl;
    }

    /** 設定圖片URL */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /** 是否為主圖 */
    public boolean isMain() {
        return isMain;
    }

    /** 設定是否為主圖 */
    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }

    /** 取得上傳時間 */
    public String getUploadedAt() {
        return uploadedAt;
    }

    /** 設定上傳時間 */
    public void setUploadedAt(String uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
