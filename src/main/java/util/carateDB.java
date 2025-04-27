package util;

import util.DBUtil;

@SuppressWarnings("unused")
public class carateDB {
    public static void main(String[] args) {
        DBUtil.initDatabase(); // 建立所有資料表
    }
}