package GUI.GUIController;


import Controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import Controller.UserController.*;
import GUI.GUIController.MainApp;
import model.User;


public class SignInController implements Initializable {
    private UserController usercontroller;
    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Button SignInButton;

    @FXML
    private Button ToRegisterButton;

    @FXML
    private Text tips;

    @FXML
    void ToMain(ActionEvent event) {
        usercontroller = new UserController();

        String UserName = username.getText();
        String Password = password.getText();

        if(usercontroller.userLogin(UserName, Password)){
            if(usercontroller.isAdmin(UserName)){
                MainApp.gotoTeacherPage(UserName);
            }
            else{
                MainApp.gotoStudentPage(UserName);
            }
        }
        else{
            tips.setText("登录失败，用户名或密码错误。");
        }
    }

    @FXML
    void ToRegister(ActionEvent event) {
        MainApp.gotoSignUpPage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
