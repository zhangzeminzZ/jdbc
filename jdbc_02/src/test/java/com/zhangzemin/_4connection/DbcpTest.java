package com.zhangzemin._4connection;

import com.zhangzemin._2dao.bean.Customer;
import com.zhangzemin._3dao.CustomerDAOImpl;
import com.zhangzemin.util.C3p0JDBCUtils;
import com.zhangzemin.util.DbcpJDBCUtils;
import com.zhangzemin.util.JDBCUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbcpTest {
    /**
     * 方式一：
     */
    @Test
    public void testGetConnection1() throws SQLException {
        //创建DBCP的数据库连接池
        BasicDataSource source = new BasicDataSource();

        //设置基本信息
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=utf8&rewriteBatchedStatements=true");
        source.setUsername("root");
        source.setPassword("123456");

        //还可以设置其他涉及数据库连接池管理的相关属性：
        source.setInitialSize(10);
        source.setMaxActive(10);
        //....

        Connection conn = source.getConnection();
        System.out.println(conn);
    }

    /**
     * 方式二：使用配置文件
     */
    private static DataSource source;
    static {
        try{
            Properties prop = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
            prop.load(is);
            source = BasicDataSourceFactory.createDataSource(prop);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void testGetConnection2() throws Exception{
        Connection conn = source.getConnection();
        System.out.println(conn);
    }

    @Test
    public void testGetCustomerById() {
        CustomerDAOImpl dao = new CustomerDAOImpl();
        Connection conn = null;
        try {
            conn = DbcpJDBCUtils.getConnection();
            Customer cust = dao.getCustomerById(conn, 19);
            System.out.println(cust);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null);
        }
    }
}
