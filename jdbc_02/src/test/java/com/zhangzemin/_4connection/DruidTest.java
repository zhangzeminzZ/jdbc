package com.zhangzemin._4connection;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.zhangzemin._2dao.bean.Customer;
import com.zhangzemin._3dao.CustomerDAOImpl;
import com.zhangzemin.util.DruidJDBCUtils;
import com.zhangzemin.util.JDBCUtils;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidTest {
    private static DataSource source;
    static {
        try{
            Properties prop = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            prop.load(is);
            source = DruidDataSourceFactory.createDataSource(prop);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void getConnection() throws Exception {
        Connection conn = source.getConnection();
        System.out.println(conn);
    }

    @Test
    public void testGetCustomerById() {
        CustomerDAOImpl dao = new CustomerDAOImpl();
        Connection conn = null;
        try {
            conn = DruidJDBCUtils.getConnection();
            Customer cust = dao.getCustomerById(conn, 19);
            System.out.println(cust);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null);
        }
    }
}
