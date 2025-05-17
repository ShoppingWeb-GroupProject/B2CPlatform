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
			<img src="${product.imageUrl}" alt="${product.name}" class="img-fluid">
		</div>
		<div class="col-md-6">
			<h2>${product.name}</h2>
			<p><strong>價格：</strong>${product.price}</p>
			<p><strong>庫存：</strong>${product.stock}</p>
			<p><strong>分類：</strong>
				<c:forEach var="category" items="${categories}">
					<c:if test="${category.id == product.categoryId}">${category.name}</c:if>
				</c:forEach>
			</p>
			<p><strong>描述：</strong>${product.description}</p>

			<c:if test="${action == 'show'}">
				<form action="CartItemController" method="post">
					<input type="hidden" name="productId" value="${product.id}" /> 
					<label for="quantity">購買數量：</label>
					<input type="number" id="quantity" name="quantity" min="1" max="${product.stock}" value="1" required />
					<button type="submit">加入購物車</button>
				</form>
			</c:if>
		</div>
	</div>

	<!-- 評論表單 -->
	<form action="ReviewController" method="post" class="mt-4">
	    <input type="hidden" name="productId" value="${product.id}" />
	    <label for="rating">評分：</label>
	    <select name="rating" id="rating">
	        <c:forEach begin="1" end="5" var="i">
	            <option value="${i}">${i} 星</option>
	        </c:forEach>
	    </select><br/>
	    <label for="comment">評論：</label><br/>
	    <textarea name="comment" rows="4" cols="50"></textarea><br/>
	    <button type="submit">送出評論</button>
	</form>

	<hr/>
	<h3>用戶評論：</h3>
	<c:forEach var="review" items="${reviews}">
	    <p><strong>${review.username}</strong>：${review.rating} ⭐</p>
	    <p>${review.comment}</p>

	    <!-- 如果是本人評論，顯示刪除按鈕 -->
	<c:if test="${sessionScope.user.id == review.userId}">
    <form action="ReviewController" method="post" style="display:inline;">
        <input type="hidden" name="action" value="delete" />
        <input type="hidden" name="reviewId" value="${review.id}" />
        <input type="hidden" name="productId" value="${product.id}" />
        <button type="submit" 
            style="background-color: #dc3545; color: white; border: none; 
                   padding: 2px 8px; font-size: 12px; border-radius: 4px; 
                   float: right; margin-top: -2em; margin-left: 10px; cursor: pointer;">
            刪除
        </button>
    </form>
</c:if>




	    <hr/>
	</c:forEach>
</div>

<%@ include file="/templates/footer.jsp"%>
