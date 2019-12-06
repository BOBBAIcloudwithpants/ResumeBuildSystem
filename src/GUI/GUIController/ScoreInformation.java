package GUI.GUIController;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;


public class ScoreInformation {
    private final StringProperty tsubject;
    private final IntegerProperty tscore;
    private final IntegerProperty trank;

    public ScoreInformation(String subject, Integer score, Integer rank){
        tsubject = new SimpleStringProperty(subject);
        tscore = new SimpleIntegerProperty(score);
        trank = new SimpleIntegerProperty(rank);
    }

    public void setTsubject(String subject){
        tsubject.set(subject);
    }

    public StringProperty getTsubject(){
        return tsubject;
    }

    public void setTscore(int score){
        tscore.set(score);
    }

    public IntegerProperty getTscore(){
        return tscore;
    }

    public void setTrank(int rank){
        trank.set(rank);
    }

    public IntegerProperty getTrank(){
        return trank;
    }
}
