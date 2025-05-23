<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap"
	rel="stylesheet">

<title>${pageTitle}</title>


<!-- Additional CSS Files -->
<link rel="stylesheet" type="text/css" href="assets/css/discount.css">

<link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css" href="assets/css/font-awesome.css">

<link rel="stylesheet" href="assets/css/templatemo-hexashop.css">

<link rel="stylesheet" href="assets/css/owl-carousel.css">

<link rel="stylesheet" href="assets/css/lightbox.css">

<link rel="stylesheet" href="<c:url value='/assets/css/main-style.css' />">

<link rel="stylesheet" href="<c:url value='/assets/css/cart-item.css' />">

<link rel="stylesheet" href="<c:url value='/assets/css/product-list.css' />">

<link rel="stylesheet" href="<c:url value='/assets/css/category.css' />">


<!--

TemplateMo 571 Hexashop

https://templatemo.com/tm-571-hexashop

-->
</head>

<body>
	<header class="header-area header-sticky">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<nav class="main-nav">
						<!-- ***** Logo Start ***** -->
						<a href="home" class="logo"> <img
							src="assets/images/KIXADO-gray.png" width="150">
						</a>
						<!-- ***** Logo End ***** -->
						<!-- ***** Menu Start ***** -->

						<c:url var="productShow" value="ProductController">
							<c:param name="action" value="show" />
						</c:url>
						<c:url var="productShowSeller" value="ProductController">
							<c:param name="action" value="showForSeller" />
						</c:url>

						<ul class="nav">
							<li class="scroll-to-section"><a href="home"
								class="active">首頁</a></li>
							<c:if test="${empty sessionScope.username}">
								<li class="scroll-to-section"><a href="register.jsp">註冊</a></li>
								<li class="scroll-to-section"><a href="login.jsp">登入</a></li>
							</c:if>
							<c:if test="${not empty sessionScope.username}">
								<li class="scroll-to-section"><a href="userProfile.jsp">個人資料</a></li>
								<li class="scroll-to-section"><a href="OrderController">訂單查詢</a></li>

								<c:url var="productShow" value="ProductController">
									<c:param name="action" value="show" />
								</c:url>
								<c:url var="productShowSeller" value="ProductController">
									<c:param name="action" value="showForSeller" />
								</c:url>
								<c:if
									test="${not empty sessionScope.role && sessionScope.role=='buyer'}">
									<li><a href="${productShow}">逛逛商品</a></li>
									<li class="scroll-to-section"><a href="CartItemController">購物車</a></li>
								</c:if>
								<c:if
									test="${not empty sessionScope.role && sessionScope.role=='seller'}">
									<!-- <li class="scroll-to-section"><a href="discountForm.jsp">折扣設定</a></li> -->
									<li><a href="${productShowSeller}">我的商品</a></li>
									<li class="submenu"><a href="javascript:;">管理功能</a>
									<ul>
										<li class="scroll-to-section"><a
											href="UserController?action=list">會員管理</a></li>
										<li class="scroll-to-section"><a
											href="CategoryController">商品分類設定</a></li>
									</ul></li>
								</c:if>
								<li class="scroll-to-section"><a href="LogoutController">登出</a></li>
							</c:if>
						</ul>
						<a class='menu-trigger'> <span>Menu</span>
						</a>
						<!-- ***** Menu End ***** -->
					</nav>
				</div>
			</div>
		</div>
	</header>