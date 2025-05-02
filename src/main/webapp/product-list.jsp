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
	grid-template-columns: repeat(6, 1fr);
}

.grid-ForSeller {
	grid-template-columns: repeat(6, 1fr);
}

.form-grid {
	display: grid;
	grid-template-columns: 150px 1fr;
	gap: 10px 15px;
	align-items: center;
	max-width: 600px;
	margin-bottom: 1em;
}

.form-grid textarea {
	width: 100%;
}

.form-actions {
	grid-column: 1/-1;
	text-align: right;
}
</style>

</head>
<body>
	<c:if test="${action.contains('show')}">
		<h2>商品列表</h2>
		<div
			class="grid-header ${action == 'showForSeller' ? 'grid-ForSeller' : 'grid-all'}">
			<div>商品名稱</div>
			<div>描述</div>
			<div>分類ID</div>
			<div>價格</div>
			<div>庫存</div>
			<div>操作</div>
		</div>

		<c:forEach var="product" items="${showProducts}">
			<div
				class="grid-row ${action == 'showForSeller' ? 'grid-ForSeller' : 'grid-all'}">
				<div>${product.name}</div>
				<div>${product.description}</div>
				<div>${product.categoryId}</div>
				<div>${product.price}</div>
				<div>${product.stock}</div>
				<c:if test="${action=='showForSeller'}">
					<div>
						<a href="ProductController?action=modify&productId=${product.id}"><button>編輯</button></a>
						<a href="ProductController?action=delete&productId=${product.id}"><button>刪除</button></a>
					</div>
				</c:if>
				<c:if test="${action=='show'}">
					<div>
						<form action="CartItemController" method="post">
							<input type="hidden" name="productId" value="${product.id}" /> <label
								for="quantity">購買數量：</label> <input type="number" id="quantity"
								name="quantity" min="1" value="${item.quantity}" required />
							<button type="submit">加入購物車</button>
							</a>
						</form>
					</div>
				</c:if>
			</div>
		</c:forEach>
	</c:if>


	<c:if test="${action == 'modify'}">
		<h2>${product.id != null ? '修改商品' : '新增商品'}</h2>

		<form action="ProductController" method="post">
			<input type="hidden" name="action"
				value="${product.id != null ? 'update' : 'add'}" />
			<c:if test="${product.id != null}">
				<input type="hidden" name="id" value="${product.id}" />
			</c:if>

			<div class="form-grid">
				<label for="name">商品名稱：</label> <input type="text" id="name"
					name="name" value="${product.name}" required /> <label for="price">價格：</label>
				<input type="number" id="price" name="price" step="0.01"
					value="${product.price}" required /> <label for="stock">庫存：</label>
				<input type="number" id="stock" name="stock"
					value="${product.stock}" required /> <label for="description">商品描述：</label>
				<textarea id="description" name="description" rows="4">${product.description}</textarea>

				<label for="categoryId">分類：</label> <select id="categoryId"
					name="categoryId" required>
					<option value="1" ${product.categoryId == 1 ? 'selected' : ''}>電子產品</option>
					<option value="2" ${product.categoryId == 2 ? 'selected' : ''}>服飾</option>
					<option value="3" ${product.categoryId == 3 ? 'selected' : ''}>書籍</option>
					<option value="4" ${product.categoryId == 4 ? 'selected' : ''}>美妝保養</option>
					<option value="5" ${product.categoryId == 5 ? 'selected' : ''}>家居用品</option>
				</select>

				<div class="form-actions">
					<input type="submit"
						value="${product.id != null ? '更新商品' : '新增商品'}" />
				</div>
			</div>
		</form>

	</c:if>

</body>
</html>
