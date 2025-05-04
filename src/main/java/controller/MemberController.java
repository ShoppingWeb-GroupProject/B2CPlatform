package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.UserDAO;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // 當 action 為 null 或 list 時都顯示列表
        if (action == null || "list".equals(action)) {
            List<User> userList = userDAO.getAllUsers();
            request.setAttribute("users", userList);
            request.getRequestDispatcher("/memberList.jsp").forward(request, response);

        } else if ("blacklist".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("id"));
            boolean isBlacklisted = Boolean.parseBoolean(request.getParameter("status"));
            userDAO.updateBlacklistStatus(userId, isBlacklisted);
            response.sendRedirect("MemberController?action=list");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    // 同時把所有 POST 請求也導給 doGet
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

}
