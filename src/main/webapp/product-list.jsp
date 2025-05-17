<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
request.setAttribute("pageTitle", "首頁");
%>
<%@ include file="/templates/header.jsp"%>

<!-- ***** Preloader Start ***** -->
<div id="preloader">
	<div class="jumper">
		<div></div>
		<div></div>
		<div></div>
	</div>
</div>
<!-- ***** Preloader End ***** -->

<!-- ***** Main Banner Area Start ***** -->
<div class="page-heading" id="top">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="inner-content">
					<h2>Check Our Products</h2>
					<!-- <span>Awesome &amp; Creative HTML CSS layout by TemplateMo</span>-->
				</div>
			</div>
		</div>
	</div>
</div>
<!-- ***** Main Banner Area End ***** -->

<div class="container mt-5">
<!-- ***** Products Area Starts ***** -->

<c:if test="${action=='show'}">
	<section class="section" id="products">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="section-heading">
						<h2>Our Latest Products</h2>
						<span>Check out all of our products.</span>
					</div>
				</div>
			</div>
		</div>
		<div class="grid-container grid-row" style="grid-template-columns: repeat(3, 1fr)" >
			<c:forEach var="product" items="${showProducts}">
				<div class="item">
					<div class="thumb">
						<a href="ProductController?action=detail&productId=${product.id}">
							<img src="${product.imageUrl}" alt="${product.name}">
						</a>
					</div>
					<div class="down-content">
						<h4>${product.name}</h4>
						<span>${product.price}</span>
					</div>
				</div>

			</c:forEach>
		</div>
	</section>
</c:if>

<c:if test="${action=='showForSeller'}">
	<div class="btn"><a href="ProductController?action=modify">新增商品</a></div>
	<div
		class="grid-container grid-header"  style="grid-template-columns: repeat(7, 1fr);">
		<div>商品名稱</div>
		<div>商品圖</div>
		<div>描述</div>
		<div>分類</div>
		<div>價格</div>
		<div>庫存</div>
		<div>操作</div>
	</div>

	<c:forEach var="product" items="${showProducts}">
		<div
			class="grid-container"  style="grid-template-columns: repeat(7, 1fr);">
			<div>${product.name}</div>
			<div><img src="${product.imageUrl}" alt="${product.name}" class="img-fluid"></div>
			<div>
			 ${product.description.length() < 10 ? product.description : product.description.substring(0, 20)}...
			</div>
			 <div>
			<c:forEach var="category" items="${categories}">
                        <c:if test="${category.id == product.categoryId}">${category.name}</c:if>
            </c:forEach>
            </div>
			<div>${product.price}</div>
			<div>${product.stock}</div>
			<c:if test="${action=='showForSeller'}">
				<div>
					<div class="btn"><a href="ProductController?action=modify&productId=${product.id}">編輯</a></div>
					<div class="btn"><a href="ProductController?action=delete&productId=${product.id}">刪除</a></div>
					<div class="btn"><a href="ProductController?action=detail&productId=${product.id}">查看評論</a></div>
				</div>
			</c:if>
		</div>
	</c:forEach>
</c:if>

<c:if test="${action == 'modify'}">
    <form action="ProductController" method="post" enctype="multipart/form-data"> <!-- 🟡 加上 enctype -->
        <input type="hidden" name="action" value="${product.id != null ? 'update' : 'add'}" />
        <c:if test="${product.id != null}">
            <input type="hidden" name="id" value="${product.id}" />
        </c:if>

        <div class="form-grid">
            <label for="name">商品名稱：</label>
            <input type="text" id="name" name="name" value="${product.name}" required />

            <label for="price">價格：</label>
            <input type="number" id="price" name="price" step="0.01" value="${product.price}" required />

            <label for="stock">庫存：</label>
            <input type="number" id="stock" name="stock" value="${product.stock}" required />

            <label for="description">商品描述：</label>
            <textarea id="description" name="description" rows="4">${product.description}</textarea>

            <label for="categoryId">分類：</label>
            <select id="categoryId" name="categoryId" required>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}" ${product.categoryId == category.id ? 'selected' : ''}>
                        ${category.name}
                    </option>
                </c:forEach>
            </select>

            <!-- 🖼️ 圖片上傳 -->
            <label for="imageFile">商品圖片：</label>
            <input type="file" id="imageFile" name="imageFile" accept="image/*" />

            <!-- 🔍 若已有圖片，顯示預覽 -->
            <c:if test="${product.imageUrl != null}">
                <img src="${product.imageUrl}" alt="商品圖片" style="max-height: 100px;" />
            </c:if>

            <div class="btn form-actions">
                <input type="submit" value="${product.id != null ? '更新商品' : '新增商品'}" />
            </div>
        </div>
    </form>
</c:if>

<!-- ***** Products Area Ends ***** -->
</div>

<%@ include file="/templates/footer.jsp"%>