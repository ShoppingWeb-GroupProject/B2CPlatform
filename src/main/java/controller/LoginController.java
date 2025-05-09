package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import service.UserService;
import model.User;

/**
 * LoginController
 * 用途：
 *   - 處理使用者登入請求
 *   - 驗證帳號與密碼後，將登入資訊寫入 session
 */
@SuppressWarnings("serial")
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    // 使用 UserService 來處理登入邏輯
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

        // 取得表單提交的帳號與密碼
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 呼叫服務層驗證使用者
        User user = userService.login(username, password);

        if (user != null) {
            // 登入成功：將使用者資訊存入 session
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            // 導向首頁
            response.sendRedirect("index.jsp");
        } else {
            // 登入失敗：回傳錯誤訊息並留在登入頁
            request.setAttribute("error", "帳號或密碼錯誤");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
