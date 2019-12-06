package GUI.GUIController;

import Controller.UserController;
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
import java.util.ResourceBundle;

public class StudentPageController implements Initializable {

    @FXML
    private Text username;

    @FXML
    private Text grade;

    @FXML
    private Text admin;

    @FXML
    private TableView<?> scoretable;

    @FXML
    private TableColumn<?, ?> subject;

    @FXML
    private TableColumn<?, ?> score;

    @FXML
    private TableView<?> awardtable;

    @FXML
    private TableColumn<?, ?> awardname;

    @FXML
    private TableColumn<?, ?> awardtime;

    @FXML
    private PieChart scorechart;

    @FXML
    private TextArea description;

    @FXML
    private Button submit;

    private UserController usercontroller;

    @FXML
    void submitdescriptionandaward(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usercontroller = new UserController();
        User user = usercontroller.getUserByUsername(userName);
        username.setText("姓名："+user.getUsername());
        grade.setText("班级："+user.getGroupID());
        if(user.getIsAdmin()==1){
            admin.setText("身份：老师");
        }
        else{
            admin.setText("身份：学生");
        }
    }
}