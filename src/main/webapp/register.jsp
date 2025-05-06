<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>註冊</title>
    <script>
    function checkField(field, value, resultSpanId) {
        if (!value) {
            document.getElementById(resultSpanId).textContent = '';
            return;
        }
        fetch('CheckDuplicateServlet?field=' + field + '&value=' + encodeURIComponent(value))
            .then(response => response.text())
            .then(result => {
                if (result === 'exists') {
                    document.getElementById(resultSpanId).textContent = field + ' 已存在';
                } else {
                    document.getElementById(resultSpanId).textContent = field + ' 可用';
                }
            });
    }
    </script>
</head>
<body>
    <h2>註冊帳號</h2>

    <!-- 顯示錯誤或成功訊息 -->
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
    <c:if test="${not empty message}">
        <p style="color: green;">${message}</p>
    </c:if>

    <form action="RegisterController" method="post">
        <label>帳號：</label>
        <input type="text" name="username" id="username" value="${username}" required 
               onblur="checkField('username', this.value, 'usernameCheck')">
        <span id="usernameCheck" style="margin-left: 10px; color: blue;"></span>
        <br><br>

        <label>密碼：</label>
        <input type="password" name="password" required>
        <br><br>

        <label>確認密碼：</label>
        <input type="password" name="confirmPassword" required>
        <br><br>

        <label>Email：</label>
        <input type="email" name="email" id="email" value="${email}" required 
               onblur="checkField('email', this.value, 'emailCheck')">
        <span id="emailCheck" style="margin-left: 10px; color: blue;"></span>
        <br><br>

        <label>手機：</label>
        <input type="tel" name="phone" id="phone" value="${phone}" required 
               onblur="checkField('phone', this.value, 'phoneCheck')">
        <span id="phoneCheck" style="margin-left: 10px; color: blue;"></span>
        <br><br>

        <label>地址：</label>
        <input type="text" name="address" value="${address}">
        <br><br>

        <button type="submit">註冊</button>
    </form>
</body>
</html>
