<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
request.setAttribute("pageTitle", "分類管理");
request.getAttribute("message");
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
		<div></div>
	</div>

	<c:forEach var="category" items="${categories}">
		<form action="CategoryController" method="post">
			<input type="hidden" name="id" value="${category.id}" />
			<div class="category-grid">
				<div>${category.id}</div>
				<div>
					<input type="text" name="name" value="${category.name}" required />
				</div>
				<div>
					<textarea name="description">${category.description}</textarea>
				</div>
				<div>

					<button class="btn" type="submit" name="action" value="update"
						onclick="return confirm('確定要修改這個分類嗎？');">修改</button>
						<c:if test="${category.state == false}" >
							<button class="btn" type="submit" name="action" value="state-true"
								onclick="return confirm('確定要啟用這個分類嗎？');">啟用</button>
						</c:if>
						<c:if test="${category.state == true}" >
							<button class="btn" type="submit" name="action" value="state-false"
								onclick="return confirm('確定要停用這個分類嗎？');">停用</button>
						</c:if>
				</div>
			</div>
		</form>
	</c:forEach>

	<!-- ✅ 顯示錯誤或成功訊息 -->
	<c:if test="${not empty error}">
		<p class="error">${error}</p>
	</c:if>

	<c:if test="${not empty message}">
		<p class="message">${message}</p>
	</c:if>

</div>

</div>

<%@ include file="/templates/footer.jsp"%>