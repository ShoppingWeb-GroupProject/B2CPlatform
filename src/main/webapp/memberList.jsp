<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.User" %>
<html>
<head>
    <title>會員管理</title>
</head>
<body>
<h2>會員列表</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>帳號</th>
        <th>Email</th>
        <th>電話</th>
        <th>地址</th>
        <th>角色</th>
        <th>黑名單</th>
        <th>折扣</th>
        <th>操作</th>
    </tr>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        for (User u : users) {
    %>
    <tr>
        <td><%= u.getId() %></td>
        <td><%= u.getUsername() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getPhone() != null ? u.getPhone() : "" %></td>
        <td><%= u.getAddress() != null ? u.getAddress() : "" %></td>
        <td><%= u.getRole() %></td>
        <td><%= u.isBlacklisted() ? "是" : "否" %></td>
        <td><%= u.getDiscount() %></td>
        <td>
            <!-- 黑名單切換 -->
            <a href="MemberController?action=blacklist&id=<%= u.getId() %>&status=<%= !u.isBlacklisted() %>">
                <%= u.isBlacklisted() ? "解除黑名單" : "加入黑名單" %>
            </a>
        </td>
    </tr>
    <% } %>
</table>

<p><a href="index.jsp">回首頁</a></p>
</body>
</html>
