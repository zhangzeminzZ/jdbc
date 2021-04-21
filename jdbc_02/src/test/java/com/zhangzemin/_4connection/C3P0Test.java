package com.zhangzemin._4connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zhangzemin._2dao.bean.Customer;
import com.zhangzemin._3dao.CustomerDAOImpl;
import com.zhangzemin.util.C3p0JDBCUtils;
import com.zhangzemin.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Test {
    /**
     * 方式一
     * @throws Exception
     */
    @Test
    public void testGetConnection1() throws Exception {
        //获取c3p0数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=utf8&rewriteBatchedStatements=true");
        cpds.setUser("root");
        cpds.setPassword("123456");
        //通过设置相关的参数，对数据库连接池进行管理。
        //设置初始时数据库连接池中的连接数
        cpds.setInitialPoolSize(10);

        Connection conn = cpds.getConnection();
        System.out.println(conn);

        //销毁c3p0数据库连接池
        //DataSources.destroy(cpds);
    }

    /**
     * 方式二：使用配置文件
     */
    ComboPooledDataSource cpds = new ComboPooledDataSource("helloC3p0");
    @Test
    public void testGetConnection2() throws SQLException {
        Connection conn = cpds.getConnection();
        System.out.println(conn);
    }

    @Test
    public void testGetCustomerById() {
        CustomerDAOImpl dao = new CustomerDAOImpl();
        Connection conn = null;
        try {
            conn = C3p0JDBCUtils.getConnection();
            Customer cust = dao.getCustomerById(conn, 19);
            System.out.println(cust);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null);
        }
    }
}
