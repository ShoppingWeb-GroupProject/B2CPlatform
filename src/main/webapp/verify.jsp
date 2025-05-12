<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setAttribute("pageTitle", "驗證Email");
%>
<%@ include file="/templates/header.jsp"%>
<div class="container mt-5">
	<div>請輸入驗證碼</div>

	<form method="post" action="VerifyController">
		<input type="text" name="code" placeholder="驗證碼" required>
		<button type="submit">送出</button>
	</form>

	<p style="color: red;">
		<%=request.getAttribute("msg") != null ? request.getAttribute("msg") : ""%>
	</p>
</div>
<%@ include file="/templates/footer.jsp"%>
