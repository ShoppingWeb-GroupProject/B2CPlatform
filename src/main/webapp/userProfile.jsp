<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="model.User"%>
<%
request.setAttribute("pageTitle", "個人資料管理");
User user = (User) session.getAttribute("user");
if (user == null) {
	response.sendRedirect("login.jsp");
	return;
}
%>
<%@ include file="/templates/header.jsp"%>

<div class="container mt-5">
	<h2 class="mb-4">個人資料</h2>

	<div class="grid-container grid-header"
		style="grid-template-columns: 1fr 3fr;">
		<div>項目</div>
		<div>內容</div>
	</div>

	<form action="UserController" method="post">
		<input type="hidden" name="action" value="updateProfile" />

		<div class="grid-container" style="grid-template-columns: 1fr 3fr;">
			<div>帳號</div>
			<div>
				<strong><%=user.getUsername()%></strong>
			</div>

			<div>Email</div>
			<div>
				<input type="email" name="email" class="form-control"
					value="<%=user.getEmail()%>" readonly />
			</div>

			<div>電話</div>
			<div>
				<input type="text" name="phone" class="form-control"
					value="<%=user.getPhone()%>" readonly />
			</div>

			<div>地址</div>
			<div>
				<input type="text" name="address" class="form-control"
					value="<%=user.getAddress()%>" />
			</div>
		</div>
		<div>
			<button type="submit" class="btn btn-primary mt-2">更新資料</button>
		</div>
	</form>

	<!-- 成功 / 錯誤訊息 -->
	<c:if test="${not empty message}">
		<div class="alert alert-success mt-3">${message}</div>
	</c:if>
	<c:if test="${not empty error}">
		<div class="alert alert-danger mt-3">${error}</div>
	</c:if>


</div>

<%@ include file="/templates/footer.jsp"%>
