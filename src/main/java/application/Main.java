package application;

import application.controller.DukeController;
import application.controller.PopUpMessageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static final String WELCOME = "Welcome to OrgaNice!";
    public static final String LOGIN = "login.fxml";
    public static Stage currentStage;

    @Override
    public void start(Stage stage) throws Exception {
        try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(DukeController.class.getClassLoader().getResource(LOGIN)));
                Scene scene = new Scene(root);
                stage.setTitle(WELCOME);
                stage.setScene(scene);
                stage.show();
                currentStage = stage;
        } catch (Exception e) {
            PopUpMessageController.createExceptionMessage(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}