<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setAttribute("pageTitle", "設定會員折扣");
%>
<%@ include file="/templates/header.jsp"%>
<div class="container mt-5">
	<h2 style="margin-bottom: 10px;">會員折扣設定</h2>

	<!-- 顯示錯誤或成功訊息 -->
	<c:if test="${not empty message}">
		<p class="message">${message}</p>
	</c:if>
	<c:if test="${not empty error}">
		<p class="error">${error}</p>
	</c:if>

	<form class="form-grid" action="DiscountController" method="post">
		<label>會員帳號：</label> <input type="text" name="username"
			value="${username}" required>

		<c:if test="${not empty discount}">
			<div class="form-actions">
				<p>目前折扣名稱：${discount.name}</p>
				<p>目前折扣描述：${discount.description}</p>
				<p>目前折扣值：${discount.discountValue}</p>
				<p>
					啟用狀態：
					<c:choose>
						<c:when test="${discount.active}">啟用中</c:when>
						<c:otherwise>未啟用</c:otherwise>
					</c:choose>
				</p>
			</div>
		</c:if>
<label>設定新折扣值（例：0.9 表示九折）：</label>
<input type="text" name="discount" required>

<button class="btn form-actions" type="submit">更新折扣</button>
</form>

<div>
	<a href="UserController?action=list">回會員管理列表</a>
</div>
</div>
<%@ include file="/templates/footer.jsp"%>