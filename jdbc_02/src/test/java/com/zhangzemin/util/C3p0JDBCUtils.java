package com.zhangzemin.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class C3p0JDBCUtils {
    private static ComboPooledDataSource cpds = new ComboPooledDataSource("helloC3p0");
    public static  Connection getConnection() throws SQLException {
        Connection conn = cpds.getConnection();
        return conn;
    }
}
