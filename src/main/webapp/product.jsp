<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <title>${product.id != null ? '修改商品' : '新增商品'}</title>
</head>
<body>

    <!-- ✅ 檢查登入與角色 -->
    <c:if test="${empty sessionScope.username or empty sessionScope.role}">
        <c:redirect url="login.jsp" />
    </c:if>

    <p>
        使用者：${sessionScope.username}（
        <c:choose>
            <c:when test="${sessionScope.role == 'buyer'}">買家</c:when>
            <c:when test="${sessionScope.role == 'seller'}">賣家</c:when>
            <c:otherwise>⚠ 無效角色</c:otherwise>
        </c:choose>
        ）
    </p>

    <h2>${product.id != null ? '修改商品' : '新增商品'}</h2>

    <!-- ✅ 商品表單 -->
    <form action="ProductController" method="post">
        <!-- 判斷是新增或修改 -->
        <input type="hidden" name="action" value="${product.id != null ? 'update' : 'add'}" />

        <!-- 修改時帶入商品 ID -->
        <c:if test="${product.id != null}">
            <input type="hidden" name="id" value="${product.id}" />
        </c:if>

        <label>
            商品名稱：
            <input type="text" name="name" value="${product.name}" required />
        </label><br/>

        <label>
            價格：
            <input type="number" name="price" value="${product.price}" required />
        </label><br/>

        <label>
            數量：
            <input type="number" name="stock" value="${product.stock}" required />
        </label><br/>

        <label>
            分類 ID：
            <input type="number" name="categoryId" value="${product.categoryId}" required />
        </label><br/>

        <input type="submit" value="${product.id != null ? '更新商品' : '新增商品'}" />
    </form>

    <!-- ✅ 回商品列表 -->
    <p>
        <a href="ProductController?action=showForSeller">回我的商品列表</a>
    </p>

</body>
</html>
