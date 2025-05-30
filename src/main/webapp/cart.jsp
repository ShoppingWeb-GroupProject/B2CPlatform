<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
request.setAttribute("pageTitle", "購物車");
%>
<%@ include file="/templates/header.jsp"%>

<c:if test="${not empty paymentError}">
    <script>
        alert("${paymentError}");
    </script>
</c:if>

<div class="container mt-5">
	<c:if test="${empty sessionScope.username}">
		<c:redirect url="login.jsp" />
	</c:if>

	<div class="cart-wrapper">
		<h2>🛒 購物車頁面</h2>
		<p class="total">歡迎，${sessionScope.username}，這是您的購物車。</p>

		<c:choose>
			<c:when test="${empty cartItems}">
				<p class="total">目前購物車是空的。</p>
			</c:when>
			<c:otherwise>
				<!-- 表頭 -->
				<div class="cart-container">
					<div>商品名稱</div>
					<div>價格</div>
					<div>數量</div>
					<div>小計</div>
					<div></div>
				</div>

				<c:set var="total" value="0" />
				<c:forEach var="item" items="${cartItems}">
					<c:set var="subtotal" value="${item.price * item.quantity}" />
					<c:set var="total" value="${total + subtotal}" />
					<div class="cart-item">
						<div>${item.productName}</div>
						<div>$${item.price}</div>
						<div>${item.quantity}</div>
						<div>$${subtotal}</div>
						<div>
							<form action="CartItemController" method="post"
								class="remove-form">
								<input type="hidden" name="action" value="remove" /> <input
									type="hidden" name="productId" value="${item.productId}" />
								<button class="btn" type="submit">移除</button>
							</form>
						</div>
					</div>
				</c:forEach>
				<p class="total">會員等級：
				    <c:choose>
				        <c:when test="${user.discount == 1.0}">普通會員（無折扣）</c:when>
				        <c:when test="${user.discount == 0.9}">銀級會員（9 折）</c:when>
				        <c:when test="${user.discount == 0.8}">金級會員（8 折）</c:when>
				        <c:otherwise>其他（${user.discount}）</c:otherwise>
				    </c:choose>
				</p>
				
				<p class="total">
					    <strong>原始總金額：</strong> $${total} <br>
   						<strong>折扣後總金額：</strong> $${discountedTotal}				
				</p>
				
				<!-- 建立訂單 -->			
				<form action="PaymentController" method="get">

					<label class="total" for="address">收件地址：</label> <input type="text" id="address"
						name="address" value="${address}" required />
					<input type="hidden" name="amount" value="${discountedTotal}" />
					<button class="btn" type="submit">前往付款</button>
				</form>
			</c:otherwise>
		</c:choose>
	</div>

</div>

<%@ include file="/templates/footer.jsp"%>