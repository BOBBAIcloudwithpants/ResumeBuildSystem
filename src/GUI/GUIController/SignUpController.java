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

import java.io.PrintWriter;
import java.net.Socket;

public class SignUpController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordConfirm;

    @FXML
    private TextField groupIDField;

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

    private Socket socket = null;
    private PrintWriter output = null;

    @FXML
    void signUp(ActionEvent event) {
        UserController user_controller = new UserController();
        //check username
        if (username.getText().equals("")) {
            errmsg.setText("用户名不能为空");
            return;
        }
        //check groupID
        if (groupIDField.getText().equals("")) {
            errmsg.setText("班级序号不能为空");
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
        boolean success = user.registerUser(username.getText(), password.getText(), isAdmin, Integer.parseInt(groupIDField.getText()));
        if (!success) {
            errmsg.setText("用户已存在");
        }
        else {
            if(isAdmin == 1){
                try {
                    socket = new Socket("localhost",1056);
                    output = new PrintWriter(socket.getOutputStream());
                    output.println(username.getText());
                    output.flush();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                MainApp.gotoTeacherPage();
            }
            else{
                try {
                    socket = new Socket("localhost",1056);
                    output = new PrintWriter(socket.getOutputStream());
                    output.println(username.getText());
                    output.flush();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                MainApp.gotoStudentPage();
            }
        }
    }


}