<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <title>註冊</title>
    <style>
        .error { color: red; font-size: 0.9em; }
        .success { color: green; font-size: 0.9em; }
    </style>
    <script>
        function validatePasswordMatch() {
            const pw1 = document.getElementById("password").value;
            const pw2 = document.getElementById("confirmPassword").value;
            const errorText = document.getElementById("pwError");
            const submitBtn = document.getElementById("submitBtn");

            if (pw1 !== pw2) {
                errorText.textContent = "⚠ 兩次輸入的密碼不一致";
                submitBtn.disabled = true;
            } else {
                errorText.textContent = "";
                submitBtn.disabled = false;
            }
        }

        function checkFieldAvailability(field, value) {
            const msgElem = document.getElementById(field + "Msg");
            const submitBtn = document.getElementById("submitBtn");

            if (value.trim() === "") {
                msgElem.textContent = "";
                msgElem.className = "";
                submitBtn.disabled = true;
                return;
            }

            const xhr = new XMLHttpRequest();
            xhr.open("GET", "CheckDuplicateServlet?field=" + field + "&value=" + encodeURIComponent(value), true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    const response = xhr.responseText.trim();

                    let label = field === "username" ? "帳號" :
                                field === "email" ? "Email 信箱" : "手機號碼";

                    if (response === "exists") {
                        msgElem.textContent = `⚠ ${label}${value}已被註冊，請更換`;
                        msgElem.className = "error";
                        submitBtn.disabled = true;
                    } else {
                        msgElem.textContent = "";
                        msgElem.className = "";
                        validatePasswordMatch(); // 檢查密碼一致性
                    }
                }
            };
            xhr.send();
        }


    </script>
</head>
<body>

<h2>註冊</h2>

<form action="RegisterController" method="post">
    <label>帳號：
        <input type="text" name="username" id="username" required 
               onblur="checkFieldAvailability('username', this.value)" 
               value="${username}" />
    </label> <span id="usernameMsg" class="error"></span><br/>

    <label>密碼：
        <input type="password" id="password" name="password" 
               oninput="validatePasswordMatch()" required />
    </label><br/>

    <label>密碼確認：
        <input type="password" id="confirmPassword" name="confirmPassword" 
               oninput="validatePasswordMatch()" required />
    </label>
    <span id="pwError" class="error"></span><br/>

    <label>手機：
        <input type="text" name="phone" id="phone" required 
               onblur="checkFieldAvailability('phone', this.value)" 
               value="${phone}" />
    </label> <span id="phoneMsg" class="error"></span><br/>

    <label>Email：
        <input type="email" name="email" id="email" required 
               onblur="checkFieldAvailability('email', this.value)" 
               value="${email}" />
    </label> <span id="emailMsg" class="error"></span><br/>

    <label>地址：
        <input type="text" name="address" value="${address}" required />
    </label><br/>

    <input type="submit" id="submitBtn" value="註冊" />
</form>

<!-- 錯誤訊息 -->
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>

<!-- 成功訊息 -->
<c:if test="${not empty message}">
    <p class="success">${message}</p>
</c:if>

<p>
    已有帳號？<a href="login.jsp">登入</a> |
    <a href="index.jsp">回首頁</a>
</p>

</body>
</html>
