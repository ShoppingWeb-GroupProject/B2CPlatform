package service;

import java.util.List;

import dao.UserDAO;
import model.User;

/**
 * 使用者服務層
 * 負責處理使用者的帳號、密碼、角色、黑名單等邏輯
 */
public class UserService {

    private UserDAO userDAO = new UserDAO();

    public boolean updateLineId(int userId, String lineId) {
        return userDAO.updateLineId(userId, lineId);
    }


    /**
     * 使用者登入驗證
     */
    public User login(String username, String password) {
        return userDAO.findByUsernameAndPassword(username, password);
    }

    /**
     * 註冊新使用者
     */
    public boolean register(User user) {
        if (usernameExists(user.getUsername())) {
            System.out.println("帳號已存在：" + user.getUsername());
            return false;
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("buyer"); // 預設角色
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
     * 查詢使用者資料（依帳號）
     */
    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    /**
     * 更新使用者基本資料
     */
    public boolean updateUserProfile(User user) {
        return userDAO.updateUser(user);
    }

    /**
     * 刪除使用者
     */
    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    /**
     * 查詢所有使用者
     */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * 更新黑名單狀態
     */
    public boolean updateBlacklistStatus(int userId, boolean isBlacklisted) {
        return userDAO.updateBlacklistStatus(userId, isBlacklisted);
    }

    /**
     * 更新使用者角色
     */
    public boolean updateUserRole(int userId, String role) {
        return userDAO.updateUserRole(userId, role);
    }

    /**
     * 檢查帳號是否已存在
     */
    public boolean usernameExists(String username) {
        return userDAO.findByUsername(username) != null;
    }

    /**
     * 檢查 Email 是否已存在
     */
    public boolean emailExists(String email) {
        return userDAO.emailExists(email);
    }

    /**
     * 檢查手機是否已存在
     */
    public boolean phoneExists(String phone) {
        return userDAO.phoneExists(phone);
    }
}
