<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>

<head>
    <title>${product.id != null ? '修改商品' : '新增商品'}</title>
</head>
<body>

<c:if test="${empty sessionScope.username or empty sessionScope.role}">
    <c:redirect url="login.jsp"/>
</c:if>

<p>使用者：${sessionScope.username}（
    <c:choose>
        <c:when test="${sessionScope.role == 'buyer'}">買家</c:when>
        <c:when test="${sessionScope.role == 'seller'}">賣家</c:when>
        <c:otherwise>⚠ 無效角色</c:otherwise>
    </c:choose>
）</p>

    <h2>${product.id != null ? '修改商品' : '新增商品'}</h2>

    <form action="ProductController" method="post">
        <input type="hidden" name="action" value="${product.id != null ? 'update' : 'add'}"/>

        <c:if test="${product.id != null}">
            <input type="hidden" name="id" value="${product.id}"/>
        </c:if>

        商品名稱：<input type="text" name="name" value="${product.name}" required/><br/>
        價格：<input type="number" name="price" value="${product.price}" required/><br/>
        數量：<input type="number" name="quantity" value="${product.quantity}" required/><br/>

        <input type="submit" value="${product.id != null ? '更新商品' : '新增商品'}"/>
    </form>

</body>
</html>
