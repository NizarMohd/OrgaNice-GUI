package application.controller;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SidePanelController {
    public static final String TASK_MANAGER = "TaskManager.fxml";
    static AnchorPane taskManagerAnchorPane = new AnchorPane();
    static Stage taskManagerStage;
    @FXML
    private VBox Vbox;

    @FXML
    private Button taskManagerBtn;

    @FXML
    private Button calViewBtn;

    @FXML
    private Button exitBtn;

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void startTask(ActionEvent event)  {
        closeDrawer();
        try {
            Stage stage = new Stage();
            taskManagerAnchorPane = FXMLLoader.load(Objects.requireNonNull(TaskManagerController.class.getClassLoader().getResource(TASK_MANAGER)));
            Scene scene = new Scene(taskManagerAnchorPane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            PopUpMessageController.createExceptionMessage(e);
        }
    }

    public void closeDrawer() {
        NavigationDrawerController.getDrawer().close();
        HamburgerBackArrowBasicTransition transition = NavigationDrawerController.getTransition();
        transition.setRate(transition.getRate() * -1);
        transition.play();
    }

    public void startCalendar(ActionEvent actionEvent) {
        closeDrawer();
        CalendarViewDialogController.setCalendarDialog();
    }
}
