<%@ page contentType="text/html; charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <title>訂單明細</title>
</head>
<body>

    <h2>訂單明細 - 訂單編號 ${orderId}</h2>

    <!-- ✅ 如果沒有找到訂單明細 -->
    <c:if test="${empty orderItems}">
        <p>查無此訂單明細！</p>
    </c:if>

    <!-- ✅ 如果有訂單明細 -->
    <c:if test="${not empty orderItems}">
        <table border="1">
            <thead>
                <tr>
                    <th>商品名稱</th>
                    <th>單價</th>
                    <th>數量</th>
                    <th>小計</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${orderItems}">
                    <tr>
                        <td>${item.productName}</td>
                        <td>${item.price}</td>
                        <td>${item.quantity}</td>
                        <td>${item.price * item.quantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <br>
    <!-- ✅ 返回訂單列表 -->
    <a href="OrderController">回訂單列表</a>

</body>
</html>
