package GUI.GUIController;

import Controller.UserController;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.PasswordField;

import Controller.UserController.*;

public class SignUpController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordConfirm;

    @FXML
    private Button signUpButton;

    @FXML
    private RadioButton isTeacher;

    @FXML
    private ToggleGroup userAdmin;

    @FXML
    private RadioButton isStudent;

    @FXML
    private Text errmsg;


    @FXML
    void signUp(ActionEvent event) {
        UserController user_controller = new UserController();
        //check username
        if (username.getText().equals("")) {
            errmsg.setText("用户名不能为空");
            return;
        }

        //check password
        if (password.getText().length() == 0) {
            errmsg.setText("密码不能为空");
            return;
        }
        if (!password.getText().equals(passwordConfirm.getText())) {
            errmsg.setText("两次密码输入不一致");
            return;
        }

        //check admin
        if (userAdmin.getSelectedToggle() == null) {
            errmsg.setText("请选择身份");
            return;
        }

        int isAdmin = (isTeacher.isSelected()) ? 1 : 0;

        UserController user = new UserController();
        boolean success = user.registerUser(username.getText(), password.getText(), isAdmin);
        if (!success) {
            errmsg.setText("用户已存在");
        }
        else {
            if(isAdmin == 1){
                MainApp.gotoTeacherPage(username.getText());
            }
            else{
                MainApp.gotoStudentPage(username.getText());
            }
        }
    }


}