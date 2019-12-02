package model;


import java.util.ArrayList;
import java.util.List;

public class Group {
    public static int MAX_CAPACITY = 20;
    private int groupID;
    private List<User> users;


    public Group (int groupID) {
        users = new ArrayList<User>();
        this.groupID = groupID;
    }

    public Group( int groupID, List<User> users){
        this.groupID = groupID;
        this.users = users;
    }
    public void addUser(User user){
        users.add(user);

    }

    public int getGroupID () {
        return groupID;
    }

    public void setGroupID (int groupID) {
        this.groupID = groupID;
    }

    public List<User> getUsers(){
        return users;
    }



}
