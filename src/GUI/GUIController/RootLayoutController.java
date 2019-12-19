package GUI.GUIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;

public class RootLayoutController {

    @FXML
    private MenuItem MenuInHelpMenu;

    @FXML
    private MenuItem aboutButton;


    @FXML
    void showAbout(ActionEvent event) {
        String info="简历生成小助手\n版本 1.0";
        Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
        alert.setHeaderText(null);
        alert.setTitle("关于小助手");
        alert.show();
    }

    @FXML
    void gotoMenuPage(ActionEvent event) {
        MainApp.gotoMenuPage();
    }

}



