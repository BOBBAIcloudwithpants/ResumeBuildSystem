package model;

import database.Mysql;
import database.MysqlManager;

import java.util.List;

public class File {

    private String name; //group id or user name

    private int id;

    private Mysql mysql;

    private int isGroup;

    private String outcome;


    public File (String name) {
        this.name = name;
        outcome = "";
        mysql = new Mysql(MysqlManager.getConnection());
    }

    public File (int id) {
        this.id = id;
        outcome = "";
        mysql = new Mysql(MysqlManager.getConnection());
    }

    public String getFile () {
        outcome = "";
        if (isGroup == 0) {
            User user = mysql.getUserByUsername(name);
            outcome += "Student Name:\t" + name + "\n";
            if (user.getGroupID() != -1) {
                outcome += "Student Group:\t" + user.getGroupID() + "\n";
            }
            outcome += "Description:\n" + user.getDescription() + "\n";
            outcome += "Award:\n";
            outcome += "Date\tTitle\t\n";
            for (Award a : user.getAwards()) {
                outcome += a.getTime() + "\t" + a.getTitle() + "\t\n";
            }

            outcome += "\nGrade:\n";
            outcome += "Subject\tGrade\tRank\t\n";
            for (int i = 0; i < User.MAX_GRADE_NUMBER; i++) {
                outcome += "\t";
                outcome += user.getGrades().get(i) + "\t";
                outcome += user.getRanks().get(i) + "\t\n";
            }
        } else {
            Group group = mysql.getGroupById(id);
        }
        return outcome;
    }

}
