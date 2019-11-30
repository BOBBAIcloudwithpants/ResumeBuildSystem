package database;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.cj.jdbc.*;

public class MysqlManager {
    private static Connection mConnect;
    static{
        try{
            System.out.println("init--");
            Class.forName("com.mysql.cj.jdbc.Driver");
            mConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai", "root","baijiadong120201");
//            jdbc.jdbcUrl = jdbc:mysql://117.*.*.*:9966/databasename?useUnicode=true&characterEncoding=utf8&autoReconnect=true
//            jdbc.user = root
//            jdbc.password = root
//            jdbc.miniPoolSize = 1
//            jdbc.maxPoolSize = 100
//            jdbc.initialPoolSize = 1
//            jdbc.maxIdleTime = 3600
//            jdbc.acquireIncrement = 1
//
//            jdbc.acquireRetryAttempts = 30
//            jdbc.acquireRetryDelay = 1000
//            jdbc.testConnectionOnCheckout=false
//            jdbc.testConnectionOnCheckin = true
//            jdbc.automaticTestTable = c3p0TestTable
//            jdbc.idleConnectionTestPeriod = 90
//            jdbc.checkoutTimeout=30000

        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return mConnect;
    }

    public static void close() {
        try{
            mConnect.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


}
