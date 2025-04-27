<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>註冊</title>
</head>
<body>

    <h2>註冊</h2>

    <form action="RegisterController" method="post">
        帳號：<input type="text" name="username" required><br>
        密碼：<input type="password" name="password" required><br>
        Email：<input type="email" name="email" required><br>
        <input type="submit" value="註冊">
    </form>

    <!-- 顯示錯誤訊息 -->
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <!-- 顯示成功訊息 -->
    <c:if test="${not empty message}">
        <p style="color:green;">${message}</p>
    </c:if>

</body>
</html>
