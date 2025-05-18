package util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

public class LineUtil {
		private static final String accessToken = 
				"nZwO7jWEPnaKr19in4pFq2HdkbC916jCKc7lbR2YLU/DaIubtPhhcs3aDR/"+
				"wZhRrm41SII3sZE1UXdvJt/AmgxPaJGsSAiSTPbR5m2DMxCAM4KdOZcaJyK"+
				"z5drJ2CXPOsRVW/HBdHT4L3rpQJoSkcgdB04t89/1O/w1cDnyilFU=";

		public static void sendPushMessage(String lineId, String message) {
	        try {
	            URL url = new URL("https://api.line.me/v2/bot/message/push");
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setDoOutput(true);
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
	
	            JSONObject body = new JSONObject()
	                    .put("to", lineId)
	                    .put("messages", new JSONArray()
	                            .put(new JSONObject()
	                                    .put("type", "text")
	                                    .put("text", message)));
	
	            conn.getOutputStream().write(body.toString().getBytes(StandardCharsets.UTF_8));
	            System.out.println("LINE 推播回應碼：" + conn.getResponseCode());
	            conn.getInputStream().close();
	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}