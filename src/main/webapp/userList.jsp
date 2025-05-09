<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>會員管理</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            padding: 8px;
            border: 1px solid #ccc;
            text-align: center;
        }
        .btn {
            padding: 4px 8px;
            margin: 2px;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
    <h2>會員管理列表</h2>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>帳號</th>
                <th>Email</th>
                <th>角色</th>
                <th>黑名單</th>
                <th>電話</th>
                <th>地址</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                    <td>
                        <c:choose>
                            <c:when test="${user.blacklisted}">是</c:when>
                            <c:otherwise>否</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${user.phone}</td>
                    <td>${user.address}</td>
                    <td>
                        <!-- 黑名單切換 -->
                        <c:choose>
                            <c:when test="${user.blacklisted}">
                                <a class="btn" href="UserController?action=blacklist&id=${user.id}&status=false">移除黑名單</a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn" href="UserController?action=blacklist&id=${user.id}&status=true">加入黑名單</a>
                            </c:otherwise>
                        </c:choose>

                        <!-- 角色更新 -->
                        <form action="UserController" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="updateRole">
                            <input type="hidden" name="id" value="${user.id}">
                            <select name="role">
                                <option value="buyer" <c:if test="${user.role == 'buyer'}">selected</c:if>>買家</option>
                                <option value="seller" <c:if test="${user.role == 'seller'}">selected</c:if>>賣家</option>
                                <option value="admin" <c:if test="${user.role == 'admin'}">selected</c:if>>管理員</option>
                            </select>
                            <button type="submit" class="btn">更新角色</button>
                        </form>

                        <!-- 折扣管理連結 -->
                        <a class="btn" href="DiscountController?username=${user.username}">管理折扣</a>

                        <!-- 刪除用戶 -->
                        <a class="btn" href="UserController?action=delete&id=${user.id}" onclick="return confirm('確定要刪除此用戶？');">刪除</a>
                    </td>
                </tr>
            </c:forEach>
            <a href="index.jsp">回首頁</a>
        </tbody>
    </table>
</body>
</html>
