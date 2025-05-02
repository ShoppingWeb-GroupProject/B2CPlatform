package service;

import dao.UserDAO;
import model.User;

/**
 * UserService
 * 用途：
 *   - 負責使用者相關的業務邏輯
 *   - 包含註冊、登入、查詢帳號等
 */
public class UserService {

    private UserDAO userDAO = new UserDAO();

    /**
     * 使用者登入
     * @param username 帳號
     * @param password 密碼
     * @return User 物件（登入成功）或 null（登入失敗）
     */
    public User login(String username, String password) {
        return userDAO.findByUsernameAndPassword(username, password);
    }

    /**
     * 使用者註冊
     * @param user 傳入要註冊的 User 物件
     * @return true = 註冊成功；false = 註冊失敗
     */
    public boolean register(User user) {
        // 確認帳號是否已存在
        User existingUser = userDAO.findByUsername(user.getUsername());
        if (existingUser != null) {
            System.out.println("帳號已存在：" + user.getUsername());
            return false;
        }
        // 設定預設角色（如果沒帶入的話）
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("buyer");
        }
        return userDAO.addUser(user);
    }

    /**
     * 查詢使用者 ID（依帳號）
     * @param username 帳號
     * @return userId（找不到則回傳 -1）
     */
    public int getUserIdByUsername(String username) {
        return userDAO.findUserIdByUsername(username);
    }

    /**
     * 查詢使用者資訊（依帳號）
     * @param username 帳號
     * @return User 物件
     */
    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    /**
     * 更新使用者資料（不含密碼）
     * @param user 傳入要更新的 User 物件
     * @return true = 更新成功；false = 更新失敗
     */
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    /**
     * 刪除使用者（依 ID）
     * @param userId 使用者 ID
     * @return true = 刪除成功；false = 刪除失敗
     */
    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }
}
