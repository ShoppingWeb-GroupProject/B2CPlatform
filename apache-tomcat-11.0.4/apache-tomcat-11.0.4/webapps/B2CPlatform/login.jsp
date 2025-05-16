<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%
request.setAttribute("pageTitle", "登入");
%>
<%@ include file="/templates/header.jsp"%>

<div class="container mt-5">
	<h2>登入</h2>

	<!-- ✅ 登入表單 -->
	<form action="LoginController" method="post">
		<label> 帳號： <input type="text" name="username" required>
		</label><br> <label> 密碼： <input type="password" name="password"
			required>
		</label><br> <input type="submit" value="登入">
	</form>

	<!-- ✅ 顯示錯誤訊息（若有） -->
	<c:if test="${not empty error}">
		<p style="color: red;">${error}</p>
	</c:if>

	<!-- ✅ 顯示一般提示訊息（如註冊成功提示） -->
	<c:if test="${not empty message}">
		<p style="color: green;">${message}</p>
	</c:if>

</div>
<%@ include file="/templates/footer.jsp"%>