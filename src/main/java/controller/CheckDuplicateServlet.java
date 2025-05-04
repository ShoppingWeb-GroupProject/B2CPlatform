package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/CheckDuplicateServlet")
public class CheckDuplicateServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 設定回應類型為純文字
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // 取得欄位名稱與值
        String field = request.getParameter("field");
        String value = request.getParameter("value");

        boolean exists = false;

        // 根據欄位類型判斷是否存在
        if ("username".equals(field)) {
            exists = userService.usernameExists(value);
        } else if ("email".equals(field)) {
            exists = userService.emailExists(value);
        } else if ("phone".equals(field)) {
            exists = userService.phoneExists(value);
        }

        // 回傳結果
        if (exists) {
            out.print("exists");
        } else {
            out.print("ok");
        }

        out.flush();
        out.close();
    }
}
