<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
request.setAttribute("pageTitle", "首頁");
%>

<%@ include file="/templates/header.jsp"%>
<!-- 熱銷商品標題 -->
<h2 style="text-align: center; font-size: 2rem; font-weight: bold; margin-top: 40px; margin-bottom: 30px;">
    熱銷商品
</h2>

<!-- 商品區塊 -->
<div class="container">
    <div style="display: flex; overflow-x: auto; gap: 20px; padding: 10px;">
        <c:forEach var="category" items="${categories}">
            <div style="background: #fff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 15px; min-width: 300px; flex-shrink: 0;">
                <div style="font-weight: bold; font-size: 1.1rem; margin-bottom: 10px;">
                    ${category.name}
                </div>
                <div>
                    <c:set var="product" value="${productMap[category.id]}" />
                    <c:choose>
                        <c:when test="${not empty sessionScope.username}">
                            <c:if test="${not empty sessionScope.role && sessionScope.role=='seller'}">
                                <c:url var="toProduct" value="ProductController?action=modify&productId=${product.id}" />
                            </c:if>
                            <c:if test="${not empty sessionScope.role && sessionScope.role=='buyer'}">
                                <c:url var="toProduct" value="ProductController?action=detail&productId=${product.id}" />
                            </c:if>
                            <c:choose>
                                <c:when test="${not empty product.id && product.id != ''}">
                                    <a href="${toProduct}">
                                        <div style="margin-bottom: 8px;">${product.name}</div>
                                        <img src="${product.imageUrl}" alt="暫無圖片" style="width: 360px; border-radius: 5px;" />
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <div>暫無商品</div>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <div style="margin-bottom: 8px;"><c:out value="${product.name}" default="無商品" /></div>
                            <img src="${product.imageUrl}" alt="無商品" style="width: 360px; border-radius: 5px;" />
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>
	<c:if test="${empty sessionScope.username || sessionScope.role == 'buyer'}">
	    <div style="text-align: center; margin-top: 20px;">
	        <a href="https://line.me/R/ti/p/@375uqvrn" target="_blank"
	           style="
	               display: inline-block;
	               padding: 10px 20px;
	               background-color: #00b900;
	               color: white;
	               font-size: 2rem;
	               border-radius: 6px;
	               text-decoration: none;
	               transition: background-color 0.3s;
	           "
	           onmouseover="this.style.backgroundColor='#009a00'"
	           onmouseout="this.style.backgroundColor='#00b900'">
	           加入Kixado官方LINE追蹤您的訂單
	        </a>
	    </div>
	</c:if>
</div>

<%@ include file="/templates/footer.jsp"%>