<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>商品頁面</title>
<style>
	.grid-header, .grid-row {
		display: grid;
		gap: 10px;
		padding: 10px 0;
		border-bottom: 1px solid #ccc;
	}
	.grid-header {
		font-weight: bold;
		background-color: #f5f5f5;
	}
	.grid-row {
		border-color: #eee;
	}
	.grid-all {
		grid-template-columns: repeat(5, 1fr);
	}
	.grid-ForSeller {
		grid-template-columns: repeat(6, 1fr);
	}
</style>

</head>
<body>

	<h2>商品列表</h2>
<div class="grid-header ${action == 'showForSeller' ? 'grid-ForSeller' : 'grid-all'}">
	<div>商品名稱</div>
	<div>描述</div>
	<div>分類ID</div>
	<div>價格</div>
	<div>庫存</div>
	<c:if test="${action=='showForSeller'}">
		<div>操作</div>
	</c:if>
</div>

<c:forEach var="product" items="${showProducts}">
	<div class="grid-row ${action == 'showForSeller' ? 'grid-ForSeller' : 'grid-all'}">
		<div>${product.name}</div>
		<div>${product.description}</div>
		<div>${product.categoryId}</div>
		<div>${product.price}</div>
		<div>${product.stock}</div>
		<c:if test="${action=='showForSeller'}">
			<div>
				<a href="ProductController?action=modify&productId=${product.id}">編輯</a>
				| 
				<a href="ProductController?action=delete&productId=${product.id}">刪除</a>
			</div>
		</c:if>
	</div>
</c:forEach>


</body>
</html>
