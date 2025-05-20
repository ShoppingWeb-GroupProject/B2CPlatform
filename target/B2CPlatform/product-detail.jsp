<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%
request.setAttribute("pageTitle", "商品詳細資訊");
%>
<%@ include file="/templates/header.jsp"%>

<div class="container mt-5">
	<div class="row">
		<div class="col-md-6">
			<img src="${product.imageUrl}" alt="${product.name}"
				class="img-fluid">
		</div>
		<div class="col-md-6">
			<h2>${product.name}</h2>
			<p>
				<strong>價格：</strong>${product.price}</p>
			<c:choose>
				<c:when test="${product.stock <= 0}">
					<p class="sold-out">庫存：已售罄</p>
				</c:when>
				<c:otherwise>
					<p>庫存：${product.stock}</p>
				</c:otherwise>
			</c:choose>
			<p>
				<strong>分類：</strong>
				<c:forEach var="category" items="${categories}">
					<c:if test="${category.id == product.categoryId}">${category.name}</c:if>
				</c:forEach>
			</p>
			<p>
				<strong>描述：</strong>${product.description}</p>

			<!-- 加入購物車（僅買家） -->
			<c:if test="${action == 'show' && sessionScope.role == 'buyer'}">
				<c:choose>
					<c:when test="${product.stock <= 0}">
						<p class="sold-out">已售罄</p>
					</c:when>
					<c:otherwise>
						<form action="CartItemController" method="post">
							<input type="hidden" name="productId" value="${product.id}" /> <label
								for="quantity">購買數量：</label> <input type="number" id="quantity"
								name="quantity" min="1" max="${product.stock}" value="1"
								required />
							<button type="submit" class="btn btn-primary">加入購物車</button>
						</form>
					</c:otherwise>
				</c:choose>
			</c:if>

			<!-- 新增評論（買家） -->
			<c:if test="${sessionScope.role == 'buyer'}">
				<form action="ReviewController" method="post" class="mt-4">
					<input type="hidden" name="productId" value="${product.id}" /> <label
						for="rating">評分：</label> <select name="rating" id="rating">
						<c:forEach begin="1" end="5" var="i">
							<option value="${i}">${i}星</option>
						</c:forEach>
					</select><br /> <label for="comment">評論：</label><br />
					<textarea name="comment" rows="4" cols="50"></textarea>
					<br />
					<button type="submit" class="btn btn-success mt-2">送出評論</button>
				</form>
			</c:if>
		</div>
	</div>

	<hr />
	<h3>用戶評論：</h3>
	<div class="review-section">
		<c:forEach var="review" items="${reviews}">
			<div style="position: relative; padding-right: 70px;">
				<p>
					<strong>${review.username}</strong>：${review.rating} ⭐
				</p>
				<p>${review.comment}</p>

				<!-- 🔴 刪除評論（賣家用） -->
				<c:if test="${sessionScope.role == 'seller'}">
					<form action="ReviewController" method="post" class="mb-2">
						<input type="hidden" name="action" value="delete" /> <input
							type="hidden" name="reviewId" value="${review.id}" /> <input
							type="hidden" name="productId" value="${product.id}" />
						<button type="submit" class="btn btn-danger btn-sm px-2 py-1"
							style="font-size: 0.75rem;"
							onclick="return confirm('確定要刪除這則評論嗎？')">刪除評論</button>
					</form>
				</c:if>

				<!-- ✅ 顯示回覆 -->
				<c:if test="${not empty review.replies}">
					<div
						style="margin-left: 2em; color: #555; border-left: 2px solid #ccc; padding-left: 10px; margin-top: 5px;">
						<strong>賣家回覆：</strong>
						<ul style="margin: 0; padding-left: 1em;">
							<c:forEach var="reply" items="${review.replies}">
								<li>${reply.reply} <c:if
										test="${sessionScope.role == 'seller' && sessionScope.user.id == reply.sellerId}">
										<form action="ReviewController" method="post"
											style="display: inline;">
											<input type="hidden" name="action" value="deleteReply" /> <input
												type="hidden" name="replyId" value="${reply.id}" /> <input
												type="hidden" name="productId" value="${product.id}" />
											<button type="submit"
												class="btn btn-outline-danger btn-sm px-2 py-1"
												style="font-size: 0.75rem;"
												onclick="return confirm('確定要刪除這則回覆嗎？')">刪除回覆</button>
										</form>
									</c:if>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>

				<!-- 🗑 買家刪自己評論（小按鈕） -->
				<c:if
					test="${sessionScope.role == 'buyer' && sessionScope.user.id == review.userId}">
					<form action="ReviewController" method="post"
						style="position: absolute; top: 0; right: 0;">
						<input type="hidden" name="action" value="delete" /> <input
							type="hidden" name="reviewId" value="${review.id}" /> <input
							type="hidden" name="productId" value="${product.id}" />
						<button type="submit"
							class="btn btn-outline-danger btn-sm px-2 py-1"
							style="font-size: 0.75rem;"
							onclick="return confirm('確定要刪除這則評論嗎？')">🗑</button>
					</form>
				</c:if>

				<!-- 💬 賣家新增回覆 -->
				<c:if test="${sessionScope.role == 'seller'}">
					<form action="ReviewController" method="post" class="mt-2">
						<input type="hidden" name="action" value="reply" /> <input
							type="hidden" name="reviewId" value="${review.id}" /> <input
							type="hidden" name="productId" value="${product.id}" />
						<textarea name="reply" rows="1" cols="40" placeholder="輸入回覆內容..."
							class="form-control form-control-sm mb-1"
							style="font-size: 0.8rem;"></textarea>
						<button type="submit" class="btn btn-secondary btn-sm px-2 py-1"
							style="font-size: 0.75rem;">回覆</button>
					</form>
				</c:if>
			</div>
			<hr />
		</c:forEach>
	</div>
</div>

<%@ include file="/templates/footer.jsp"%>
