<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <title>註冊</title>
</head>
<body>

    <h2>註冊</h2>

    <!-- ✅ 註冊表單 -->
    <form action="RegisterController" method="post">
        <label>
            帳號：
            <input type="text" name="username" required />
        </label><br/>

        <label>
            密碼：
            <input type="password" name="password" required />
        </label><br/>

        <label>
            Email：
            <input type="email" name="email" required />
        </label><br/>

        <input type="submit" value="註冊" />
    </form>

    <!-- ✅ 顯示錯誤訊息（紅色） -->
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <!-- ✅ 顯示成功訊息（綠色） -->
    <c:if test="${not empty message}">
        <p style="color:green;">${message}</p>
    </c:if>

    <!-- ✅ 導覽連結 -->
    <p>
        已有帳號？<a href="login.jsp">登入</a> |
        <a href="index.jsp">回首頁</a>
    </p>

</body>
</html>
