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

	<h2 style="margin-bottom:10px;">訂單管理</h2>

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
			<c:choose>
				<c:when test="${sessionScope.role == 'buyer'}">
					<div class="grid-container grid-header"
						style="grid-template-columns: repeat(7, minmax(120px, 1fr));">
						<div>訂單編號</div>
						<div>總金額</div>
						<div>送達地址</div>
						<div>狀態</div>
						<div>下單時間</div>
						<div></div>
						<div>明細</div>
					</div>
				</c:when>
				<c:when test="${sessionScope.role == 'seller'}">
						<div class="grid-container grid-header"
						style="grid-template-columns: repeat(8, minmax(120px, 1fr));">
						<div>訂單編號</div>
						<div>買家名稱</div>
						<div>總金額</div>
						<div>送達地址</div>
						<div>狀態</div>
						<div>下單時間</div>
						<div></div>
						<div>明細</div>
					</div>
				</c:when>
			</c:choose>
			<!-- 資料列 -->
			<c:forEach var="order" items="${orders}">
			<c:choose>
				<c:when test="${sessionScope.role == 'buyer'}">
					<div class="grid-container"
						style="grid-template-columns: repeat(7, minmax(120px, 1fr));">
						<div>${order.id}</div>
						<div>${order.totalAmount}</div>
						<div>${order.address}</div>
						<div>${order.status}</div>
						<div>${order.createdAt}</div>
						<!-- 操作按鈕 -->
						<form action="OrderUpdateController" method="post"
							style="display: inline;">
							<div>
								<c:choose>
									<c:when test="${order.status == 'pending'}">
										<input type="hidden" name="orderId" value="${order.id}">
										<input type="hidden" name="action" value="cancel">
										<button class="btn" type="submit">取消訂單</button>
									</c:when>
									<c:when test="${order.status == 'shipped'}">
										<input type="hidden" name="orderId" value="${order.id}">
										<input type="hidden" name="action" value="complete">
										<button class="btn" type="submit">確認完成</button>
									</c:when>
								</c:choose>
							</div>
						</form>
	
						<!-- 查看明細 -->
						<div>
							<form action="OrderDetailController" method="get"
								style="display: inline;">
								<input type="hidden" name="orderId" value="${order.id}">
								<button class="btn">查看明細</button>
							</form>
						</div>
					</div>
				</c:when>
				<c:when test="${sessionScope.role == 'seller'}">
					<div class="grid-container"
						style="grid-template-columns: repeat(8, minmax(120px, 1fr));">
						<div>${order.id}</div>
						<div>${order.buyerName}</div>
						<div>${order.totalAmount}</div>
						<div>${order.address}</div>
						<div>${order.status}</div>
						<div>${order.createdAt}</div>
						<!-- 操作按鈕 -->
						<form action="OrderUpdateController" method="post"
							style="display: inline;">
							<div class="btn">
								<c:choose>
									<c:when test="${order.status == 'pending'}">
										<input type="hidden" name="orderId" value="${order.id}">
										<input type="hidden" name="action" value="ship">
										<input type="submit" value="出貨">
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
				</c:when>
			</c:choose>
			</c:forEach>
		</div>
	</c:if>

</div>
<%@ include file="/templates/footer.jsp"%>