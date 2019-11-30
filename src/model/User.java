package model;

public class User {
    private String username;
    private String password;
    private String description;
    private int isAdmin;
    private int group;

    public int getGroup () {
        return group;
    }

    public void setGroup (int group) {
        this.group = group;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        isAdmin = 0;
    }


    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getPassword () {
        return password;
    }

    public int getIsAdmin () {
        return isAdmin;
    }

    public void setIsAdmin (int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setPassword (String password) {
        this.password = password;
    }
}
