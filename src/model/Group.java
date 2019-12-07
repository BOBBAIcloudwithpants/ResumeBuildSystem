package model;


import java.util.ArrayList;
import java.util.List;

public class Group {
    public static int MAX_CAPACITY = 20;
    private int groupID;
    private List<User> users;
    private List<Integer>[] ranks; // 存储了每个科目用户的排名（索引）

    public void sort(int[] a, List<Integer> target){
        int []temp = new int[a.length];
        for(int i = 0;i<a.length;i++){
            temp[i] = i;
        }

        for(int i = 0;i<a.length;i++){
            for(int j = 0;j<a.length-1;j++){
                if(a[j] < a[j+1]){
                    int t = temp[j];
                    temp[j] = temp[j+1];
                    temp[j+1] = t;
                }
            }
        }


        for(int i = 0;i<temp.length;i++){
            target.add(temp[i]);
        }
    }

    public int findIndex (String username, List<User> users) { // 已测试
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
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
        for(int i = 0;i<User.MAX_GRADE_NUMBER; i++){
            int[] grade = new int[users.size()];

            for(int j = 0;j<users.size();j++){
                grade[j] = users.get(j).getGradeById(i);
            }

            sort(grade, ranks[i]);
        }
    }

    public void addUser (User user) {
        users.add(user);
        List<Integer> userRank = user.getRanks();
        List<Integer> userGrade = user.getGrades();

        for (int i = 0; i < User.MAX_GRADE_NUMBER; i++) {
            ranks[i].clear();
        }
        for(int i = 0;i<User.MAX_GRADE_NUMBER; i++){
            int[] grade = new int[users.size()];

            for(int j = 0;j<users.size();j++){
                grade[j] = users.get(j).getGradeById(i);
            }

            sort(grade, ranks[i]);
        }

        for(int k = 0 ; k<users.size() ; k++){
        for(int i = 0;i<User.MAX_GRADE_NUMBER;i++){
            for(int j = 0;j<ranks[i].size();j++){
                if(ranks[i].get(j).equals(k)){
                    users.get(k).getRanks().set(i, j);
                }
            }
        }
        }

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
