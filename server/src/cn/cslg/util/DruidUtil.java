package cn.cslg.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author XWf
 * @create 2020-03-11-20:49
 */
public class DruidUtil {
    static DruidDataSource dSource = null;
    static Properties properties = new Properties();
    //当前正在使用的连接
    private static ThreadLocal<Connection> local = new ThreadLocal<>();

    // 静态代码块初始化加载驱动
    static {
        // 通过类加载器来获得流
        InputStream is = DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            // 通过流读取配置文件中的内容到集合中
            properties.load(is);
            // 通过Druid工厂加载文件注册驱动,初始化池子
            dSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static DruidDataSource getDataSource(){
        return dSource;
    }
    public static Connection getConn(){
        try {
            //当你的连接是空的 或者是关闭状态的时候
            //我们需要从连接池中获取一个新的连接
            Connection thisConn = local.get();
            if(thisConn == null || thisConn.isClosed()){
                thisConn = dSource.getConnection();
                local.set(thisConn);
            }

            return thisConn;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        System.out.println("获取连接失败");
        return null;
    }
}
