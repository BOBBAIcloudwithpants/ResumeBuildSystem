package Controller;


import database.Mysql;
import database.MysqlManager;
import model.Group;
import model.User;

public class GroupController {
    private Mysql mysql;


    public GroupController () {
        mysql = new Mysql(MysqlManager.getConnection());
    }


    public boolean isAdmin (String username) {
        User user = mysql.getUserByUsername(username);
        if (user == null || user.getIsAdmin() == 1) {
            return true;
        }
        return false;
    }

    public Group getGroupById (String username, int id) {
        if (isAdmin(username) == false) {
            return null;
        }

        return mysql.getGroupById(id);

    }

    public boolean appendUserInGroup (String username, int id) {
        if (isAdmin(username) == true) {
            return false;
        }
        return mysql.appendUserIntoGroup(username, id);
    }

    public boolean removeUserFromGroup (String username, int id) {
        if (isAdmin(username)) {
            return false;
        }

        return mysql.deleteUserInGroup(username, id);
    }

    public void deleteAllUserInGroup (int id) {
        mysql.clearAllUserOfGroup(id);
    }


}
