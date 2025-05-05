<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
    request.setAttribute("pageTitle", "首頁");
%>
<%@ include file="/templates/header.jsp"%>

<h1>ProjectShop 首頁</h1>

<c:choose>
	<c:when test="${not empty sessionScope.username}">
		<p>歡迎，${sessionScope.username}！</p>
		<ul>
			<li><a href="CartItemController">購物車</a></li>
			<li><a href="OrderController">訂單查詢</a></li>

			<c:url var="productShow" value="ProductController">
				<c:param name="action" value="show" />
			</c:url>
			<c:url var="productShowSeller" value="ProductController">
				<c:param name="action" value="showForSeller" />
			</c:url>

			<li><a href="${productShow}">逛逛商品</a></li>
			<li><a href="${productShowSeller}">我的商品</a></li>

			<li><a href="LogoutController">登出</a></li>
		</ul>
	</c:when>
	<c:otherwise>
		<p>請先登入或註冊帳號：</p>
		<ul>
			<li><a href="register.jsp">註冊</a></li>
			<li><a href="login.jsp">登入</a></li>
		</ul>
	</c:otherwise>
</c:choose>

<hr>

	<h2>熱銷商品（未來可接資料庫動態列出）</h2>
	<p>目前是靜態測試區。</p>

<%@ include file="/templates/footer.jsp"%>