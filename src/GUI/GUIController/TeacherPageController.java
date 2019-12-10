package GUI.GUIController;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import Controller.UserController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.util.List;
import java.util.ListIterator;

public class TeacherPageController {
    @FXML
    private Text username;

    @FXML
    private Text groupID;

    @FXML
    private TableView<ScoreInforForTeacher> studentScoreTable;

    @FXML
    private TableColumn<ScoreInforForTeacher, String> studentNameColumn;

    @FXML
    private TableColumn<ScoreInforForTeacher, Integer> scoreColumn;

    @FXML
    private MenuButton subjectSelector;

    @FXML
    private MenuItem subject1;

    @FXML
    private MenuItem subject2;

    @FXML
    private MenuItem subject3;

    private final ObservableList<ScoreInforForTeacher> data = FXCollections.observableArrayList();

    //根据科目更新tableView
    public void reset(String teacherName, int subjectID) {
        UserController controller = new UserController();
        //get all students
        User teacher = controller.getUserByUsername(teacherName);
        int groupID = teacher.getGroupID();
        List<User> students =  controller.getStudentsByGroupID(groupID);
        //get grade by subject
        //default subject1
        for (int i =0; i< students.size();i++) {
            User student = students.get(i);
            String studentName = student.getUsername();
            int grade = controller.getGradeByUsernameAndSubject(studentName, subjectID);
            data.add(new ScoreInforForTeacher("subject" + subjectID , studentName, grade));
        }

        studentNameColumn.setCellValueFactory(data->data.getValue().getStudentName());
        scoreColumn.setCellValueFactory(data->data.getValue().getScore().asObject());
    }

    //处理科目选择的逻辑

    //处理饼图


}

class ScoreInforForTeacher {
    private final StringProperty t_subject;
    private final StringProperty t_studentName;
    private final IntegerProperty t_score;

    public ScoreInforForTeacher(String subject, String studentName, Integer score){
        t_subject = new SimpleStringProperty(subject);
        t_studentName = new SimpleStringProperty(studentName);
        t_score = new SimpleIntegerProperty(score);
    }

    public void setSubject(String subject){
        t_subject.set(subject);
    }

    public StringProperty getSubject(){
        return t_subject;
    }

    public void setScore(int score){
        t_score.set(score);
    }

    public IntegerProperty getScore(){
        return t_score;
    }

    public void setStudentName(String studentName){
        t_studentName.set(studentName);
    }

    public StringProperty getStudentName(){
        return t_studentName;
    }
}