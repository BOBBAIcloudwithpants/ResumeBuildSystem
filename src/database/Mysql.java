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

    public int findIndex (String username, List<User> users) { // 已测试
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }


    public void addUser (List<User> users) { //已测试
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

    public User getUserByUsername (String username) { //已测试
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

    public int updateUserPwd (String username, String oldPwd, String newPwd) { //未测试
        int result = -1;

        try {
            String sql = "UPDATE `test`.`new_table1` SET `password` = '" + newPwd + "' WHERE `username` = '" + username + "'";

            User user = getUserByUsername(username);

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

    public boolean appendGradeByUsernameAndId (String username, int id, int newGrade) { //已测试
        User user = getUserByUsername(username);
        if (user == null) {
            return false;
        }

        if (id > User.MAX_GRADE_NUMBER) {
            return false; // 不合法的id
        }

        try {
            Statement sta = mConnect.createStatement();
            String temp = "grade" + id;
            String sql = "update test.user set " + temp + "=" + newGrade + " where username=\"" + username + "\";";
            user.getGrades().set(id - 1, newGrade);
            sta.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<User> getAllUsers () { //已测试
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

    public boolean deleteUserInGroup (String username, int id) { //已测试
        User user = getUserByUsername(username);
        if (user == null) {
            return false;
        }

        Group group = getGroupById(id);
        if (group == null) {
            return false;
        }

        if (user.getGroupID() != id) {
            return false;
        }

        int i = findIndex(username, group.getUsers()) + 1;
        System.out.println(i);


        try {
            Statement sta = mConnect.createStatement();
            while (i < group.getUsers().size()) {
                int j = i + 1;
                String temp = "username" + j;

                String sql = "select " + temp + " from `test`.`group` where id=" + id + ";";
                ResultSet resultSet = sta.executeQuery(sql);
                String target = "";
                while (resultSet.next()) { // 更新username，并保证group中的每一条的username都相邻存放
                    target = resultSet.getString(temp);
                }

                String temp2 = "username" + i;
                String sql2 = "update test.group set " + temp2 + "=\"" + target + "\" where id=" + id + ";";
                sta.executeUpdate(sql2);
                i++;

            }
            String temp = "username" + i;
            String sql3 = "update test.group set " + temp + "=null where id=" + id + ";";
            sta.executeUpdate(sql3);
            group.getUsers().remove(findIndex(username, group.getUsers()));
            user.setGroupID(-1);
            String sql4 = "update test.user set groupID=-1 where username=\"" + user.getUsername() + "\";";
            sta.executeUpdate(sql4);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Group getGroupById (int id) { //已测试
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
                        users.add(getUserByUsername(username));
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

    public void clearAllUserOfGroup (int id) { //已测试
        Group group = getGroupById(id);

        group.getUsers().clear();


        try {
            Statement sta = mConnect.createStatement();
            for (int i = 1; i <= 10; i++) {
                String temp = "username" + i;
                String sql = "update test.group set " + temp + "=null where id=" + id + ";";
                sta.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean appendUserIntoGroup (String username, int groupID) { //已测试
        User user = getUserByUsername(username);
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
            deleteUserInGroup(username, user.getGroupID());
        }

        user.setGroupID(groupID);
        try {

            String sql = "update test.user set groupID=" + groupID + " where username=\"" + username + "\";";
            Statement sta = mConnect.createStatement();
            sta.executeUpdate(sql);

            int i = group.getUsers().size() + 1;
            String temp = "username" + i;


            String sql2 = "update test.group set " + temp + "=\"" + username + "\" where id=" + groupID + ";";
            group.getUsers().add(user);

            sta.executeUpdate(sql2);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

    public static void main (String[] args) {
        Mysql mysql = new Mysql(MysqlManager.getConnection());

        mysql.appendUserIntoGroup("bob", 2);


    }
}

