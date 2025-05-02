package model;

/**
 * 使用者類別
 * 對應資料表：users
 * 用途：
 *   - 儲存平台使用者資料，包括買家、賣家、管理員
 *   - 用於登入、註冊、驗證角色權限
 */
public class User {
    private int id;             // 使用者ID（主鍵，自動編號）
    private String username;    // 使用者帳號名稱（唯一）
    private String password;    // 使用者密碼（可考慮加密儲存）
    private String email;       // 使用者電子郵件（唯一）
    private String role;        // 使用者角色（buyer, seller, admin）

    // Getter & Setter

    /** 取得使用者ID */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /** 取得帳號名稱 */
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    /** 取得密碼 */
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /** 取得電子郵件 */
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /** 取得角色 */
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
