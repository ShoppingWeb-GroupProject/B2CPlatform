<%@ page contentType="text/html; charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>訂單管理</title>
</head>
<body>

    <!-- 檢查是否有登入與角色 -->
    <c:if test="${empty sessionScope.username or empty sessionScope.role}">
        <c:redirect url="login.jsp"/>
    </c:if>

    <h2>訂單管理</h2>
    <p>使用者：${sessionScope.username}（
        <c:choose>
            <c:when test="${sessionScope.role == 'buyer'}">買家</c:when>
            <c:when test="${sessionScope.role == 'seller'}">賣家</c:when>
            <c:otherwise>⚠ 無效角色</c:otherwise>
        </c:choose>
    ）</p>

    <c:choose>
        <c:when test="${sessionScope.role == 'buyer'}">
            <h3>🔍 我的訂單紀錄</h3>
            <ul>
                <li>查詢訂單狀態</li>
                <li>取消未出貨訂單</li>
                <li>評價商品（訂單完成後）</li>
            </ul>
        </c:when>
        <c:when test="${sessionScope.role == 'seller'}">
            <h3>📦 商家訂單管理</h3>
            <ul>
                <li>查看新接收的訂單</li>
                <li>修改訂單狀態（待出貨 → 已出貨）</li>
                <li>查詢歷史訂單</li>
            </ul>
        </c:when>
        <c:otherwise>
            <p style="color:red;">⚠ 無效角色</p>
        </c:otherwise>
    </c:choose>

    <a href="index.jsp">回首頁</a>

</body>
</html>
