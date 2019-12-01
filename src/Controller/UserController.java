package Controller;

import database.*;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private Mysql mysql;

    public UserController(){
        mysql = new Mysql(MysqlManager.getConnection());
    }

    public boolean findUserByName(String username){
        User user = mysql.getUserInfos(username);
        if(user != null){
            return true;
        }
        return false;
    }

    public boolean userLogin(String username, String password){
        User user = mysql.getUserInfos(username);
        if(user == null){
            return false;
        }
        if(user.getPassword() != password){
            return false;
        }
        return true;
    }

    public boolean registerUser(String username, String password, int isAdmin){
        User user = mysql.getUserInfos(username);
        if(user != null){
            return false; // 已注册
        }
        List<User> users = new ArrayList<User>();
        users.add(new User(username, password, isAdmin));
        mysql.addUser(users);
        return true;
    }

    public boolean isAdmin(String username){
        User user = mysql.getUserInfos(username);
        if(user.getIsAdmin() == 1){
            return true;
        }
        return false;
    }

    public int getGradeByUsernameAndSubject(String username, int id){
        User user = mysql.getUserInfos(username);
        return user.getGradeById(id);
    }

    public String getDescriptionByUsername(String username){
        User user = mysql.getUserInfos(username);
        return user.getDescription();
    }


}
