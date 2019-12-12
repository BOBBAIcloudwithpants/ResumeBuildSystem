package GUI.GUIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
//import sun.applet.Main;

public class MenuPageController {

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void signIn(ActionEvent event) {
        MainApp.gotoSignInPage();
    }

    @FXML
    void signUp(ActionEvent event) {
        MainApp.gotoSignUpPage();
    }

}