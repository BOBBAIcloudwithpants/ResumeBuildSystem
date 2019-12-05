package GUI.GUIController;

import Controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import Controller.UserController.*;

public class RegisterController {

    @FXML
    private TextField user_name_box;

    @FXML
    private Button sign_up_button;

    @FXML
    private ToggleGroup user_identity;

    @FXML
    private Text errmsg;

    @FXML
    void signUp(ActionEvent event) {
        UserController user_controller = new UserController();
        //check username
        if (user_name_box.getText().equals("")) {
            errmsg.setText("用户名不能为空");
            return;
        }
        boolean valid_name = user_controller.findUserByName(user_name_box.getText());
        if (!valid_name) {
            errmsg.setText("用户名已被使用");
            return;
        }
        //check password
        errmsg.setText("submit press");
    }

}
