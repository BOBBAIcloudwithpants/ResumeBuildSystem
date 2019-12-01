package model;


import java.util.ArrayList;
import java.util.List;

public class Group {
    private int groupID;
    private List<User> users;

    public Group (int groupID) {
        users = new ArrayList<User>();
        this.groupID = groupID;
    }

    public void addUser(User user){
        users.add(user);

    }



}
