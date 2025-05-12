<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<%
request.setAttribute("pageTitle", "訂單明細");
%>
<%@ include file="/templates/header.jsp"%>

<div class="container mt-5">
	<div>訂單編號 ${orderId}</div>

	<!-- ✅ 如果沒有找到訂單明細 -->
	<c:if test="${empty orderItems}">
		<p>查無此訂單明細！</p>
	</c:if>

	<!-- ✅ 如果有訂單明細 -->
	<c:if test="${not empty orderItems}">
		<div class="grid-container grid-header"
			style="grid-template-columns: repeat(4, minmax(120px, 1fr));">
			<!-- 表頭 -->
			<div>商品名稱</div>
			<div>單價</div>
			<div>數量</div>
			<div>小計</div>
		</div>
		<!-- 資料列 -->
		<c:forEach var="item" items="${orderItems}">
			<div class="grid-container"
				style="grid-template-columns: repeat(4, minmax(120px, 1fr));">
				<div>${item.productName}</div>
				<div>${item.price}</div>
				<div>${item.quantity}</div>
				<div>${item.price * item.quantity}</div>
			</div>
		</c:forEach>

	</c:if>

	<br>
	<!-- ✅ 返回訂單列表 -->
	<div class="btn"><a href="OrderController">回訂單列表</a></div>
</div>

<%@ include file="/templates/footer.jsp"%>
