package service;

import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        return userDAO.findByUsernameAndPassword(username, password);
    }

    public boolean register(User user) {
        return userDAO.save(user);
    }
}
