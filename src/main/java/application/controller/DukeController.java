package application.controller;

import application.Main;
import application.storage.Storage;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;


public class DukeController {

    public static final String LOGIN_FAIL = "login fail";
    public static final String RED = "red";
    public static final String LOGIN_SUCCESS = "login success";
    public static final String WELCOME_PAGE = "WelcomePage.fxml";
    public static final String MAIN_PAGE = "MainPage.fxml";
    static Stage currentStage;
    public static final int SLEEP = 3;
    public static final int NULL_OPACITY = 0;
    public static final int FULL_OPACITY = 1;
    public static final int ONCE = 1;
    static AnchorPane rootDrawer;

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblStatus;

    public void login(ActionEvent action) {
        boolean isUserName = txtUserName.getText().equals("user");
        boolean isPassword = txtPassword.getText().equals("password");
        if (isPassword && isUserName) {
            Main.currentStage.close();
            lblStatus.setText(LOGIN_SUCCESS);
            try {
                EventTableController.getEventObservableList().addAll(Storage.loadEvents());
                DeadlineTableController.getDeadlineObservableList().addAll(Storage.loadDeadline());
                loadSplashScreen();
            } catch (IOException e) {
                PopUpMessageController.createExceptionMessage(e);
            }
        } else {
            lblStatus.setText(LOGIN_FAIL);
            lblStatus.textFillProperty().setValue(Paint.valueOf(RED));
        }
    }

    public static void loadSplashScreen() throws IOException {
        currentStage = new Stage();
        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(application.controller.DukeController.class.getClassLoader().getResource(WELCOME_PAGE)));
        Scene scene = new Scene(root, 400, 400);
        currentStage.setScene(scene);
        currentStage.show();
        startFadeIn(root);
    }

    private static void startFadeIn(AnchorPane root) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(SLEEP), root);
        fadeIn.setFromValue(NULL_OPACITY);
        fadeIn.setToValue(FULL_OPACITY);
        fadeIn.setCycleCount(ONCE);
        fadeIn.play();

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(SLEEP), root);
        fadeOut.setFromValue(FULL_OPACITY);
        fadeOut.setToValue(NULL_OPACITY);
        fadeOut.setCycleCount(ONCE);

        fadeIn.setOnFinished(e -> {
            fadeOut.play();
        });

        fadeOut.setOnFinished( e-> {
            currentStage.close();
            try {
                showMainPage();
            } catch (IOException ex) {
                PopUpMessageController.createExceptionMessage(ex);
            }
        });
    }

    public static void showMainPage() throws IOException {
        currentStage = new Stage();
        rootDrawer = FXMLLoader.load(Objects.requireNonNull(NavigationDrawerController.class.getClassLoader().getResource(MAIN_PAGE)));
        Scene scene = new Scene(rootDrawer);
        currentStage.setScene(scene);
        currentStage.show();
    }

}
