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
		<div class="container grid-row">
			<c:forEach var="product" items="${showProducts}">
				<div class="item">
					<div class="thumb">
						<!-- <div class="hover-content">
						<ul>
							<li><a href="single-product.html"><i class="fa fa-eye"></i></a></li>
							<li><a href="single-product.html"><i class="fa fa-star"></i></a></li>
							<li><a href="single-product.html"><i
									class="fa fa-shopping-cart"></i></a></li>
						</ul>
					</div> -->
						<a href="ProductController?action=detail&productId=${product.id}">
							<img src="assets/images/men-01.jpg" alt="${product.name}">
						</a>
					</div>
					<div class="down-content">
						<h4>${product.name}</h4>
						<span>${product.price}</span>
						<!-- <ul class="stars">
						<li><i class="fa fa-star"></i></li>
						<li><i class="fa fa-star"></i></li>
						<li><i class="fa fa-star"></i></li>
						<li><i class="fa fa-star"></i></li>
						<li><i class="fa fa-star"></i></li>
					</ul> -->
					</div>
				</div>

			</c:forEach>
		</div>
	</section>
</c:if>

<c:if test="${action=='showForSeller'}">
	<h2>商品列表</h2>
	<a href="ProductController?action=modify"><button>新增商品</button></a>
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
			<!--<c:if test="${action=='show'}">
                            <div>
                                <form action="CartItemController" method="post">
                                    <input type="hidden" name="productId" value="${product.id}" /> <label
                                        for="quantity">購買數量：</label> <input type="number" id="quantity" name="quantity"
                                        min="1" value="${item.quantity}" required />
                                    <button type="submit">加入購物車</button>
                                    </a>
                                </form>
                            </div>
                        </c:if>-->
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
			<input type="number" id="stock" name="stock" value="${product.stock}"
				required /> <label for="description">商品描述：</label>
			<textarea id="description" name="description" rows="4">${product.description}</textarea>

			<label for="categoryId">分類：</label> <select id="categoryId"
				name="categoryId" required>
				<option value="1" ${product.categoryId==1 ? 'selected' : '' }>電子產品</option>
				<option value="2" ${product.categoryId==2 ? 'selected' : '' }>服飾</option>
				<option value="3" ${product.categoryId==3 ? 'selected' : '' }>書籍</option>
				<option value="4" ${product.categoryId==4 ? 'selected' : '' }>美妝保養</option>
				<option value="5" ${product.categoryId==5 ? 'selected' : '' }>家居用品</option>
			</select>

			<div class="form-actions">
				<input type="submit" value="${product.id != null ? '更新商品' : '新增商品'}" />
			</div>
		</div>
	</form>

</c:if>
<!-- ***** Products Area Ends ***** -->

<%@ include file="/templates/footer.jsp"%>