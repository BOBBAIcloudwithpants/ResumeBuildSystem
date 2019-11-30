package model;

public class Teacher {
    private int id;
    private String username;
    private String password;
    private int group;
    private int isAdmin;

    public Teacher(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
        int isAdmin = 1;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public int getGroup () {
        return group;
    }

    public void setGroup (int group) {
        this.group = group;
    }

    public int getIsAdmin () {
        return isAdmin;
    }

    public void setIsAdmin (int isAdmin) {
        this.isAdmin = isAdmin;
    }
}
