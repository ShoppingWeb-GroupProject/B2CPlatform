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
     */
    public User login(String username, String password) {
        return userDAO.findByUsernameAndPassword(username, password);
    }

    /**
     * 使用者註冊
     */
    public boolean register(User user) {
        User existingUser = userDAO.findByUsername(user.getUsername());
        if (existingUser != null) {
            System.out.println("帳號已存在：" + user.getUsername());
            return false;
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("buyer");
        }

        return userDAO.addUser(user);
    }

    /**
     * 查詢使用者 ID（依帳號）
     */
    public int getUserIdByUsername(String username) {
        return userDAO.findUserIdByUsername(username);
    }

    /**
     * 查詢使用者資訊（依帳號）
     */
    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    /**
     * 更新使用者資料
     */
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    /**
     * 刪除使用者
     */
    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    /**
     * 查詢帳號是否已存在
     */
    public boolean usernameExists(String username) {
        return userDAO.findByUsername(username) != null;
    }

    /**
     * 查詢 email 是否已存在
     */
    public boolean emailExists(String email) {
        return userDAO.emailExists(email);
    }

    /**
     * 查詢 phone 是否已存在
     */
    public boolean phoneExists(String phone) {
        return userDAO.phoneExists(phone);
    }
}
