package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // ✅ JawsDB MariaDB connection parameters
    private static final String URL = "jdbc:mysql://m1nnhxhj4achm4my:z0gciz6n0chhq8fs@c9cujduvu830eexs.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/ism1tkefynz1jgyp";
    private static final String USER = "m1nnhxhj4achm4my";
    private static final String PASSWORD = "z0gciz6n0chhq8fs";
    
    //本地資料
//    private static final String URL = "jdbc:mariadb://localhost:3306/b2cshop?useSSL=false&serverTimezone=Asia/Taipei&characterEncoding=utf8";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
//        	Class.forName("org.mariadb.jdbc.Driver");
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
