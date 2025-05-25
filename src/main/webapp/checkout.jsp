<%@ page contentType="text/html; charset=UTF-8" session="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/templates/header.jsp"%>

<%
	request.setAttribute("pageTitle", "結帳");
%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <title>信用卡結帳</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5">
    <div class="card mx-auto shadow p-4" style="max-width: 500px;">
        <h2 class="mb-4 text-center">信用卡結帳</h2>

        <!-- 最終送到 PaymentController 做金流呼叫 -->
        <form action="PaymentController" method="post">
            <input type="hidden" name="orderId" value="${param.orderId}"/>
            <input type="hidden" name="address" value="${param.address}" />

            <div class="mb-3">
                <label for="address" class="form-label">收件地址</label>
                <input type="text" class="form-control" value="${param.address}" id="address" name="address" readonly/>
            </div>

            <div class="mb-3">
                <label for="cardNumber" class="form-label">卡號</label>
                <input type="text" class="form-control" id="cardNumber" name="cardNumber"
                       placeholder="**** **** **** ****" maxlength="19" required>
            </div>

            <div class="row">
                <div class="mb-3 col-md-6">
                    <label for="expiry" class="form-label">有效期限</label>
                    <input type="text" class="form-control" id="expiry" name="expiry"
                           placeholder="MM/YY" required>
                </div>
                <div class="mb-3 col-md-6">
                    <label for="cvv" class="form-label">CVV</label>
                    <input type="password" class="form-control" id="cvv" name="cvv"
                           maxlength="4" required>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">金額</label>
                <input type="text" class="form-control" name="amount" value="${param.amount}" readonly>
            </div>

            <button type="submit" class="btn btn-primary w-100">確認付款</button>
        </form>
    </div>
</div>
</body>
</html>

<%@ include file="/templates/footer.jsp"%>