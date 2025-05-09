package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    // 固定路徑：寫死為專案資料夾下


    private static final String dbPath = "C:\\Users\\codyc\\eclipse-workspace\\B2CPlatform\\shop.db";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + dbPath;
            System.out.println("📌 SQLite 使用的固定路徑：" + dbPath);

            // 載入 SQLite 驅動
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(url);

            // 啟用外鍵約束
            try (Statement pragmaStmt = conn.createStatement()) {
                pragmaStmt.execute("PRAGMA foreign_keys = ON;");
            }

            System.out.println("✅ SQLite 連線成功，外鍵已啟用！");
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
    	    """
    	    CREATE TABLE IF NOT EXISTS users (
    	    id INTEGER PRIMARY KEY AUTOINCREMENT,
    	    username VARCHAR(50) NOT NULL UNIQUE,
    	    password VARCHAR(255) NOT NULL,
    	    email VARCHAR(100) UNIQUE,
    	    phone VARCHAR(20),
    	    address VARCHAR(255),
    	    role VARCHAR(20) DEFAULT 'buyer',
    	    is_blacklisted BOOLEAN DEFAULT FALSE,
    	    discount DOUBLE DEFAULT 1.0
    	    );
    	    """,

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
            """
            CREATE TABLE IF NOT EXISTS discount (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INT NOT NULL,
            name VARCHAR(100) DEFAULT '未設定',
            description VARCHAR(255) DEFAULT '無描述',
            discount_value DOUBLE DEFAULT 1.0,
            is_active BOOLEAN DEFAULT TRUE,
            FOREIGN KEY (user_id) REFERENCES users(id)
            );
            """
        };

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            for (String sql : tableSQLs) {
                stmt.execute(sql);
            }
            System.out.println("✅ 所有資料表建立完成！");
        } catch (SQLException e) {
            System.out.println("❌ 建立資料表失敗: " + e.getMessage());
        }
    
    
    String[] insertCategories = {
    		"""
		    INSERT INTO categories (name, description) VALUES ('電子產品', '各式電子裝置、配件');
			INSERT INTO categories (name, description) VALUES ('服飾', '男女服飾、鞋包、配件');
			INSERT INTO categories (name, description) VALUES ('書籍', '各類書籍、雜誌、漫畫');
			INSERT INTO categories (name, description) VALUES ('美妝保養', '化妝品、護膚品、保養用品');
			INSERT INTO categories (name, description) VALUES ('家居用品', '家具、廚房用品、家用工具');
    		"""
		};

		try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement()) {
		    for (String sql : insertCategories) {
		        stmt.executeUpdate(sql);
		    }
		    System.out.println("✅ categories 資料建立完成！");
		} catch (SQLException e) {
		    System.out.println("❌ 建立 categories 資料失敗: " + e.getMessage());
		}
	}
}
