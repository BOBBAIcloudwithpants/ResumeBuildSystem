package GUI.GUIController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;

import Controller.UserController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Award;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TeacherPageController {
    @FXML
    private Text usernameText;

    @FXML
    private Text groupIDText;

    @FXML
    private TableView<ScoreInforForTeacher> studentScoreTable;

    @FXML
    private TableColumn<ScoreInforForTeacher, String> studentNameColumn;

    @FXML
    private TableColumn<ScoreInforForTeacher, String> scoreColumn;

    @FXML
    private MenuButton subjectSelector;

    @FXML
    private MenuItem subject1;

    @FXML
    private MenuItem subject2;

    @FXML
    private MenuItem subject3;

    @FXML
    private MenuItem subject4;

    @FXML
    private MenuItem subject5;

    @FXML
    private PieChart pieChart;

    @FXML
    private ToggleGroup editOrSubmit;

    @FXML
    private ToggleButton editScoreButton;

    @FXML
    private ToggleButton submitEditButton;


    private final ObservableList<ScoreInforForTeacher> scoreData = FXCollections.observableArrayList();
    private final ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
    private int groupID;
    private int currentSubjectID;

    //根据科目更新tableView
    public void reset(String teacherName) {
        currentSubjectID = 0;
        UserController controller = new UserController();
        //get all students
        User teacher = controller.getUserByUsername(teacherName);
        groupID = teacher.getGroupID();
        //get grade by subject
        //default subject1
        setProfile(teacherName, groupID);
        setScoreTable(groupID, 0);
        setPieChart();
        subjectSelector.setText("Java");
    }

    private void setProfile(String username, int groupID) {
        usernameText.setText(username);
        groupIDText.setText(String.valueOf(groupID));
    }

    //根据 groupID 和科目更新成绩表格
    private void setScoreTable(int groupID, int subjectID) {
        scoreData.clear();
        UserController controller = new UserController();
        List<User> students =  controller.getStudentsByGroupID(groupID);
        for (int i =0; i< students.size();i++) {
            User student = students.get(i);
            String studentName = student.getUsername();
            int grade = controller.getGradeByUsernameAndSubject(studentName, subjectID);
            scoreData.add(new ScoreInforForTeacher("subject" + subjectID , studentName, grade));
        }
        studentNameColumn.setCellValueFactory(scoreData->scoreData.getValue().getStudentName());
        scoreColumn.setCellValueFactory(scoreData->scoreData.getValue().getScore());
        studentScoreTable.setItems(scoreData);
    }

    @FXML
    void editScore(ActionEvent event) {
        studentScoreTable.setEditable(true);
        scoreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        /*scoreColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ScoreInforForTeacher, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ScoreInforForTeacher, String> t) {
                        ((ScoreInforForTeacher) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setScore(Integer.parseInt(t.getNewValue()));
                    }
                }
        );*/
    }


    @FXML
    void submitEdit(ActionEvent event) {
        UserController controller = new UserController();

        for(int i = 0; i < scoreData.size(); i++){
            String username = scoreData.get(i).getStudentName().getValue();
            int grade = Integer.parseInt(scoreData.get(i).getScore().getValue());
            usernameText.setText(username+ " " + grade);
            controller.appendGradeOfStudent(username, currentSubjectID, grade);
        }
    }

    //处理科目选择的逻辑
    @FXML
    void changeSubjectOne(ActionEvent event) {
        setScoreTable(groupID, 0);
        subjectSelector.setText("Java");
        setPieChart();
    }

    @FXML
    void changeSubjectTwo(ActionEvent event) {
        setScoreTable(groupID, 1);
        subjectSelector.setText("计组");
        setPieChart();
    }

    @FXML
    void changeSubjectThree(ActionEvent event) {
        setScoreTable(groupID, 2);
        subjectSelector.setText("数值");
        setPieChart();
    }

    @FXML
    void changeSubjectFour(ActionEvent event) {
        setScoreTable(groupID, 3);
        subjectSelector.setText("概统");
        setPieChart();
    }

    @FXML
    void changeSubjectFive(ActionEvent event) {
        setScoreTable(groupID, 4);
        subjectSelector.setText("Web");
        setPieChart();
    }

    //处理饼图
    private void setPieChart() {
        chartData.clear();
        int count[] = new int[5];
        for (int i = 0; i < scoreData.size(); i++) {
            Integer grade = Integer.parseInt(scoreData.get(i).getScore().getValue());
            if (grade < 60){
                count[0]++;
            }
            else if (grade < 70){
                count[1]++;
            }
            else if (grade < 80){
                count[2]++;
            }
            else if (grade < 90){
                count[3]++;
            }
            else {
                count[4]++;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (count[i] >= 0) {
                String interval;
                if (i == 0) {
                    interval = "0-60";
                }
                else if(i < 4) {
                    interval = (60 + 10 * (i - 1)) + "-"+ (69 + 10 * (i - 1));
                }
                else {
                    interval = "90-100";
                }
                double percentage = (double)count[i] / (scoreData.size()) * 100;
                int percent = (int) percentage;
                chartData.add(new PieChart.Data(interval,percent));
            }
        }
        pieChart.setData(chartData);
    }

}



class ScoreInforForTeacher {
    private final StringProperty t_subject;
    private final StringProperty t_studentName;
    private final StringProperty t_score;

    public ScoreInforForTeacher(String subject, String studentName, Integer score){
        t_subject = new SimpleStringProperty(subject);
        t_studentName = new SimpleStringProperty(studentName);
        t_score = new SimpleStringProperty(String.valueOf(score));
    }

    public void setSubject(String subject){
        t_subject.set(subject);
    }

    public StringProperty getSubject(){
        return t_subject;
    }

    public void setScore(int score){
        t_score.set(String.valueOf(score));
    }

    public StringProperty getScore(){
        return t_score;
    }

    public void setStudentName(String studentName){
        t_studentName.set(studentName);
    }

    public StringProperty getStudentName(){
        return t_studentName;
    }
}