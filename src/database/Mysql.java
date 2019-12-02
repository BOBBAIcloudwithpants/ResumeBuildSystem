package database;

import model.Group;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Mysql {

    public Connection mConnect;

    public Mysql (Connection connection) {
        super();
        this.mConnect = connection;
    }

    public boolean createTable (String sql) {
        boolean result = false;

        try {
            Statement statement = mConnect.createStatement();
            statement.execute(sql);
            result = true;
            statement.close();
        } catch (SQLException e) {
            System.err.println("创建表异常:" + e.getMessage());
        }
        return result;
    }


    public void addUser (List<User> users) {
        try {
            Statement statement = mConnect.createStatement();
            for (User user : users) {
                try {
                    String string = user.getUserString();
                    statement.executeUpdate("INSERT INTO test.user(username,password,isAdmin) VALUES " + string);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        } catch (SQLException e) {
            if (e.getMessage().contains("PRIMAEY")) {
                System.out.println("主键重复");
            }
        }
    }

    public User getUserById (String username) {
        String sql = "SELECT * FROM `test`.`user` WHERE username='" + username + "';";
        try {
            Statement statement = mConnect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    public int updateUserPwd (String username, String oldPwd, String newPwd) {
        int result = -1;

        try {
            String sql = "UPDATE `test`.`new_table1` SET `password` = '" + newPwd + "' WHERE `username` = '" + username + "'";

            User user = getUserById(username);

            if (user != null) {
                if (user.getPassword().equals(oldPwd)) {
                    Statement statement = mConnect.createStatement();
                    statement.executeUpdate(sql);
                    statement.close();
                    result = 0;
                } else {
                    result = 1;
                    System.err.println("密码错误，无法修改");
                }
            } else {
                result = 2;
                System.err.println("无此用户");
            }

        } catch (SQLException e) {

        }
        return result;
    }

    public boolean appendGradeByUsernameAndId(String username, int id, int newGrade){
        User user = getUserById(username);
        if(user == null){
            return false;
        }

        if(id > User.MAX_GRADE_NUMBER){
            return false; // 不合法的id
        }

        try{
            Statement sta = mConnect.createStatement();
            String temp = "grade"+id;
            String sql = "update test.user set "+temp+"="+newGrade+" where username="+username+";";
            sta.executeUpdate(sql);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public List<User> getAllUsers () {
        String sql = "SELECT * FROM test.user";

        try {
            Statement statement = mConnect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            List<User> users = new ArrayList<User>();

            if (resultSet.next())
                while (resultSet.next()) {
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
                    users.add(user);
                }
            if (users.isEmpty()) {
                return null;
            }
            return users;
        } catch (SQLException e) {

        }
        return null;

    }

    public Group getGroupById (int id) {
        String sql = "SELECT * FROM `test`.`group` WHERE id=" + id;
        Group groups = null;

        try {
            Statement sta = mConnect.createStatement();
            ResultSet resultSet = sta.executeQuery(sql);
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                String base = "username";
                for (int i = 1; i <= 10; i++) {

                    String username = resultSet.getString("username" + i);
                    if (username != null) {
                        users.add(getUserById(username));
                    }

                }
                groups = new Group(id, users);
            }

            if (groups == null) {
                return null;
            }
            return groups;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    public boolean appendUserIntoGroup (String username, int groupID) {
        User user = getUserById(username);
        if (user == null) {
            return false; //g该用户不存在
        }

        Group group = getGroupById(groupID);
        if (group == null) {
            return false; // 该组不存在
        }

        if (group.getUsers().size() >= Group.MAX_CAPACITY) {
            return false; // 超过容量
        }

        if (user.getGroupID() > 0) {
            Group group1 = getGroupById(user.getGroupID());
            try {
                int i = group1.getUsers().indexOf(user.getUsername()) + 1;
                Statement sta = mConnect.createStatement();
                while (i <= Group.MAX_CAPACITY) {
                    String temp = "username" + i;
                    String sql = "select " + temp + " from test.group where id=" + groupID + ";";
                    ResultSet resultSet = sta.executeQuery(sql);
                    String target = "";
                    while (resultSet.next()) { // 更新username，并保证group中的每一条的username都相邻存放
                        target = resultSet.getString(temp);
                    }

                    int j = i - 1;
                    String temp2 = "username" + j;
                    String sql2 = "update test.group set " + temp2 + "=" + target + " where id=" + groupID + ";";
                    sta.executeUpdate(sql2);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            group1.getUsers().remove(getUserById(username));
        }

        user.setGroupID(groupID);
        try {
            String sql = "update test.user set groupID=" + groupID + " where username=" + username + ";";
            Statement sta = mConnect.createStatement();
            sta.executeUpdate(sql);

            int i = group.getUsers().size() + 1;
            String temp = username + i;

            String sql2 = "update test.group set " + temp + "=" + username + "where id=" + groupID + ";";
            sta.executeUpdate(sql2);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

    public static void main (String[] args) {
        Mysql mysql = new Mysql(MysqlManager.getConnection());
        List<User> users = new ArrayList<>();
        users.add(new User("bob", "12345", 1));

        mysql.addUser(users);
    }
}

