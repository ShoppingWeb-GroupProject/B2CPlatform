<%@ page contentType="text/html; charset=UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/templates/header.jsp"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <title>分類管理</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin-bottom: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        form.inline { display: inline; margin: 0 5px; }
        .error { color: red; }
        .success { color: green; }
    </style>
</head>
<body>

<h2>分類管理</h2>

<!-- ✅ 新增分類表單 -->
<h3>新增分類</h3>
<form action="CategoryController" method="post">
    <input type="hidden" name="action" value="add" />
    <input type="text" name="name" placeholder="分類名稱" required />
    <input type="text" name="description" placeholder="描述" />
    <button type="submit">新增</button>
</form>

<hr/>

<!-- ✅ 顯示分類列表 -->
<h3>分類清單</h3>
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>名稱</th>
            <th>描述</th>
            <th>操作</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td>${category.id}</td>
                <td>${category.name}</td>
                <td>${category.description}</td>
                <td>
                    <!-- ✅ 修改分類 -->
                    <form class="inline" action="CategoryController" method="post">
                        <input type="hidden" name="action" value="update" />
                        <input type="hidden" name="id" value="${category.id}" />
                        <input type="text" name="name" value="${category.name}" required />
                        <input type="text" name="description" value="${category.description}" />
                        <button type="submit">修改</button>
                    </form>

                    <!-- ✅ 刪除分類 -->
                    <form class="inline" action="CategoryController" method="post"
                          onsubmit="return confirm('確定要刪除這個分類嗎？');">
                        <input type="hidden" name="action" value="delete" />
                        <input type="hidden" name="id" value="${category.id}" />
                        <button type="submit">刪除</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<!-- ✅ 返回首頁連結 -->
<p>
    <a href="index.jsp">← 回首頁</a>
</p>

<!-- ✅ 顯示錯誤或成功訊息 -->
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>

<c:if test="${not empty message}">
    <p class="success">${message}</p>
</c:if>

</body>
</html>
