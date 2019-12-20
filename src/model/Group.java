package model;


import java.util.ArrayList;
import java.util.List;

public class Group {
    public static int MAX_CAPACITY = 20;
    private int groupID;
    private List<User> users;
    private List<Integer>[] ranks; // 存储了每个科目用户的排名（索引）


    public void updateRank() {
        for(int i = 0;i<User.MAX_GRADE_NUMBER;i++){
            ranks[i].clear();
        }

        List<Integer> [] grades = new List[User.MAX_GRADE_NUMBER];
        for(int i = 0;i<User.MAX_GRADE_NUMBER;i++){
            grades[i] = new ArrayList<>();
        }

        for(int i = 0;i<users.size();i++) {
            for(int j = 0;j<User.MAX_GRADE_NUMBER;j++){
                grades[j].add(users.get(i).getGradeById(j));
            }
        }

        for(int i = 0;i<grades.length;i++){
            sortSingleSubject(grades[i], i);
        }


    }


    public void sortSingleSubject(List<Integer> grades, int which) {
        int [] rank = new int[users.size()];

        for(int i = 0;i<users.size();i++) {
            rank[i] = i;
        }

        for(int i = 0;i<grades.size();i++){
            for(int j = 0;j<grades.size()-1;j++){
                if(grades.get(j) < grades.get(j+1)){
                    int temp = grades.get(j);
                    grades.set(j, grades.get(j+1));
                    grades.set(j+1,temp);

                    int t2 = rank[j];
                    rank[j] = rank[j+1];
                    rank[j+1] = t2;
                }
            }
        }

        for(int i = 0;i<rank.length;i++) {
            users.get(rank[i]).getRanks().set(which, i);
        }
    }




    public Group (int groupID) {
        users = new ArrayList<User>();
        this.groupID = groupID;
        ranks = new List[User.MAX_GRADE_NUMBER];
        for (int i = 0; i < User.MAX_GRADE_NUMBER; i++) {
            ranks[i] = new ArrayList<>();
        }


}

    public Group (int groupID, List<User> users) {
        this.groupID = groupID;
        this.users = users;
        ranks = new List[User.MAX_GRADE_NUMBER];
        for (int i = 0; i < User.MAX_GRADE_NUMBER; i++) {
            ranks[i] = new ArrayList<Integer>();
        }


    }

    public void addUser (User user) {
        users.add(user);

        updateRank();
    }

    public void setUsers (List<User> users) {
        this.users = users;
    }

    public List<Integer>[] getRanks () {
        return ranks;
    }

    public void setRanks (List<Integer>[] ranks) {
        this.ranks = ranks;
    }

    public int getGroupID () {
        return groupID;
    }

    public void setGroupID (int groupID) {
        this.groupID = groupID;
    }

    public List<User> getUsers () {
        return users;
    }


}
