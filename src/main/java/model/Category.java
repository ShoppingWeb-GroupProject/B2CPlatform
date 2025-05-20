package model;

/**
 * 商品分類類別
 * 對應資料表：categories
 * 用途：商品屬於哪個分類，例如「服飾」、「家電」、「書籍」
 */
public class Category {
    private int id;             // 分類ID（主鍵，自動編號）
    private String name;        // 分類名稱（唯一，不可重複，例如「服飾」）
    private String description; // 分類描述（可選，例如「各式各樣的衣物與配件」）
    private boolean state;

    // Getter & Setter

    /** 取得分類ID */
    public int getId() {
        return id;
    }

    /** 設定分類ID */
    public void setId(int id) {
        this.id = id;
    }

    /** 取得分類名稱 */
    public String getName() {
        return name;
    }

    /** 設定分類名稱 */
    public void setName(String name) {
        this.name = name;
    }

    /** 取得分類描述 */
    public String getDescription() {
        return description;
    }

    /** 設定分類描述 */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /** 取得分類ID */
    public boolean getstate() {
        return state;
    }

    /** 設定分類ID */
    public void setstate(boolean state) {
        this.state = state;
    }
}
