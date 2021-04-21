package com.zhangzemin.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 
 * @Description 操作数据库的工具类
 * @author shkstart Email:shkstart@126.com
 * @version
 * @date 上午9:10:02
 *
 */
public class JDBCUtils {
	
	/**
	 * 
	 * @Description 获取数据库的连接
	 * @author shkstart
	 * @date 上午9:11:23
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		// 1.读取配置文件中的4个基本信息
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

		Properties pros = new Properties();
		pros.load(is);

		String driverClass = pros.getProperty("jdbc.type");
		String url = pros.getProperty("jdbc.url");
		String user = pros.getProperty("jdbc.username");
		String password = pros.getProperty("jdbc.password");

		// 2.加载驱动
		Class.forName(driverClass);

		// 3.获取连接
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	/**
	 * 
	 * @Description 关闭连接和Statement的操作
	 * @author shkstart
	 * @date 上午9:12:40
	 * @param conn
	 * @param ps
	 */
	public static void closeResource(Connection conn,Statement ps){
		try {
			if(ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @Description 关闭资源操作
	 * @author shkstart
	 * @date 上午10:21:15
	 * @param conn
	 * @param ps
	 * @param rs
	 */
	public static void closeResource(Connection conn,Statement ps,ResultSet rs){
		try {
			if(ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
