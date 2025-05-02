<%@ page contentType="text/html; charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>購物車</title>
    <style>
        .cart-container {
            display: grid;
            grid-template-columns: repeat(5, 1fr); /* 多一欄放刪除按鈕 */
            font-weight: bold;
            padding: 10px 0;
        }

        .cart-item {
            display: grid;
            grid-template-columns: repeat(5, 1fr);
            gap: 10px;
            padding: 8px 0;
            border-bottom: 1px solid #ccc;
            align-items: center;
        }

        .cart-wrapper {
            max-width: 900px;
            margin: auto;
        }

        .total {
            font-size: 18px;
            margin-top: 20px;
        }

        form.remove-form {
            margin: 0;
        }
    </style>
</head>
<body>

    <c:if test="${empty sessionScope.username}">
        <c:redirect url="login.jsp"/>
    </c:if>

    <div class="cart-wrapper">
        <h2>🛒 購物車頁面</h2>
        <p>歡迎，${sessionScope.username}，這是您的購物車。</p>

        <c:choose>
            <c:when test="${empty cartItems}">
                <p>目前購物車是空的。</p>
            </c:when>
            <c:otherwise>
                <!-- 表頭 -->
                <div class="cart-container">
                    <div>商品名稱</div>
                    <div>價格</div>
                    <div>數量</div>
                    <div>小計</div>
                    <div>操作</div>
                </div>

                <c:set var="total" value="0" />
                <c:forEach var="item" items="${cartItems}">
                    <c:set var="subtotal" value="${item.price * item.quantity}" />
                    <c:set var="total" value="${total + subtotal}" />
                    <div class="cart-item">
                        <div>${item.productName}</div>
                        <div>$${item.price}</div>
                        <div>${item.quantity}</div>
                        <div>$${subtotal}</div>
                        <div>
                            <form action="CartItemController" method="post" class="remove-form">
                                <input type="hidden" name="action" value="remove"/>
                                <input type="hidden" name="productId" value="${item.productId}" />
                                <button type="submit">移除</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>

                <p class="total"><strong>總金額：</strong> $${total}</p>

                <!-- 建立訂單 -->
                <form action="OrderController" method="post">
                    <label for="address">收件地址：</label>
                    <input type="text" id="address" name="address" required />
                    <button type="submit">建立訂單</button>
                </form>
            </c:otherwise>
        </c:choose>

        <br/>
        <a href="index.jsp">回首頁</a>
    </div>

</body>
</html>
