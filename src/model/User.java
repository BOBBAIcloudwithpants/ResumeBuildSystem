package model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class User {
    public static int MAX_GRADE_NUMBER = 5;
    public static int MAX_AWARD = 4;
    private String username;
    private String password;
    private String description;
    private int isAdmin;
    private int groupID;
    private List<Integer> grades;
    private List<Integer> ranks;
    private List<Award> awards;


    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public User(String username, String password ,int isAdmin){ //注册时使用
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        grades = new ArrayList<Integer>();
        for(int i = 0;i<MAX_GRADE_NUMBER;i++){
            grades.add(0);
        }
        ranks = new ArrayList<Integer>();
        for(int i = 0;i<MAX_GRADE_NUMBER;i++){
            ranks.add(0);
        }
        groupID = -1;
        awards = new ArrayList<Award>();
        for(int i = 0;i<MAX_AWARD;i++) {

        }

    }


    public List<Award> getAwards () {
        return awards;
    }

    public void setAwards (List<Award> awards) {
        this.awards = awards;
    }

    public User(String username, String password , int isAdmin, String description, int groupID, List<Integer> grades, List<Integer> ranks, List<Award> awards){
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.description = description;
        this.groupID = groupID;
        this.grades = grades;
        this.ranks = ranks;
        this.awards = awards;
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

    public int getGroupID () {
        return groupID;
    }

    public void setGroupID (int groupID) {
        this.groupID = groupID;
    }

    public List<Integer> getGrades () {
        return grades;
    }

    public void setGrades (List<Integer> grades) {
        this.grades = grades;
    }

    public int getGradeById(int id){
        return grades.get(id);
    }

    public String getUserString(){
        return "("+"'"+username+"','"+password+"',"+isAdmin+")";
    }

    public List<Integer> getRanks () {
        return ranks;
    }

    public void setRanks (List<Integer> ranks) {
        this.ranks = ranks;
    }
}
