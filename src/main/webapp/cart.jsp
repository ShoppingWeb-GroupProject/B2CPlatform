<%@ page contentType="text/html; charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <title>購物車</title>
</head>
<body>

    <!-- ✅ 檢查登入 -->
    <c:if test="${empty sessionScope.username}">
        <c:redirect url="login.jsp" />
    </c:if>

    <h2>🛒 購物車內容</h2>
    <p>歡迎，${sessionScope.username}！</p>

    <!-- ✅ 購物車有內容時 -->
    <c:if test="${not empty cartItems}">
        <form action="CartController" method="post">
            <input type="hidden" name="action" value="clear" />
            <button type="submit">清空購物車</button>
        </form>

        <table border="1" cellpadding="5" cellspacing="0">
            <thead>
                <tr>
                    <th>商品名稱</th>
                    <th>單價</th>
                    <th>數量</th>
                    <th>小計</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${cartItems}">
                    <tr>
                        <td>${item.productName}</td>
                        <td>${item.price}</td>
                        <td>
                            <!-- ✅ 修改數量表單 -->
                            <form action="CartController" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="update" />
                                <input type="hidden" name="cartItemId" value="${item.id}" />
                                <input type="number" name="newQuantity" value="${item.quantity}" min="1" required />
                                <button type="submit">更新</button>
                            </form>
                        </td>
                        <td>${item.subtotal}</td>
                        <td>
                            <!-- ✅ 刪除項目表單 -->
                            <form action="CartController" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" name="cartItemId" value="${item.id}" />
                                <button type="submit">刪除</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <p>總金額：${totalAmount}</p>

        <!-- ✅ 前往結帳 -->
        <form action="checkout" method="post">
            <button type="submit">前往結帳</button>
        </form>
    </c:if>

    <!-- ✅ 購物車為空 -->
    <c:if test="${empty cartItems}">
        <p>您的購物車目前是空的。</p>
    </c:if>

    <p>
        <a href="ProductController?action=show">繼續購物</a> |
        <a href="index.jsp">回首頁</a>
    </p>

</body>
</html>
