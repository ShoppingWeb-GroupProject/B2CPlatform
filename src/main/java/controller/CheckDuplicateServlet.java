package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/CheckDuplicateServlet")
public class CheckDuplicateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 設定回應類型為 JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String field = request.getParameter("field");
        String value = request.getParameter("value");

        boolean exists = false;
        String message = "";

        if ("username".equals(field)) {
            exists = userService.usernameExists(value);
            message = exists ? "帳號已存在" : "帳號可用";
        } else if ("email".equals(field)) {
            exists = userService.emailExists(value);
            message = exists ? "Email 已存在" : "Email 可用";
        } else if ("phone".equals(field)) {
            exists = userService.phoneExists(value);
            message = exists ? "手機已存在" : "手機可用";
        } else {
            message = "未知欄位";
        }

        String json = String.format("{\"status\": \"%s\", \"message\": \"%s\"}",
                exists ? "exists" : "ok",
                message);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
