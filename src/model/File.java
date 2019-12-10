package model;

import database.Mysql;
import database.MysqlManager;

import java.util.ArrayList;
import java.util.List;

public class File {

    private String name; //group id or user name

    private int id;

    private Mysql mysql;


    private String outcome;


    public File (String name) {
        this.name = name;
        id = -1;
        outcome = "";
        mysql = new Mysql(MysqlManager.getConnection());
    }

    public File (int id) {
        this.id = id;
        outcome = "";
        mysql = new Mysql(MysqlManager.getConnection());
    }

    public String format(String target, int length){
        for(int i = 0;i<length-target.length();i++){
            target+=" ";
        }

        return target;
    }

    public static String reverse1(String str) {
        return new StringBuilder(str).reverse().toString();
    }
    public String transfer(int num){
        if(num == 0){
            return String.valueOf(0);
        }
        String outcome = "";
        while(num != 0){
            outcome += String.valueOf(num%10);
            num /= 10;
        }
        outcome = reverse1(outcome);
        return outcome;
    }
    public String getFile () {
        outcome = "";
        if (id == -1) {
            User user = mysql.getUserByUsername(name);
            outcome += "Student Name:\t" + name + "\n\n";
            if (user.getGroupID() != -1) {
                outcome += "Student Group:\t" + user.getGroupID() + "\n\n";
            }
            outcome += "Description:\n" + user.getDescription() + "\n\n";
            outcome += "Award:\n";
            outcome += "Date\tTitle\t\n";
            for (Award a : user.getAwards()) {
                outcome += a.getTime() + "\t" + a.getTitle() + "\t\n";
            }
            outcome += "\n";

            outcome += "\nGrade:\n";
            outcome += "Subject\tGrade\tRank\t\n";
            for (int i = 0; i < User.MAX_GRADE_NUMBER; i++) {
                outcome += "Subject\t";
                outcome += user.getGrades().get(i) + "\t\t";
                outcome += user.getRanks().get(i) + "\t\n";
            }
        } else {
            Group group = mysql.getGroupById(id);
            outcome += "Group ID:\t" + id + "\n\n";


            outcome += "Students' Grade:\n";

            outcome += format("name", 10);
            for(int i = 1;i<=User.MAX_GRADE_NUMBER;i++){
                outcome += format("s"+i, 10)+format("r"+i, 10);
            }
            outcome += "\n";
            for(User user : group.getUsers()){
                outcome += format(user.getUsername(),10) ;
                for(int i = 0;i<User.MAX_GRADE_NUMBER;i++){
                    outcome += format(transfer(user.getGrades().get(i)), 10) + format(transfer(user.getRanks().get(i)),10);
                }
                outcome += "\n";
            }
        }
        return outcome;
    }

}
