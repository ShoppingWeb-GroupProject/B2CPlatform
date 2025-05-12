<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setAttribute("pageTitle", "註冊");
%>
<%@ include file="/templates/header.jsp"%>

<div class="container mt-5">
<h2 style="margin-bottom:10px;">註冊</h2>
<!-- 顯示後端錯誤或成功訊息 -->
<c:if test="${not empty error}">
	<p style="color: red;">${error}</p>
</c:if>
<c:if test="${not empty message}">
	<p style="color: green;">${message}</p>
</c:if>
<form action="RegisterController" method="post" class="form-grid">
	<label for="username">帳號：</label>
	<div>
		<input type="text" name="username" id="username" value="${username}" required
			onblur="checkField('username', this.value, 'usernameCheck')">
		<span id="usernameCheck" style="margin-left: 10px;"></span>
	</div>

	<label for="password">密碼：</label>
	<input type="password" name="password" id="password" required oninput="validatePasswordMatch()">

	<label for="confirmPassword">確認密碼：</label>
	<div>
		<input type="password" name="confirmPassword" id="confirmPassword" required oninput="validatePasswordMatch()">
		<span id="passwordCheck" style="margin-left: 10px;"></span>
	</div>

	<label for="email">Email：</label>
	<div>
		<input type="email" name="email" id="email" value="${email}" required
			onblur="checkField('email', this.value, 'emailCheck')">
		<span id="emailCheck" style="margin-left: 10px;"></span>
	</div>

	<label for="phone">手機：</label>
	<div>
		<input type="tel" name="phone" id="phone" value="${phone}" required
			onblur="checkField('phone', this.value, 'phoneCheck')">
		<span id="phoneCheck" style="margin-left: 10px;"></span>
	</div>

	<label for="address">地址：</label>
	<input type="text" name="address" id="address" value="${address}">
	
	<button class="btn form-actions" type="submit">註冊</button>
</form>



</div>
<script>
    function checkField(field, value, resultSpanId) {
        if (!value) {
            document.getElementById(resultSpanId).textContent = '';
            return;
        }

        fetch('CheckDuplicateServlet?field=' + field + '&value=' + encodeURIComponent(value))
            .then(response => response.json()) // ⬅️ 解析 JSON 回應
            .then(data => {
                const span = document.getElementById(resultSpanId);
                span.textContent = data.message;
                span.style.color = data.status === 'exists' ? 'red' : 'blue';
            })
            .catch(error => {
                console.error('驗證失敗:', error);
            });
    }
    
    function validatePasswordMatch() {
        const password = document.querySelector('input[name="password"]').value;
        const confirmPassword = document.querySelector('input[name="confirmPassword"]').value;
        const messageSpan = document.getElementById("passwordCheck");

        if (!confirmPassword) {
            messageSpan.textContent = '';
            return;
        }

        if (password !== confirmPassword) {
            messageSpan.textContent = "密碼不一致";
            messageSpan.style.color = "red";
        } else {
            messageSpan.textContent = "";
        }
    }

    </script>

<%@ include file="/templates/footer.jsp"%>
