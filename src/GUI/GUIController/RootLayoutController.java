package GUI.GUIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class RootLayoutController {

    @FXML
    private MenuItem MenuInHelpMenu;

    @FXML
    void gotoMenuPage(ActionEvent event) {
        MainApp.gotoMenuPage();
    }

}
