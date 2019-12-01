package database;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Mysql {

    public Connection mConnect;
    public Mysql(Connection connection){
        super();
        this.mConnect = connection;
    }

    public boolean createTable(String sql) {
        boolean result = false;

        try{
            Statement statement = mConnect.createStatement();
            statement.execute(sql);
            result = true;
            statement.close();
        }catch (SQLException e){
            System.err.println("创建表异常:"+e.getMessage());
        }
        return result;
    }

    public void addUser(List<User> users){
        try{
            Statement statement = mConnect.createStatement();
        } catch (SQLException e){
            if(e.getMessage().contains("PRIMAEY")){
                System.out.println("主键重复");
            }
        }
    }

    public User getUserInfos(String username) {
        String sql = "SELECT * FROM `test`.`new_table` WHERE username='"+username+"';";
        try {
            Statement statement = mConnect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.first()){
                List<Integer> grades = new ArrayList<Integer>();
                grades.add(resultSet.getInt("grade1"));
                grades.add(resultSet.getInt("grade2"));
                grades.add(resultSet.getInt("grade3"));
                grades.add(resultSet.getInt("grade4"));
                grades.add(resultSet.getInt("grade5"));
                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("isAdmin"),
                        resultSet.getString("description"),
                        resultSet.getInt("groupID"),
                        grades
                );
                        return user;
            }

            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    public int updateUserPwd(String username, String oldPwd, String newPwd){
        int result = -1;

        try{
            String sql = "UPDATE `test`.`new_table1` SET `password` = '"+newPwd + "' WHERE `username` = '"+username+"'";

            User user = getUserInfos(username);

            if(user != null){
                if(user.getPassword().equals(oldPwd)) {
                    Statement statement = mConnect.createStatement();
                    statement.executeUpdate(sql);
                    statement.close();
                    result = 0;
                } else{
                    result = 1;
                    System.err.println("密码错误，无法修改");
                }
            } else{
                result = 2;
                System.err.println("无此用户");
            }

            }catch(SQLException e){

        }
        return result;
    }

    public static void main(String[] args){
        Mysql mysql = new Mysql(MysqlManager.getConnection());

    }
}

