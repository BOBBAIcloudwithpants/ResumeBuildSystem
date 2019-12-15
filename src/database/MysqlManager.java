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
            mConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true", "root","baijiadong120201");


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
