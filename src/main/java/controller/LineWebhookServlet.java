package controller;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;

/**
 * LineWebhookServlet
 * 功能：接收來自 LINE 的 webhook 請求，解析訊息是否為「綁定 username」，
 * 若是，將該 username 綁定傳送者的 LINE userId
 */
@SuppressWarnings("serial")
public class LineWebhookServlet extends HttpServlet {

    // 使用者服務層：負責查詢使用者與更新 line_id
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 🔽 讀取 LINE 傳來的 webhook JSON 請求內容
        BufferedReader reader = request.getReader();
        StringBuilder payload = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            payload.append(line);
        }

        try {
            // 🔽 從 JSON 中解析出 event（LINE 僅一次通常只有一個 event）
            JSONObject event = new JSONObject(payload.toString())
                    .getJSONArray("events").getJSONObject(0);

            // 取得使用者的 LINE userId
            String lineUserId = event.getJSONObject("source").getString("userId");

            // 取得使用者傳送的文字訊息內容
            String messageText = event.getJSONObject("message").getString("text").trim();

            // 檢查訊息是否為「綁定 username」開頭
            if (messageText.toLowerCase().startsWith("綁定 ")) {
                // 🔽 取出 username
                String username = messageText.substring(3).trim(); // "綁定 abc123" → 取出 abc123

                // 查詢使用者 ID
                int userId = userService.getUserIdByUsername(username);
                if (userId == -1) {
                    System.out.println("綁定失敗：找不到使用者 " + username);
                    response.setStatus(404);
                    return;
                }

                // 🔽 更新 line_id
                boolean success = userService.updateLineId(userId, lineUserId);
                if (success) {
                    System.out.println("Line ID 綁定成功：" + username + " → " + lineUserId);
                    response.setStatus(200);
                } else {
                    System.out.println("Line ID 綁定失敗");
                    response.setStatus(500);
                }

            } else {
                // 非綁定指令，可忽略或回覆訊息
                System.out.println("收到其他訊息：" + messageText);
                response.setStatus(200); // 仍需回應 200
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500); // JSON 格式錯誤或解析失敗
        }
    }
}
