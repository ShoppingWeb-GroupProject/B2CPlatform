package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Random;

import model.User;
import service.UserService;
import util.MailUtil;

@SuppressWarnings("serial")
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username        = request.getParameter("username");
        String password        = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email           = request.getParameter("email");
        String phone           = request.getParameter("phone");
        String address         = request.getParameter("address");

        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "密碼與確認密碼不一致！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (userService.usernameExists(username)) {
            request.setAttribute("error", "該帳號已被註冊，請選擇其他帳號！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (userService.emailExists(email)) {
            request.setAttribute("error", "該 Email 已被註冊，請使用其他 Email！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (userService.phoneExists(phone)) {
            request.setAttribute("error", "該手機號碼已被註冊！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 產生驗證碼
        String verifyCode = String.valueOf(new Random().nextInt(900000) + 100000);

        // 讀取寄信設定（你可改成從 .properties 檔或環境變數讀取）
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587;
        boolean useTLS = true;
        String senderEmail = "testingapi0508@gmail.com";         // ← 改成你要寄出的信箱
        String senderPass = "syxr vrkm quwx yexy";        // ← 應用程式密碼或普通密碼（依平台）

        try {
            String subject = "您的註冊驗證碼";
            String content = "您好，您的驗證碼為：" + verifyCode + "\n請於 10 分鐘內完成驗證。";

            MailUtil.sendEmail(
                smtpHost, smtpPort, useTLS,
                senderEmail, senderPass,
                email, subject, content
            );

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "寄送驗證信失敗，請稍後再試！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 暫存 User 與驗證碼至 Session
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRole("buyer");

        HttpSession session = request.getSession();
        session.setAttribute("pendingUser", user);
        session.setAttribute("verifyCode", verifyCode);

        request.getRequestDispatcher("verify.jsp").forward(request, response);
    }
}
