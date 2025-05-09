package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.User;
import service.UserService;

import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/VerifyController")
public class VerifyController extends HttpServlet {

    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String inputCode = request.getParameter("code");
        HttpSession session = request.getSession();

        String realCode = (String) session.getAttribute("verifyCode");
        User pendingUser = (User) session.getAttribute("pendingUser");

        if (realCode == null || pendingUser == null) {
            request.setAttribute("msg", "驗證逾時或資料已失效，請重新註冊！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (inputCode.equals(realCode)) {
            // 驗證成功 → 寫入資料庫
            boolean success = userService.register(pendingUser);

            if (success) {
                // 清除暫存資料
                session.removeAttribute("verifyCode");
                session.removeAttribute("pendingUser");

                request.setAttribute("message", "註冊成功，請登入！");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "註冊失敗，請稍後再試！");
                request.getRequestDispatcher("verify.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("msg", "驗證碼錯誤，請重新輸入！");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
        }
    }
}
