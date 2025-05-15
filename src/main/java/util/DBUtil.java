package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    // ✅ JawsDB MariaDB connection parameters
    private static final String URL = "jdbc:mysql://m1nnhxhj4achm4my:z0gciz6n0chhq8fs@c9cujduvu830eexs.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/ism1tkefynz1jgyp";
    private static final String USER = "m1nnhxhj4achm4my";
    private static final String PASSWORD = "z0gciz6n0chhq8fs";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ MariaDB 連線成功！");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ 找不到 MariaDB JDBC 驅動: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ MariaDB 連線失敗: " + e.getMessage());
        }
        return conn;
    }

    public static void initDatabase() {
        String[] tableSQLs = {
            // USERS
            """
            CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(50) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(100) UNIQUE,
                phone VARCHAR(20),
                address VARCHAR(255),
                role VARCHAR(20) DEFAULT 'buyer',
                is_blacklisted TINYINT(1) DEFAULT 0,
                discount DOUBLE DEFAULT 1.0,
                line_id VARCHAR(100);
            );
            """,

            // CATEGORIES
            """
            CREATE TABLE IF NOT EXISTS categories (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL UNIQUE,
                description TEXT
            );
            """,

            // PRODUCTS
            """
            CREATE TABLE IF NOT EXISTS products (
                id INT AUTO_INCREMENT PRIMARY KEY,
                seller_id INT NOT NULL,
                name VARCHAR(255) NOT NULL,
                description TEXT,
                category_id INT,
                price DOUBLE NOT NULL,
                stock INT DEFAULT 0,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (seller_id) REFERENCES users(id),
                FOREIGN KEY (category_id) REFERENCES categories(id)
            );
            """,

            // PRODUCTS_IMG
            """
            CREATE TABLE IF NOT EXISTS products_img (
                id INT AUTO_INCREMENT PRIMARY KEY,
                product_id INT NOT NULL,
                image_url TEXT NOT NULL,
                is_main TINYINT(1) DEFAULT 0,
                uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (product_id) REFERENCES products(id)
            );
            """,

            // CART_ITEMS
            """
            CREATE TABLE IF NOT EXISTS cart_items (
                id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT NOT NULL,
                product_id INT NOT NULL,
                quantity INT DEFAULT 1,
                added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id),
                FOREIGN KEY (product_id) REFERENCES products(id)
            );
            """,

            // ORDERS
            """
            CREATE TABLE IF NOT EXISTS orders (
                id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT NOT NULL,
                total_amount DOUBLE NOT NULL,
                address TEXT NOT NULL,
                status ENUM('pending','paid','shipped','completed','cancelled') DEFAULT 'pending',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id)
            );
            """,

            // ORDER_ITEMS
            """
            CREATE TABLE IF NOT EXISTS order_items (
                id INT AUTO_INCREMENT PRIMARY KEY,
                order_id INT NOT NULL,
                product_id INT NOT NULL,
                quantity INT NOT NULL,
                price DOUBLE NOT NULL,
                FOREIGN KEY (order_id) REFERENCES orders(id),
                FOREIGN KEY (product_id) REFERENCES products(id)
            );
            """,

            // REVIEWS
            """
            CREATE TABLE IF NOT EXISTS reviews (
                id INT AUTO_INCREMENT PRIMARY KEY,
                product_id INT NOT NULL,
                user_id INT NOT NULL,
                rating INT CHECK (rating BETWEEN 1 AND 5),
                comment TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (product_id) REFERENCES products(id),
                FOREIGN KEY (user_id) REFERENCES users(id)
            );
            """,

            // DISCOUNT
            """
            CREATE TABLE IF NOT EXISTS discount (
                id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT NOT NULL,
                name VARCHAR(100) DEFAULT '未設定',
                description VARCHAR(255) DEFAULT '無描述',
                discount_value DOUBLE DEFAULT 1.0,
                is_active TINYINT(1) DEFAULT 1,
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
            INSERT INTO categories (name, description) VALUES
            ('電子產品', '各式電子裝置、配件'),
            ('服飾', '男女服飾、鞋包、配件'),
            ('書籍', '各類書籍、雜誌、漫畫'),
            ('美妝保養', '化妝品、護膚品、保養用品'),
            ('家居用品', '家具、廚房用品、家用工具');
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
