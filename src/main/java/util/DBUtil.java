package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    //本地資料
    private static final String URL = "jdbc:mysql://localhost:3306/b2cshop?useSSL=false&serverTimezone=Asia/Taipei&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "cody7658";

    public static Connection getConnection() {
        Connection conn = null;
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ 連線成功！");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ 找不到驅動: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ MariaDB 連線失敗: " + e.getMessage());
        }
        return conn;
    }
    
    // 測試用主方法
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ 成功連線到資料庫！");
            }
        } catch (SQLException e) {
            System.err.println("❌ 資料庫連線失敗：" + e.getMessage());
        }
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                System.out.println("✅ 使用的是 MariaDB JDBC 驅動");
            } catch (ClassNotFoundException e1) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    System.out.println("✅ 使用的是 MySQL JDBC 驅動");
                } catch (ClassNotFoundException e2) {
                    System.out.println("❌ 未找到 MariaDB 或 MySQL JDBC 驅動");
                }
            }
        }
    }
