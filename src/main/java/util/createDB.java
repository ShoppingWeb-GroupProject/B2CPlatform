package util;

import util.DBUtil;

@SuppressWarnings("unused")
public class createDB {
    public static void main(String[] args) {
        DBUtil.initDatabase(); // 建立所有資料表
    }
}