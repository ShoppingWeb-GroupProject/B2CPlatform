<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
request.setAttribute("pageTitle", "購物車");
%>
<%@ include file="/templates/header.jsp"%>

<div class="container mt-5">
	<c:if test="${empty sessionScope.username}">
		<c:redirect url="login.jsp" />
	</c:if>

	<div class="cart-wrapper">
		<h2>🛒 購物車頁面</h2>
		<p>歡迎，${sessionScope.username}，這是您的購物車。</p>

		<c:choose>
			<c:when test="${empty cartItems}">
				<p>目前購物車是空的。</p>
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

				<p class="total">
					<strong>總金額：</strong> $${total}
				</p>

				<!-- 建立訂單 -->
				<form action="OrderController" method="post">
<<<<<<< HEAD
					<label for="address">收件地址：</label> <input type="text" id="address" name="address" required />
=======
					<label for="address">收件地址：</label> <input type="text" id="address"
						name="address" value="${address}" required />
>>>>>>> 1ed5c80861f7df6cbdb923c5d44584819f32fcd0
					<button class="btn" type="submit">建立訂單</button>
				</form>
			</c:otherwise>
		</c:choose>
	</div>

</div>
<%@ include file="/templates/footer.jsp"%>