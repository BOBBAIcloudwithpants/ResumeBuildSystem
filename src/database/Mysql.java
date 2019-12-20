package database;

import Controller.UserController;
import com.sun.glass.ui.Window;
import javafx.util.Pair;
import model.Award;
import model.File;
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

    public int AwardIndex (String title, List<Award> awards) {
        int i = 0;
        for (Award award : awards) {
            if (award.getTitle().equals(title)) {
                System.out.println(i);
                return i;
            } else {
                i++;
            }
        }


        return -1;
    }

    public boolean createGroup (int id) {
        try {
            Statement sta = mConnect.createStatement();
            String sql = "insert into test.group(id) values (" + id + ")";
            sta.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void addUser (List<User> users) { //已测试
        try {
            Statement statement = mConnect.createStatement();
            for (User user : users) {
                try {
                    String string = user.getUserString();
                    System.out.println(string);
                    statement.executeUpdate("INSERT INTO test.user(username,password,isAdmin,description,groupID,grade1,grade2,grade3,grade4,grade5,rank1,rank2,rank3,rank4,rank5) VALUES " + string);
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
                List<Integer> ranks = new ArrayList<>();
                List<Award> awards = new ArrayList<>();

                grades.add(resultSet.getInt("grade1"));
                grades.add(resultSet.getInt("grade2"));
                grades.add(resultSet.getInt("grade3"));
                grades.add(resultSet.getInt("grade4"));
                grades.add(resultSet.getInt("grade5"));

                for (int i = 1; i <= User.MAX_GRADE_NUMBER; i++) {
                    String temp = "rank" + i;
                    ranks.add(resultSet.getInt(temp));

                }

                for (int i = 1; i <= User.MAX_AWARD; i++) {
                    String temp1 = "title" + i;
                    String temp2 = "time" + i;
                    if (resultSet.getString(temp1) != null && resultSet.getString(temp2) != null && resultSet.getString(temp1) != "" && resultSet.getString(temp2) != "") {
                        awards.add(new Award(resultSet.getString(temp1), resultSet.getString(temp2)));
                    }
                }

                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("isAdmin"),
                        resultSet.getString("description"),
                        resultSet.getInt("groupID"),
                        grades,
                        ranks,
                        awards
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

            while (resultSet.next()) {
                List<Integer> grades = new ArrayList<Integer>();
                List<Integer> rank = new ArrayList<>();
                List<Award> awards = new ArrayList<>();
                grades.add(resultSet.getInt("grade1"));
                grades.add(resultSet.getInt("grade2"));
                grades.add(resultSet.getInt("grade3"));
                grades.add(resultSet.getInt("grade4"));
                grades.add(resultSet.getInt("grade5"));

                for (int i = 1; i <= 5; i++) {
                    String temp = "rank" + i;
                    rank.add(resultSet.getInt(temp));

                }

                for (int i = 1; i <= User.MAX_AWARD; i++) {
                    String temp1 = "title" + i;
                    String temp2 = "time" + i;
                    if (resultSet.getString(temp1) != null && resultSet.getString(temp2) != null && resultSet.getString(temp1) != "" && resultSet.getString(temp2) != "") {
                        awards.add(new Award(resultSet.getString(temp1), resultSet.getString(temp2)));
                    }
                }

                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("isAdmin"),
                        resultSet.getString("description"),
                        resultSet.getInt("groupID"),
                        grades,
                        rank,
                        awards
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
            group.updateRank();

            for (int j = 1; j <= User.MAX_GRADE_NUMBER; j++) {
                String a = "rank" + j;
                String t = "update test.user set " + a + "=0" + " where username=\"" + user.getUsername() + "\"";
                sta.executeUpdate(t);
            }

            List<User> users = group.getUsers();
            for (User user1 : users) {
                for (int j = 1; j <= User.MAX_GRADE_NUMBER; j++) {
                    String a = "rank" + j;
                    String t = "update test.user set " + a + "=" + user1.getRanks().get(j - 1) + " where username=\"" + user1.getUsername() + "\"";
                    sta.executeUpdate(t);
                }
            }

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

            }
            groups = new Group(id, users);
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

        try {
            List<User> users = group.getUsers();
            Statement sta = mConnect.createStatement();
            for (User item : users) {
                String username = item.getUsername();
                String sql1 = "update test.user set groupID=-1 where username=\"" + username + "\";";
                sta.executeUpdate(sql1);
                for (int j = 1; j <= User.MAX_GRADE_NUMBER; j++) {
                    String a = "rank" + j;
                    String t = "update test.user set " + a + "=0" + " where username=\"" + item.getUsername() + "\"";
                    sta.executeUpdate(t);
                }
            }

            for (int i = 1; i <= 10; i++) {
                String temp = "username" + i;
                String sql = "update test.group set " + temp + "=null where id=" + id + ";";
                sta.executeUpdate(sql);
            }

            group.getUsers().clear();
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
            group.addUser(user);

            sta.executeUpdate(sql2);

            List<User> users = group.getUsers();
            for (User user1 : users) {
                for (int j = 1; j <= User.MAX_GRADE_NUMBER; j++) {
                    String a = "rank" + j;
                    String t = "update test.user set " + a + "=" + user1.getRanks().get(j - 1) + " where username=\"" + user1.getUsername() + "\"";
                    sta.executeUpdate(t);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

    public Award getAwardByUsernameAndName (String username, String awardname) {
        User user = getUserByUsername(username);
        if (user == null) {
            return null;
        }

        for (Award item : user.getAwards()) {
            if (item.getTitle().equals(username)) {
                return item;
            }
        }
        return null;
    }

    public boolean appendAwardByUsername (String username, Award award) {
        User user = getUserByUsername(username);

        if (user == null) {
            return false;
        }

        if (user.getAwards().size() >= User.MAX_AWARD) {
            return false;
        }

        user.getAwards().add(award);
        try {
            Statement sta = mConnect.createStatement();
            for (int i = 0; i < user.getAwards().size(); i++) {
                int j = i + 1;
                String t1 = "title" + j;
                String t2 = "time" + j;
                String temp1 = "update test.user set " + t1 + "=\"" + user.getAwards().get(i).getTitle() + "\" where username=\"" + username + "\"";
                String temp2 = "update test.user set " + t2 + "=\"" + user.getAwards().get(i).getTime() + "\" where username=\"" + username + "\"";
                sta.executeUpdate(temp1);
                sta.executeUpdate(temp2);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


    public boolean appendAwardByUsername (String username, String title, String time) {
        User user = getUserByUsername(username);

        if (user == null) {
            return false;
        }

        if (user.getAwards().size() >= User.MAX_AWARD) {
            return false;
        }


        int t = user.getAwards().size() + 1;
        String temp1 = "title" + t;
        String temp2 = "time" + t;

        try {

            String sql1 = "update test.user set " + temp1 + "=\"" + title + "\" where username=\"" + username + "\"";
            String sql2 = "update test.user set " + temp2 + "=\"" + time + "\" where username=\"" + username + "\"";
            System.out.println(sql1);
            System.out.println(sql2);

            Statement sta = mConnect.createStatement();
            sta.executeUpdate(sql1);
            sta.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Award award = new Award(title, time);
        user.getAwards().add(award);
        return true;
    }

    public boolean deleteAwardByUsernameAndName (String username, String title) {
        User user = getUserByUsername(username);

        if (user == null) {
            return false;
        }

        int i = AwardIndex(title, user.getAwards());
        if (i < 0) {
            return false;
        }

        if (i == user.getAwards().size() - 1) {
            try {
                Statement sta = mConnect.createStatement();
                String t1 = "title" + user.getAwards().size();
                String t2 = "time" + user.getAwards().size();

                String sql1 = "update test.user set " + t1 + "=null where username=\"" + username + "\"";
                String sql2 = "update test.user set " + t2 + "=null where username=\"" + username + "\"";

                System.out.println(sql1);
                System.out.println(sql2);
                sta.executeUpdate(sql1);
                sta.executeUpdate(sql2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {

                for (int j = i; j < user.getAwards().size() - 1; j++) {
                    int k = j + 1;
                    String pre1 = "title" + k;
                    String pre2 = "time" + k;

                    k++;
                    String next1 = "title" + k;
                    String next2 = "time" + k;

                    String sql1 = "select * from test.user where username=\"" + username + "\";";
                    Statement sta = mConnect.createStatement();
                    ResultSet set1 = sta.executeQuery(sql1);


                    String value1 = "";
                    String value2 = "";
                    while (set1.next()) {
                        value1 = set1.getString(next1);
                        value2 = set1.getString(next2);
                    }

                    System.out.println(sql1);
                    String sql3 = "update test.user set " + pre1 + "=\"" + value1 + "\" where username=\"" + username + "\";";
                    String sql4 = "update test.user set " + pre2 + "=\"" + value2 + "\" where username=\"" + username + "\";";
                    System.out.println(sql3);
                    System.out.println(sql4);


                    sta.executeUpdate(sql3);
                    sta.executeUpdate(sql4);
                }

                String t1 = "title" + user.getAwards().size();
                String t2 = "time" + user.getAwards().size();

                String sql1 = "update test.user set " + t1 + "=null where username=\"" + username + "\"";
                String sql2 = "update test.user set " + t2 + "=null where username=\"" + username + "\"";
                Statement sta = mConnect.createStatement();
                sta.executeUpdate(sql1);
                sta.executeUpdate(sql2);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        user.getAwards().remove(i);

        return true;

    }

    public void clearAllAwardsByUsername (String username) {

        for (int i = 1; i <= User.MAX_AWARD; i++) {
            String temp1 = "title" + i;
            String temp2 = "time" + i;

            try {
                Statement sta = mConnect.createStatement();
                String sql1 = "update test.user set " + temp1 + "=null where username=\"" + username + "\";";
                String sql2 = "update test.user set " + temp2 + "=null where username=\"" + username + "\";";

                sta.executeUpdate(sql1);
                sta.executeUpdate(sql2);

            } catch (SQLException e) {
                e.printStackTrace();

            }


        }

    }

    public boolean setDescriptionByUsername (String username, String description) {
        User user = getUserByUsername(username);

        if (user == null) {
            return false;
        }
        try {
            Statement sta = mConnect.createStatement();
            String sql = "update test.user set description=\"" + description + "\" where username=\"" + username + "\"";
            sta.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


    public static void main (String[] args) {
        Mysql mysql = new Mysql(MysqlManager.getConnection());

    }
}

