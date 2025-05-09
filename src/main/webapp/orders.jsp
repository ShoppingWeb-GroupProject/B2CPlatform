<%@ page contentType="text/html; charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    request.setAttribute("pageTitle", "訂單管理");
%>
<%@ include file="/templates/header.jsp"%>

    <!-- ✅ 檢查是否登入與有角色，否則導向登入頁 -->
    <c:if test="${empty sessionScope.username or empty sessionScope.role}">
        <c:redirect url="login.jsp" />
    </c:if>

    <h2>訂單管理</h2>

    <p>
        使用者：${sessionScope.username}（
        <c:choose>
            <c:when test="${sessionScope.role == 'buyer'}">買家</c:when>
            <c:when test="${sessionScope.role == 'seller'}">賣家</c:when>
            <c:otherwise>⚠ 無效角色</c:otherwise>
        </c:choose>
        ）
    </p>

    <!-- ✅ 沒有訂單時的顯示 -->
    <c:if test="${empty orders}">
        <p>尚無訂單。</p>
    </c:if>

    <!-- ✅ 有訂單時的表格顯示 -->
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

                        <!-- ✅ 根據角色顯示不同欄位 -->
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

                        <!-- ✅ 操作區：根據角色與狀態決定按鈕 -->
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

                        <!-- ✅ 查看訂單明細按鈕 -->
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
<%@ include file="/templates/footer.jsp"%>
