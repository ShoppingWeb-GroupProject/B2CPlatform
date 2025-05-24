<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setAttribute("pageTitle", "設定會員等級");
%>
<%@ include file="/templates/header.jsp"%>

<div class="discount-container">
    <h2>會員折扣設定</h2>

    <form action="UserController" method="post">
        <input type="hidden" name="action" value="updateLevel" />
        <input type="hidden" name="username" value="${user.username}" />

        <p><strong>會員：</strong> ${user.username}</p>
        <p><strong>Email：</strong> ${user.email}</p>

        <label for="discount">會員等級：</label>
        <select name="discount" id="discount">
            <option value="1.00" ${user.discount == 1.00 ? "selected" : ""}>普通會員 (0%)</option>
            <option value="0.90" ${user.discount == 0.90 ? "selected" : ""}>白銀會員 (10%)</option>
            <option value="0.80" ${user.discount == 0.80 ? "selected" : ""}>黃金會員 (20%)</option>
        </select>

        <input type="submit" value="更新等級" />
    </form>

    <a href="UserController?action=list" class="back-link">← 回會員管理列表</a>
</div>

<%@ include file="/templates/footer.jsp"%>
