package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/LogoutController")
public class LogoutController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        // 銷毀 Session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 導回首頁
        response.sendRedirect("index.jsp");
    }
}
