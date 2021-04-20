package com.zhangzemin._1connection;

import com.mysql.jdbc.Driver;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * jdbc连接
 */
public class ConnectionTest {
    /**
     * 方式一
     * @throws SQLException
     */
    @Test
    public void testConnection1() throws SQLException {
        Driver driver = new Driver();
        //jdbc:mysql: 协议
        //localhost ip地址
        //3306 默认mysql的端口号
        //test test数据库
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=utf8&rewriteBatchedStatements=true";
        //讲用户名和密码封装在Properties文件中
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","123456");
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    /**
     * 方式二：对方式一的迭代
     * 在如下的程序中不出现第三方的api，使得程序具有更好的移植性
     */
    @Test
    public void testConnection2() throws Exception {
        //1.获取Driver实现类对象：使用反射
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //2.提供要连接的数据库
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=utf8&rewriteBatchedStatements=true";

        //3.提供要连接的用户名和密码
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","123456");

        //4.获取连接
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    /**
     * 方式三：使用DriverManager替换Driver
     */
    @Test
    public void testConnection3() throws Exception{
        //1.获取Driver实现类对象：使用反射
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        //2.提供另外三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=utf8&rewriteBatchedStatements=true";
        String user = "root";
        String password = "123456";
        //3.注册驱动
        DriverManager.registerDriver(driver);
        //4.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    /**
     * 方式四：可以只加载驱动，不用手动去加载，因为源码中已经帮我们加载了。
     */
    @Test
    public void testConnection4() throws Exception{
        //1.提供另外三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=utf8&rewriteBatchedStatements=true";
        String user = "root";
        String password = "123456";

        //2.加载Driver
        Class.forName("com.mysql.jdbc.Driver");
        //Driver driver = (Driver) clazz.newInstance();
        //注册驱动
        //DriverManager.registerDriver(driver);
        /**
         * 为什么可以省略上述操作呢?
         * 在mysql的Driver实现类中，声明了如下操作：
         * static {
         *         try {
         *             DriverManager.registerDriver(new Driver());
         *         } catch (SQLException var1) {
         *             throw new RuntimeException("Can't register driver!");
         *         }
         *     }
         */
        //4.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    /**
     * 方式五：将数据库连接需要的4个基本信息声明在配置文件中，通过读取配置文件的方式，获取连接
     * 此种方式的好处？
     * 1.实现了数据与代码的分离，实现了解耦。
     * 2.如果需要修改配置文件信息，可以避免程序重新打包
     */
    @Test
    public void testConnection5() throws Exception {
        //1.读取配置文件中的4个基本信息
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties props = new Properties();
        props.load(is);
        String driverClass = props.getProperty("jdbc.type");
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        Class.forName(driverClass);
        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println(conn);
    }
}
