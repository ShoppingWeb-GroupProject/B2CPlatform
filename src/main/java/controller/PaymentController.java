package controller;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/PaymentController")
public class PaymentController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String address = request.getParameter("address");
	    String amount = request.getParameter("amount");

	    // 加入防呆（可選）
	    if (address == null || amount == null) {
	        response.sendRedirect("cart.jsp");
	        return;
	    }

	    // 把參數傳給 checkout.jsp 顯示
	    request.setAttribute("address", address);
	    request.setAttribute("amount", amount);

	    request.getRequestDispatcher("checkout.jsp").forward(request, response);
	}

	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String address = request.getParameter("address");
        String amountStr = request.getParameter("amount");
        String cardNumber = request.getParameter("cardNumber");
        String cvv = request.getParameter("cvv");
        String expiry = request.getParameter("expiry");

        boolean paymentSuccess = validateCard(cardNumber, cvv, expiry);

        if (paymentSuccess) {
            // ✅ 付款成功 → forward 給 OrderController 建立訂單
            request.setAttribute("address", address);
            request.setAttribute("amount", amountStr);
            request.setAttribute("fromPayment", true); // 做個旗標讓 OrderController 知道這是付款後來的
            request.getRequestDispatcher("OrderController").forward(request, response);
        } else {
            // ❌ 付款失敗
            String reason = URLEncoder.encode("付款失敗：卡號無效或餘額不足", "UTF-8");
            response.sendRedirect("CartItemController?error=" + reason);
            return;
        }
    }

    private boolean validateCard(String cardNumber, String cvv, String expiry) {
        return cardNumber != null && cvv != null && expiry != null;
    }
}
