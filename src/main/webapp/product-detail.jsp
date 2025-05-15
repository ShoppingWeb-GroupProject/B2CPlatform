<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%
request.setAttribute("pageTitle", "商品詳細資訊");
%>
<%@ include file="/templates/header.jsp"%>

<div class="container mt-5">
	<div class="row">
		<div class="col-md-6">
			<img src="${product.imageUrl}" alt="${product.name}"
				class="img-fluid">
		</div>
		<div class="col-md-6">
			<h2>${product.name}</h2>
			<p>
				<strong>價格：</strong>${product.price}</p>
			<p>
				<strong>庫存：</strong>${product.stock}</p>
			<p>
				<strong>分類：</strong>
				<c:forEach var="category" items="${categories}">
					<c:if test="${category.id == product.categoryId}">${category.name}</c:if>
				</c:forEach>
			</p>
			<p>
				<strong>描述：</strong>${product.description}</p>

			<c:if test="${action == 'show'}">
				<form action="CartItemController" method="post">
					<input type="hidden" name="productId" value="${product.id}" /> 
					<label for="quantity">
					購買數量：</label> <input type="number" id="quantity" name="quantity" min="1" max="${product.stock}" value="1" required />
					<button type="submit">加入購物車</button>
				</form>
			</c:if>
		</div>
	</div>
</div>

<%@ include file="/templates/footer.jsp"%>