package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import util.DBUtil;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 🔥 這邊指定你放在 WebContent/WEB-INF/db/shop.db 的路徑
        String path = sce.getServletContext().getRealPath("/WEB-INF/db/shop.db");
        DBUtil.setDbPath(path);
        System.out.println("✅ SQLite DB 路徑設定完成：" + path);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 清除動作（可選）
    }
}
