<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
  <meta charset="UTF-8">
  <title>設定使用者折扣</title>
  <style>
    body {
      font-family: "微軟正黑體", sans-serif;
      background-color: #f0f2f5;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }
    .form-container {
      background-color: #fff;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 2px 12px rgba(0,0,0,0.1);
      width: 360px;
      text-align: center; /* 中心對齊內容 */
    }
    h2 {
      margin-bottom: 20px;
      color: #333;
    }
    label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
      color: #555;
      text-align: left; /* 標籤左對齊 */
    }
    input[type="text"],
    input[type="number"] {
      width: 100%;
      padding: 8px;
      margin-bottom: 15px;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-sizing: border-box;
    }
    input[type="submit"] {
      width: 100%;
      padding: 10px;
      background-color: #28a745;
      border: none;
      border-radius: 4px;
      color: #fff;
      font-size: 16px;
      cursor: pointer;
    }
    input[type="submit"]:hover {
      background-color: #218838;
    }
    .btn-home {
      display: inline-block;
      padding: 10px 20px;
      margin: 20px auto 0;
      background-color: #007bff;
      color: #fff;
      text-decoration: none;
      border-radius: 4px;
      cursor: pointer;
    }
    .btn-home:hover {
      background-color: #0056b3;
    }
    .error-message {
      color: #d93025;
      margin-top: 10px;
      text-align: center;
    }
  </style>
</head>
<body>
  <div class="form-container">
    <h2>設定使用者折扣</h2>
    <form action="DiscountController" method="post">
      <label for="username">使用者帳號</label>
      <input type="text" id="username" name="username" value="${username}" required>

      <label for="discount">折扣 (例如 0.8 代表八折)</label>
      <input type="number" id="discount" name="discount" step="0.01" required>

      <input type="submit" value="提交">
    </form>

    <c:if test="${not empty error}">
      <div class="error-message">${error}</div>
    </c:if>

    <a class="btn-home" href="index.jsp">回首頁</a>
  </div>
</body>
</html>
