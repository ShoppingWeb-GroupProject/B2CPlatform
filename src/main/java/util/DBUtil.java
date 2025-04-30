//package util;
//import java.sql.*;
//
//public class DBUtil {
//    private static final String URL = "jdbc:mariadb://localhost:3306/testdb";
//    private static final String USER = "root";
//    private static final String PASSWORD = "cody7658";
//
//    public static Connection getConnection() throws Exception {
//        Class.forName("org.mariadb.jdbc.Driver");
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//}
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DBUtil {
	
	//private static final String DB_PATH = "C:/Users/codyc/eclipse-workspace/B2CPlatform/shop.db";

	public static Connection getConnection() {
        Connection conn = null;
        try {
        	String sep = File.separator;
        	//String theFilePath = System.getProperty("user.dir") + sep + "shop.db";
        	
        	File projectLocal = new File(DBUtil.class.getResource("").getPath().replaceAll("%20", " "));
        	projectLocal = projectLocal.getParentFile().getParentFile().getParentFile();
        	String dbPath = projectLocal.getAbsolutePath() + sep + "src" + sep + "database" + sep + "shop.db";
        	
        	String URL = "jdbc:sqlite:" + dbPath;
        	System.out.println("📌 SQLite 實際使用的檔案路徑：" + dbPath);
            // ✅ 強制載入 SQLite JDBC 驅動
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(URL);
            System.out.println("✅ SQLite 連線成功！");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ 找不到 SQLite JDBC 驅動: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ SQLite 連線失敗: " + e.getMessage());
        }
        return conn;
    }


    public static void initDatabase() {
        String[] tableSQLs = {
            // USERS
            "CREATE TABLE IF NOT EXISTS users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT NOT NULL UNIQUE," +
            "password TEXT NOT NULL," +
            "email TEXT NOT NULL UNIQUE," +
            "role TEXT CHECK(role IN ('buyer', 'seller', 'admin')) DEFAULT 'buyer'," +
            "created_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
            ");",

            // CATEGORIES
            "CREATE TABLE IF NOT EXISTS categories (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL UNIQUE," +
            "description TEXT" +
            ");",

            // PRODUCTS
            "CREATE TABLE IF NOT EXISTS products (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "seller_id INTEGER NOT NULL," +
            "name TEXT NOT NULL," +
            "description TEXT," +
            "category_id INTEGER," +
            "price REAL NOT NULL," +
            "stock INTEGER DEFAULT 0," +
            "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (seller_id) REFERENCES users(id)," +
            "FOREIGN KEY (category_id) REFERENCES categories(id)" +
            ");",

            // PRODUCTS_IMG
            "CREATE TABLE IF NOT EXISTS products_img (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "product_id INTEGER NOT NULL," +
            "image_url TEXT NOT NULL," +
            "is_main BOOLEAN DEFAULT FALSE," +
            "uploaded_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (product_id) REFERENCES products(id)" +
            ");",

            // CART_ITEMS
            "CREATE TABLE IF NOT EXISTS cart_items (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER NOT NULL," +
            "product_id INTEGER NOT NULL," +
            "quantity INTEGER NOT NULL DEFAULT 1," +
            "added_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (user_id) REFERENCES users(id)," +
            "FOREIGN KEY (product_id) REFERENCES products(id)" +
            ");",

            // ORDERS
            "CREATE TABLE IF NOT EXISTS orders (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER NOT NULL," +
            "total_amount REAL NOT NULL," +
            "address TEXT NOT NULL," +
            "status TEXT CHECK(status IN ('pending','paid','shipped','completed','cancelled')) DEFAULT 'pending'," +
            "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (user_id) REFERENCES users(id)" +
            ");",

            // ORDER_ITEMS
            "CREATE TABLE IF NOT EXISTS order_items (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "order_id INTEGER NOT NULL," +
            "product_id INTEGER NOT NULL," +
            "quantity INTEGER NOT NULL," +
            "price REAL NOT NULL," +
            "FOREIGN KEY (order_id) REFERENCES orders(id)," +
            "FOREIGN KEY (product_id) REFERENCES products(id)" +
            ");",

            // REVIEWS
            "CREATE TABLE IF NOT EXISTS reviews (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "product_id INTEGER NOT NULL," +
            "user_id INTEGER NOT NULL," +
            "rating INTEGER CHECK(rating BETWEEN 1 AND 5)," +
            "comment TEXT," +
            "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (product_id) REFERENCES products(id)," +
            "FOREIGN KEY (user_id) REFERENCES users(id)" +
            ");",

            // DISCOUNT
            "CREATE TABLE IF NOT EXISTS discount (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "description TEXT," +
            "discount_value REAL NOT NULL," +
            "is_active BOOLEAN DEFAULT TRUE" +
            ");"
        };

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            for (String sql : tableSQLs) {
                stmt.execute(sql);
            }
            System.out.println("所有資料表建立完成！");
        } catch (SQLException e) {
            System.out.println("建立資料表失敗: " + e.getMessage());
        }
    }
}
