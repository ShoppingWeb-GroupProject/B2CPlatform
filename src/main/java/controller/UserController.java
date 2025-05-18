package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserService;

/**
 * 使用者控制器 處理與使用者帳號、角色、黑名單、註冊、登入相關的 HTTP 請求
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null || "list".equals(action)) {
			// 顯示所有使用者列表
			List<User> userList = userService.getAllUsers();
			request.setAttribute("users", userList);
			request.getRequestDispatcher("/userList.jsp").forward(request, response);

		} else if ("blacklist".equals(action)) {
			// 更新黑名單狀態
			int userId = Integer.parseInt(request.getParameter("id"));
			boolean isBlacklisted = Boolean.parseBoolean(request.getParameter("status"));
			userService.updateBlacklistStatus(userId, isBlacklisted);
			response.sendRedirect("UserController?action=list");

		} else if ("updateRole".equals(action)) {
			// 更新使用者角色
			int userId = Integer.parseInt(request.getParameter("id"));
			String role = request.getParameter("role");
			userService.updateUserRole(userId, role);
			response.sendRedirect("UserController?action=list");

		} else if ("delete".equals(action)) {
			// 刪除使用者
			int userId = Integer.parseInt(request.getParameter("id"));
			userService.deleteUser(userId);
			response.sendRedirect("UserController?action=list");

		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("register".equals(action)) {
			// 註冊新使用者
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");

			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setPhone(phone);
			user.setAddress(address);
			user.setRole("buyer");

			boolean success = userService.register(user);
			if (success) {
				request.setAttribute("message", "註冊成功，請登入！");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "註冊失敗，帳號可能已存在。");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}

		} else if ("login".equals(action)) {
			// 使用者登入
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			User user = userService.login(username, password);
			if (user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect("index.jsp");
			} else {
				request.setAttribute("error", "登入失敗，請檢查帳號密碼。");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}

		} else if ("updateProfile".equals(action)) {
			// 使用者更新個人資料
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			if (user == null) {
				response.sendRedirect("login.jsp");
				return;
			}

			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");

			user.setEmail(email);
			user.setPhone(phone);
			user.setAddress(address);

			boolean success = userService.updateUserProfile(user);
			if (success) {
				session.setAttribute("user", user); // 更新 session 裡的資料
				request.setAttribute("message", "資料更新成功！");
			} else {
				request.setAttribute("error", "資料更新失敗！");
			}

			request.getRequestDispatcher("userProfile.jsp").forward(request, response);
		} else {
			doGet(request, response);
		}
	}
}
