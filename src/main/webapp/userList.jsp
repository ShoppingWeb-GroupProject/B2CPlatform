<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/templates/header.jsp"%>

<div class="container mt-5">

	<div class="grid-container grid-header"
		style="grid-template-columns: 1fr 4fr repeat(3, 2fr) 4fr 2fr;">
		<!-- <div>ID</div> -->
		<div>帳號</div>
		<div>Email</div>
		<div>角色</div>
		<div>黑名單</div>
		<div>電話</div>
		<div>地址</div>
		<div>操作</div>
	</div>

	<!-- 使用者資料 -->
	<c:forEach var="user" items="${users}">
		<div class="grid-container"
			style="grid-template-columns: 1fr 4fr repeat(3, 2fr) 4fr 2fr;">
			<!-- <div>${user.id}</div> -->
			<div>${user.username}</div>
			<div>${user.email}</div>
			<form action="UserController" method="get" style="display: inline;">
					<input type="hidden" name="action" value="updateRole" /> <input
						type="hidden" name="id" value="${user.id}" /> <select class="btn"
						name="role">
						<option value="buyer"
							<c:if test="${user.role == 'buyer'}">selected</c:if>>買家</option>
						<option value="seller"
							<c:if test="${user.role == 'seller'}">selected</c:if>>賣家</option>
						<option value="admin"
							<c:if test="${user.role == 'admin'}">selected</c:if>>管理員</option>
					</select>
					<button type="submit" class="btn">更新</button>
				</form>
			<div>
				<c:choose>
					<c:when test="${user.blacklisted}">是</c:when>
					<c:otherwise>否</c:otherwise>
				</c:choose>
			</div>
			<div>${user.phone}</div>
			<div>${user.address}</div>
			<div>
				<!-- 黑名單切換 -->
				<div>
					<c:choose>
						<c:when test="${user.blacklisted}">
							<a class="btn"
								href="UserController?action=blacklist&id=${user.id}&status=false">移除黑名單</a>
						</c:when>
						<c:otherwise>
							<a class="btn"
								href="UserController?action=blacklist&id=${user.id}&status=true">加入黑名單</a>
						</c:otherwise>
					</c:choose>
				</div>

				<!-- 角色更新 -->
				

				<!-- 折扣管理 -->
				<div>
					<a class="btn" href="DiscountController?username=${user.username}">折扣</a>
				</div>

				<!-- 刪除 -->
				<div>
					<a class="btn" href="UserController?action=delete&id=${user.id}"
						onclick="return confirm('確定要刪除此用戶？');">刪除</a>
				</div>
			</div>
		</div>
	</c:forEach>
</div>

<%@ include file="/templates/footer.jsp"%>