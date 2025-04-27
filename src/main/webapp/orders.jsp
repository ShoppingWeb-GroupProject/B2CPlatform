<%@ page contentType="text/html; charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>訂單管理</title>
</head>
<body>

<!-- 檢查登入 -->
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

<c:if test="${empty orders}">
    <p>尚無訂單。</p>
</c:if>

<c:if test="${not empty orders}">
    <table border="1">
        <thead>
            <tr>
                <th>訂單編號</th>
                <c:choose>
                    <c:when test="${sessionScope.role == 'buyer'}">
                        <th>總金額</th>
                    </c:when>
                    <c:when test="${sessionScope.role == 'seller'}">
                        <th>買家名稱</th>
                        <th>總金額</th>
                    </c:when>
                </c:choose>
                <th>狀態</th>
                <th>下單時間</th>
                <th>操作</th>
                <th>明細</th>
            </tr>
        </thead>
		<tbody>
		    <c:forEach var="order" items="${orders}">
		        <tr>
		            <td>${order.id}</td>
		
		            <c:choose>
		                <c:when test="${sessionScope.role == 'buyer'}">
		                    <td>${order.totalAmount}</td>
		                </c:when>
		                <c:when test="${sessionScope.role == 'seller'}">
		                    <td>${order.buyerName}</td>
		                    <td>${order.totalAmount}</td>
		                </c:when>
		            </c:choose>
		
		            <td>${order.status}</td>
		            <td>${order.createdAt}</td>
		
		            <!-- 🔵 這個是動作按鈕 -->
		            <td>
		                <c:choose>
		                    <c:when test="${sessionScope.role == 'buyer' && order.status == 'pending'}">
		                        <form action="OrderUpdateController" method="post" style="display:inline;">
		                            <input type="hidden" name="orderId" value="${order.id}">
		                            <input type="hidden" name="action" value="cancel">
		                            <input type="submit" value="取消訂單">
		                        </form>
		                    </c:when>
		
		                    <c:when test="${sessionScope.role == 'seller' && order.status == 'pending'}">
		                        <form action="OrderUpdateController" method="post" style="display:inline;">
		                            <input type="hidden" name="orderId" value="${order.id}">
		                            <input type="hidden" name="action" value="ship">
		                            <input type="submit" value="出貨">
		                        </form>
		                    </c:when>
		
		                    <c:when test="${sessionScope.role == 'buyer' && order.status == 'shipped'}">
		                        <form action="OrderUpdateController" method="post" style="display:inline;">
		                            <input type="hidden" name="orderId" value="${order.id}">
		                            <input type="hidden" name="action" value="complete">
		                            <input type="submit" value="確認完成">
		                        </form>
		                    </c:when>
		                </c:choose>
		            </td>
		
		            <!-- 🟠 這個是查看明細按鈕 -->
		            <td>
		                <form action="OrderDetailController" method="get" style="display:inline;">
		                    <input type="hidden" name="orderId" value="${order.id}">
		                    <input type="submit" value="查看明細">
		                </form>
		            </td>
		        </tr>
		    </c:forEach>
		</tbody>
    </table>
</c:if>

<br>
<a href="index.jsp">回首頁</a>

</body>
</html>
