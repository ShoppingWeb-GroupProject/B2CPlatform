package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import model.User;
import service.UserService;

@SuppressWarnings("serial")
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email    = request.getParameter("email");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        boolean success = userService.register(user);

        if (success) {
        	request.setAttribute("message", "註冊成功，請登入！");
        	request.getRequestDispatcher("register.jsp").forward(request, response);
            response.sendRedirect("login.jsp");
        } else {
        	request.setAttribute("error", "註冊失敗，請稍後再試。");
        	request.getRequestDispatcher("register.jsp").forward(request, response);
            response.getWriter().println("註冊失敗，請稍後再試。");
        }
    }
}
