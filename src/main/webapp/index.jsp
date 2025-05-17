<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
request.setAttribute("pageTitle", "首頁");
%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="dao.CategoryDAO"%>
<%@ page import="model.Category"%>
<%@ page import="model.Product"%>
<%@ page import="service.ProductService"%>

<%@ include file="/templates/header.jsp"%>

<%
CategoryDAO categoryDAO = new CategoryDAO();
List<Category> categories = categoryDAO.findAllCategories();
request.setAttribute("categories", categories);

Map<Integer, Product> productMap = new HashMap<>();
for (Category category : categories) {
	List<Product> products = ProductService.getProductsByCategory(category.getId());
	if (products != null && !products.isEmpty()) {
		productMap.put(category.getId(), products.get(0)); // 只取第一個
	}
}
request.setAttribute("productMap", productMap);
%>

<div class="container mt-5">
	<div style="display: flex; overflow-x: auto;">
		<c:forEach var="category" items="${categories}">
			<div style="margin: 5px;">
				<div>
					<strong>分類：</strong> ${category.name}
				</div>
				<div>
					<c:set var="product" value="${productMap[category.id]}" />
					<c:choose>
						<c:when test="${not empty sessionScope.username}">
							<c:if
								test="${not empty sessionScope.role && sessionScope.role=='seller'}">
								<c:url var="toProduct"
									value="ProductController?action=modify&productId=${product.id}"></c:url>
							</c:if>
							<c:if
								test="${not empty sessionScope.role && sessionScope.role=='buyer'}">
								<c:url var="toProduct"
									value="ProductController?action=detail&productId=${product.id}"></c:url>
							</c:if>
							<a href="${toProduct}">
								<c:out value="${product.name}" default="無商品" /><br /> <img
								src="${product.imageUrl}" alt="無商品" width="300" />
							</a>

						</c:when>
						<c:otherwise>
							<c:out value="${product.name}" default="無商品" />
							<br />
							<img src="${product.imageUrl}" alt="無商品" width="300" />
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:forEach>
	</div>
	<c:if
		test="${empty sessionScope.username || sessionScope.role=='buyer'}">
		<a
			href="https://line.me/R/ti/p/@375uqvrnhttps://line.me/R/ti/p/@375uqvrn">加入官方帳號，追蹤您的包裹</a>
	</c:if>
</div>

<%@ include file="/templates/footer.jsp"%>