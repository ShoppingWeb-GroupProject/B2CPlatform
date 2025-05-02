package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import model.User;
import service.UserService;

/**
 * RegisterController
 * 用途：
 *   - 處理使用者註冊請求
 *   - 將新使用者資料送入服務層註冊
 */
@SuppressWarnings("serial")
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

        // 取得註冊表單資料
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email    = request.getParameter("email");

        // 封裝成 User 物件
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole("buyer"); // 預設新註冊的帳號為 buyer

        // 呼叫服務層執行註冊
        boolean success = userService.register(user);

        if (success) {
            // 註冊成功 → 顯示成功訊息，導向登入頁
            request.setAttribute("message", "註冊成功，請登入！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // 註冊失敗 → 顯示錯誤訊息，留在註冊頁
            request.setAttribute("error", "註冊失敗，請稍後再試。");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
