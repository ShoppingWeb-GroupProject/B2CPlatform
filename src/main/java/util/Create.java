package util;

public class Create{
    public static void main(String[] args) {
        System.out.println("🔧 開始建立資料庫...");

        // 執行初始化（使用 DBUtil 裡寫死的路徑）
        DBUtil.initDatabase();

        System.out.println("✅ 資料庫與資料表建立完成！");
    }
}
