<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%
request.setAttribute("pageTitle", "訂單管理");
%>
<%@ include file="/templates/header.jsp"%>

<!-- ✅ 檢查是否登入與有角色，否則導向登入頁 -->
<c:if test="${empty sessionScope.username or empty sessionScope.role}">
	<c:redirect url="login.jsp" />
</c:if>

<div class="container mt-5">

	<div>訂單管理</div>

	<div>
		使用者：${sessionScope.username}（
		<c:choose>
			<c:when test="${sessionScope.role == 'buyer'}">買家</c:when>
			<c:when test="${sessionScope.role == 'seller'}">賣家</c:when>
			<c:otherwise>⚠ 無效角色</c:otherwise>
		</c:choose>
		）
	</div>

	<!-- ✅ 沒有訂單時的顯示 -->
	<c:if test="${empty orders}">
		<p>尚無訂單。</p>
	</c:if>

	<!-- ✅ 有訂單時的 Grid 顯示 -->
	<c:if test="${not empty orders}">
		<div class="order-section">
			<!-- 表頭 -->
			<div class="grid-container grid-header"
				style="grid-template-columns: repeat(6, minmax(120px, 1fr));">
				<div>訂單編號</div>

				<c:choose>
					<c:when test="${sessionScope.role == 'buyer'}">
						<div>總金額</div>
					</c:when>
					<c:when test="${sessionScope.role == 'seller'}">
						<div>買家名稱</div>
						<div>總金額</div>
					</c:when>
				</c:choose>

				<div>狀態</div>
				<div>下單時間</div>
				<div>操作</div>
				<div>明細</div>
			</div>

			<!-- 資料列 -->
			<c:forEach var="order" items="${orders}">
				<div class="grid-container"
					style="grid-template-columns: repeat(6, minmax(120px, 1fr));">
					<div>${order.id}</div>

					<c:choose>
						<c:when test="${sessionScope.role == 'buyer'}">
							<div>${order.totalAmount}</div>
						</c:when>
						<c:when test="${sessionScope.role == 'seller'}">
							<div>${order.buyerName}</div>
							<div>${order.totalAmount}</div>
						</c:when>
					</c:choose>

					<div>${order.status}</div>
					<div>${order.createdAt}</div>

					<!-- 操作按鈕 -->
					<form action="OrderUpdateController" method="post"
						style="display: inline;">
						<div class="btn">
							<c:choose>
								<c:when
									test="${sessionScope.role == 'buyer' && order.status == 'pending'}">
									<input type="hidden" name="orderId" value="${order.id}">
									<input type="hidden" name="action" value="cancel">
									<input type="submit" value="取消訂單">
								</c:when>
								<c:when
									test="${sessionScope.role == 'seller' && order.status == 'pending'}">
									<input type="hidden" name="orderId" value="${order.id}">
									<input type="hidden" name="action" value="ship">
									<input type="submit" value="出貨">

								</c:when>
								<c:when
									test="${sessionScope.role == 'buyer' && order.status == 'shipped'}">
									<input type="hidden" name="orderId" value="${order.id}">
									<input type="hidden" name="action" value="complete">
									<input type="submit" value="確認完成">
								</c:when>
							</c:choose>
						</div>
					</form>

					<!-- 查看明細 -->
					<div class="btn">
						<form action="OrderDetailController" method="get"
							style="display: inline;">
							<input type="hidden" name="orderId" value="${order.id}">
							<input type="submit" value="查看明細">
						</form>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:if>

</div>
<%@ include file="/templates/footer.jsp"%>
