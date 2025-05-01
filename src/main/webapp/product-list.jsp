<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>商品頁面</title>
</head>
<body>

	<c:if test="${action.contains('show')}">

		<h2>商品列表</h2>
		<table border="1">
			<tr>
				<th>商品名稱</th>
				<th>描述</th>
				<th>分類ID</th>
				<th>價格</th>
				<th>庫存</th>

				<c:if test="${action=='showForSeller'}">
					<th>操作</th>
				</c:if>

			</tr>
			<c:forEach var="product" items="${showProducts}">
				<tr>
					<td>${product.name}</td>
					<td>${product.description}</td>
					<td>${product.categoryId}</td>
					<td>${product.price}</td>
					<td>${product.stock}</td>

					<c:if test="${action=='showForSeller'}">
						<td><a
							href="ProductController?action=modify&productId=${product.id}">編輯</a>
							| <a
							href="ProductController?action=delete&productId=${product.id}">刪除</a>
						</td>
					</c:if>

				</tr>
			</c:forEach>
		</table>

	</c:if>

	<c:if test="${action=='showForSeller'}">
		<c:url var="productAdd" value="ProductController">
			<c:param name="action" value="modify" />
		</c:url>
		<li><a href="${productAdd}">商品新增</a></li>
	</c:if>

	<c:if test="${action == 'modify'}">
		<h2>${product.id != null ? '修改商品' : '新增商品'}</h2>

		<form action="ProductController" method="post">
			<input type="hidden" name="action"
				value="${product.id != null ? 'update' : 'add'}" />

			<c:if test="${product.id != null}">
				<input type="hidden" name="id" value="${product.id}" />
			</c:if>

			<div>
				商品名稱：<input type="text" name="name" value="${product.name}" required />
			</div>

			<div>
				價格：<input type="number" name="price" step="0.01"
					value="${product.price}" required />
			</div>

			<div>
				庫存：<input type="number" name="stock" value="${product.stock}"
					required />
			</div>

			<div>
				商品描述：
				<textarea name="description" rows="4" cols="50">${product.description}</textarea>
			</div>

			<div>
				分類：
				<!-- <select name="categoryId" required>
				<c:forEach var="category" items="${categoryList}">
					<option value="${category.id}"
						${category.id == product.categoryId ? 'selected' : ''}>
						${category.name}</option>
				</c:forEach>
			</select> -->
				<select name="categoryId" required>
					<option value="1" ${product.categoryId == 1 ? 'selected' : ''}>電子產品</option>
					<option value="2" ${product.categoryId == 2 ? 'selected' : ''}>服飾</option>
					<option value="3" ${product.categoryId == 3 ? 'selected' : ''}>書籍</option>
					<option value="4" ${product.categoryId == 4 ? 'selected' : ''}>美妝保養</option>
					<option value="5" ${product.categoryId == 5 ? 'selected' : ''}>家居用品</option>
				</select>
			</div>

			<div>
				<input type="submit" value="${product.id != null ? '更新商品' : '新增商品'}" />
			</div>
		</form>
	</c:if>

</body>
</html>
