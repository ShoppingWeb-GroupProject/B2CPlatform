<%@ page contentType="text/html; charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>購物車</title>
</head>
<body>

    <!-- 如果沒有登入，轉到 login.jsp -->
    <c:if test="${empty sessionScope.username}">
        <c:redirect url="login.jsp"/>
    </c:if>

    <!-- 顯示購物車內容 -->
    <h2>🛒 購物車頁面</h2>
    <p>歡迎，${sessionScope.username}，這是您的購物車。</p>

    <!-- 商品清單區（之後可以接資料庫撈商品） -->
    <p>（這裡將顯示加入的商品清單）</p>

    <a href="index.jsp">回首頁</a>

</body>
</html>
