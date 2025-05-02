<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <title>結帳確認</title>
</head>
<body>

    <h2>結帳確認</h2>

    <!-- ✅ 顯示購物車內容 -->
    <table border="1">
        <tr>
            <th>商品名稱</th>
            <th>單價</th>
            <th>數量</th>
            <th>小計</th>
        </tr>
        <c:forEach var="item" items="${cartItems}">
            <tr>
                <td>${item.productName}</td>
                <td>${item.price}</td>
                <td>${item.quantity}</td>
                <td>${item.subtotal}</td>
            </tr>
        </c:forEach>
    </table>

    <!-- ✅ 顯示總金額 -->
    <p>總金額：${totalAmount}</p>

    <!-- ✅ 提交結帳表單 -->
    <form action="checkout" method="post">
        <button type="submit">確認結帳</button>
    </form>

    <!-- ✅ 回購物車或首頁 -->
    <p>
        <a href="cart.jsp">回購物車</a> |
        <a href="index.jsp">回首頁</a>
    </p>

</body>
</html>
