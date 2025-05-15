package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * LogoutController
 * 用途：
 *   - 處理使用者登出請求
 *   - 銷毀當前 Session，並導回首頁
 */
@SuppressWarnings("serial")
@WebServlet("/LogoutController")
public class LogoutController extends HttpServlet {

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // 取得當前 Session（若不存在則不建立新 Session）
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 銷毀 Session（登出）
            session.invalidate();
        }

        // 導回首頁
        response.sendRedirect("index.jsp");
    }
}
