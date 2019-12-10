package GUI.GUIController;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Award;

public class AwardInformation {
    private final StringProperty title;
    private final StringProperty time;

    public AwardInformation(String title, String time){
        this.title = new SimpleStringProperty(title);
        this.time = new SimpleStringProperty(time);
    }

    public void setName(String title){
        this.title.set(title);
    }

    public StringProperty getName(){
        return title;
    }

    public void setTime(String time){
        this.time.set(time);
    }

    public StringProperty getTime(){
        return time;
    }

    public Award toAward(){
        return new Award(title.toString(), time.toString());
    }
}
