package GUI.GUIController;

import Controller.UserController;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentPageController implements Initializable {

    @FXML
    private Text username;

    @FXML
    private Text grade;

    @FXML
    private Text admin;

    @FXML
    private TableView<ScoreInformation> scoretable;

    @FXML
    private TableColumn<ScoreInformation, String> subject;

    @FXML
    private TableColumn<ScoreInformation, Integer> score;

    @FXML
    private TableColumn<ScoreInformation, Integer> rank;

    private final ObservableList<ScoreInformation> data = FXCollections.observableArrayList();

    @FXML
    private TableView<User> awardtable;

    @FXML
    private TableColumn<User, String> awardname;

    @FXML
    private TableColumn<User, String> awardtime;

    @FXML
    private PieChart scorechart;

    @FXML
    private TextArea description;

    @FXML
    private Button submit;

    private UserController usercontroller;

    private String userName;

    //public static PassParameter passParameter = new PassParameter();

    void setUserName(String name){
        userName = name;
    }

    @FXML
    void submitdescriptionandaward(ActionEvent event) {

    }

    public void reset(){

        //User user = usercontroller.getUserByUsername(userName);
        List<Integer> grades = new ArrayList<Integer>();
        grades.add(99);
        grades.add(93);
        List<Integer> ranks = new ArrayList<Integer>();
        ranks.add(1);
        ranks.add(3);
        List<String> subjects = new ArrayList<String>();
        subjects.add("语文");
        subjects.add("英语");

        User user = new User("bob","123",0,"I'm bob.",1, grades, ranks, subjects);
        username.setText("姓名："+user.getUsername());
        grade.setText("班级："+user.getGroupID());
        if(user.getIsAdmin()==1){
            admin.setText("身份：老师");
        }
        else{
            admin.setText("身份：学生");
        }
        description.setText(user.getDescription());
        for(int i = 0; i < 2; i++){
            data.add(new ScoreInformation(subjects.get(i), grades.get(i), ranks.get(i)));
        }
        subject.setCellValueFactory(data->data.getValue().getTsubject());
        score.setCellValueFactory(data->data.getValue().getTscore().asObject());
        rank.setCellValueFactory(data->data.getValue().getTrank().asObject());
        scoretable.setItems(data);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        usercontroller = new UserController();

    }
}