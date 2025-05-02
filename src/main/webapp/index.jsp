<%@ page contentType="text/html; charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <title>首頁 - ProjectShop</title>
</head>
<body>

    <h1>ProjectShop 首頁</h1>

    <!-- 判斷是否已登入 -->
    <c:choose>
        <c:when test="${not empty sessionScope.username}">
            <p>歡迎，${sessionScope.username}！</p>
            <ul>
                <li><a href="CartController">購物車</a></li>
                <li><a href="OrderController">訂單查詢</a></li>

                <!-- 建立商品列表與賣家商品連結 -->
                <c:url var="productShow" value="ProductController">
                    <c:param name="action" value="show" />
                </c:url>
                <c:url var="productShowSeller" value="ProductController">
                    <c:param name="action" value="showForSeller" />
                </c:url>

                <li><a href="${productShow}">逛逛商品</a></li>
                <li><a href="${productShowSeller}">我的商品</a></li>

                <li><a href="LogoutController">登出</a></li>
            </ul>
        </c:when>
        <c:otherwise>
            <p>請先登入或註冊帳號：</p>
            <ul>
                <li><a href="register.jsp">註冊</a></li>
                <li><a href="login.jsp">登入</a></li>
            </ul>
        </c:otherwise>
    </c:choose>

    <hr>

    <!-- 熱銷商品展示區 -->
    <h2>熱銷商品（未來可接資料庫動態列出）</h2>
    <p>目前是靜態測試區。</p>
    <!-- 可改成：從後端傳入熱銷商品清單，使用 c:forEach 動態列出 -->
    <!-- 範例：
    <c:forEach var="product" items="${hotProducts}">
        <div>
            <p>${product.name}</p>
            <p>價格：${product.price}</p>
        </div>
    </c:forEach>
    -->

</body>
</html>
