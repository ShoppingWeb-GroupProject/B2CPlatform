package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import model.User;
import service.UserService;

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 取得註冊表單資料
        String username        = request.getParameter("username");
        String password        = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email           = request.getParameter("email");
        String phone           = request.getParameter("phone");
        String address         = request.getParameter("address");

        // 儲存輸入資料，方便回填
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);

        // 密碼一致性檢查
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "密碼與確認密碼不一致！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 檢查帳號是否已存在
        if (userService.usernameExists(username)) {
            request.setAttribute("error", "該帳號已被註冊，請選擇其他帳號！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 檢查 Email 是否已存在
        if (userService.emailExists(email)) {
            request.setAttribute("error", "該 Email 已被註冊，請使用其他 Email！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 檢查手機號碼是否已存在
        if (userService.phoneExists(phone)) {
            request.setAttribute("error", "該手機號碼已被註冊！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 封裝成 User 物件
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRole("buyer"); // 預設新註冊的帳號為 buyer

        // 呼叫服務層執行註冊
        boolean success = userService.register(user);

        if (success) {
            request.setAttribute("message", "註冊成功，請登入！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "註冊失敗，請稍後再試。");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
