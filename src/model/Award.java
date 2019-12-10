package model;

public class Award {

    private String time; //YYYY-MM-DD

    private String title;

    public String getTime () {
        return time;
    }

    public void setTime (String time) {
        this.time = time;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public Award(String title, String time){
        this.time = time;
        this.title = title;
    }
}
