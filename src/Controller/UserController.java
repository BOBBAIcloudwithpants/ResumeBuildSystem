package Controller;

import database.*;
import model.Award;
import model.File;
import model.Group;
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

    public User getUserByUsername (String username) {
        return mysql.getUserByUsername(username);
    }

    public boolean userLogin (String username, String password) {
        User user = mysql.getUserByUsername(username);
        if (user == null) {
            System.out.println(1);
            return false;
        }

        if (!user.getPassword().equals(password)) {
            System.out.println(2);
            return false;
        }
        return true;
    }

    public Group getGroupById (int groupID) {
        return mysql.getGroupById(groupID);
    }

    public boolean registerUser (String username, String password, int isAdmin, int group) {
        User user = mysql.getUserByUsername(username);
        if (user != null) {
            return false; // 已注册
        }

        if (getGroupById(group) == null) {
            return false;
        }
        List<User> users = new ArrayList<User>();
        users.add(new User(username, password, isAdmin));
        mysql.addUser(users);
        appendUserIntoGroup(username, group);
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

    public boolean appendUserIntoGroup (String username, int id) {
        return mysql.appendUserIntoGroup(username, id);
    }

    public boolean appendGradeOfStudent (String username, int id, int grade) {
        if (isAdmin(username) == true) {
            return false;
        }

        return mysql.appendGradeByUsernameAndId(username, id, grade);
    }

    public boolean setDescriptionByUsername (String username, String description) {
        User user = getUserByUsername(username);
        if (user == null) {
            return false;
        }
        user.setDescription(description);

        return mysql.setDescriptionByUsername(username, description);
    }

    public String getAwardnameByName (String username, String awardname) {
        return mysql.getAwardByUsernameAndName(username, awardname).getTitle();
    }

    public String getAwardtimeByName (String username, String awardname) {
        return mysql.getAwardByUsernameAndName(username, awardname).getTime();
    }

    public void deleteAllAwardsByUsername (String username) {
        mysql.clearAllAwardsByUsername(username);
    }

    public boolean deleteAwardByUsernameAndAwardname (String username, String awardname) {
        return mysql.deleteAwardByUsernameAndName(username, awardname);
    }

    public boolean appendAwardByUsername (String username, String title, String time) {
        return mysql.appendAwardByUsername(username, title, time);
    }

    public boolean setAwardsByUsername (String username, List<Award> awards) {
        User user = getUserByUsername(username);
        boolean flag = true;

        for (Award a : awards) {
            flag = appendAwardByUsername(username, a.getTitle(), a.getTime());
            if (flag == false) {
                return false;
            }
        }
        return true;
    }

    public boolean resetAwardsByUsername (String username, List<Award> awards) {
        User user = getUserByUsername(username);
        if (user == null) {
            return false;
        }

        deleteAllAwardsByUsername(username);


        return setAwardsByUsername(username, awards);
    }

    public boolean createGroup (int id) {
        return mysql.createGroup(id);
    }

    public String getStudentFile (String username) {
        return new File(username).getFile();
    }

    public String getGroupFile (int id) {
        return new File(id).getFile();
    }

    public static void main (String[] args) {
        UserController userController = new UserController();

        userController.registerUser("wjs", "12345", 1, 5);

    }


}
