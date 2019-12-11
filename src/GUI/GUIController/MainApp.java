package GUI.GUIController;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private static BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ResumeBuildSystem");

        initRootLayout();
        gotoMenuPage();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void gotoMenuPage() {
        try {
            // Load student page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/MenuPage.fxml"));
            AnchorPane menuPage = (AnchorPane) loader.load();

            // Set student page into the center of root layout
            rootLayout.setCenter(menuPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void gotoSignUpPage() {
        try {
            // Load sign up page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/SignUpController.fxml"));
            AnchorPane signUpPage = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(signUpPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void gotoSignInPage() {
        try {
            // Load sign in page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/SignInController.fxml"));
            AnchorPane signInPage = (AnchorPane) loader.load();

            // Set sign in page into the center of root layout
            rootLayout.setCenter(signInPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void gotoStudentPage(String username) {
        try {
            // Load student page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/StudentPageController.fxml"));
            AnchorPane studentPage = (AnchorPane) loader.load();

            StudentPageController controller = loader.<StudentPageController>getController();
            controller.setUserName(username);
            controller.reset();
            // Set student page into the center of root layout
            rootLayout.setCenter(studentPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void gotoTeacherPage(String username) {
        try {
            // Load student page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/TeacherPageController.fxml"));
            AnchorPane teacherPage = (AnchorPane) loader.load();

            TeacherPageController controller = loader.<TeacherPageController>getController();
            controller.reset(username);

            // Set student page into the center of root layout
            rootLayout.setCenter(teacherPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return main stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}