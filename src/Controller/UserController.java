package Controller;

import database.*;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private Mysql mysql;

    public UserController () {
        mysql = new Mysql(MysqlManager.getConnection());
    }

    public boolean findUserByName (String username) {
        User user = mysql.getUserByUsername(username);
        if (user != null) {
            return true;
        }
        return false;
    }

    public boolean userLogin (String username, String password) {
        User user = mysql.getUserByUsername(username);
        if (user == null) {
            return false;
        }
        if (user.getPassword() != password) {
            return false;
        }
        return true;
    }

    public boolean registerUser (String username, String password, int isAdmin) {
        User user = mysql.getUserByUsername(username);
        if (user != null) {
            return false; // 已注册
        }
        List<User> users = new ArrayList<User>();
        users.add(new User(username, password, isAdmin));
        mysql.addUser(users);
        return true;
    }

    public boolean isAdmin (String username) {
        User user = mysql.getUserByUsername(username);
        if (user.getIsAdmin() == 1) {
            return true;
        }
        return false;
    }

    public int getGradeByUsernameAndSubject (String username, int id) {
        User user = mysql.getUserByUsername(username);
        return user.getGradeById(id);
    }

    public String getDescriptionByUsername (String username) {
        User user = mysql.getUserByUsername(username);
        return user.getDescription();
    }

    public List<User> getAllStudents () {
        List<User> users = mysql.getAllUsers();
        List<User> outcome = new ArrayList<>();
        for (User user : users) {
            if (user.getIsAdmin() == 0) {
                outcome.add(user);
            }
        }

        return outcome;
    }

    public List<User> getStudentsByGroupID (int groupID) {
        List<User> users = mysql.getAllUsers();

        List<User> outcome = new ArrayList<>();

        for (User user : users) {
            if (user.getIsAdmin() == 0 && user.getGroupID() == groupID) {
                outcome.add(user);
            }
        }

        return outcome;
    }

    public boolean appendStudentToGroup(String username, int id){
        if(isAdmin(username) == true){
            return false;
        }

        return appendStudentToGroup(username, id);
    }

    public boolean appendGradeOfStudent(String username, int id, int grade){
        if(isAdmin(username) == true){
            return false;
        }

        return appendGradeOfStudent(username, id, grade);
    }







}
