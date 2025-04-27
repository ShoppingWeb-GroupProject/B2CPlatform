<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>登入</title>
</head>
<body>
    <h2>登入</h2>

    <form action="LoginController" method="post">
        帳號：<input type="text" name="username" required><br>
        密碼：<input type="password" name="password" required><br>
        <input type="submit" value="登入">
    </form>

    <!-- 用 JSTL 顯示錯誤訊息 -->
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>
</body>
</html>
