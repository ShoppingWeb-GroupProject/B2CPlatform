<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
request.setAttribute("pageTitle", "分類管理");
%>
<%@ include file="/templates/header.jsp"%>

<div class="container mt-5">

	<!-- ✅ 新增分類表單 -->
	<div class="header">新增分類</div>
	<form action="CategoryController" method="post">
		<input type="hidden" name="action" value="add" /> <input type="text"
			name="name" placeholder="分類名稱" required /> <input type="text"
			name="description" placeholder="描述" />
		<button type="submit">新增</button>
	</form>

	<hr />

	<!-- ✅ 顯示分類列表 -->
	<div class="category-grid header">
		<div>ID</div>
		<div>名稱</div>
		<div>描述</div>
		<div>操作</div>
	</div>

	<c:forEach var="category" items="${categories}">
		<form action="CategoryController" method="post">
		<input type="hidden" name="action" value="update" /> <input
						type="hidden" name="id" value="${category.id}" />
			<div class="category-grid">
				<div>${category.id}</div>
				<div>
					<input type="text" name="name" value="${category.name}" required />
				</div>
				<div>
					<textarea name="description">${category.description}</textarea>
				</div>
				<div>
					
					<button type="submit">修改</button>
					<button type="submit" name="action" value="delete"
						onclick="return confirm('確定要刪除這個分類嗎？');">刪除</button>
				</div>
			</div>
		</form>
	</c:forEach>
</div>


<!-- ✅ 顯示錯誤或成功訊息 -->
<c:if test="${not empty error}">
	<p class="error">${error}</p>
</c:if>

<c:if test="${not empty message}">
	<p class="success">${message}</p>
</c:if>

</div>

<%@ include file="/templates/footer.jsp"%>