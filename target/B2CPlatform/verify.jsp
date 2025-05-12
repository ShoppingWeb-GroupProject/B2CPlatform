<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>驗證 Email</title>
</head>
<body>
    <h2>請輸入驗證碼</h2>

    <form method="post" action="VerifyController">
        <input type="text" name="code" placeholder="驗證碼" required>
        <button type="submit">送出</button>
    </form>

    <p style="color:red;">
        <%= request.getAttribute("msg") != null ? request.getAttribute("msg") : "" %>
    </p>
</body>
</html>
