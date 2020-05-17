package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TaskManagerController implements Initializable {

    public static final int EVENT = 1;
    public static final int DEADLINE = 2;
    public static final int FALSE = 0;
    public static final String EVENT_TABLE = "EventTable.fxml";
    public static final String DEADLINE_TABLE = "DeadlineTable.fxml";
    static boolean isLoaded = false;
    public TextField searchField;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button add;
    static TextField currentSearchField;


    @FXML
    void addTask(ActionEvent event) {
        AddCommandController.setAddCommandStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                currentSearchField = searchField;
                AnchorPane eventPane = FXMLLoader.load(Objects.requireNonNull(EventTableController.class.getClassLoader().getResource(EVENT_TABLE)));
                AnchorPane deadlinePane = FXMLLoader.load(Objects.requireNonNull(DeadlineTableController.class.getClassLoader().getResource(DEADLINE_TABLE)));
                splitPane.getItems().set(0, eventPane);
                splitPane.getItems().set(1, deadlinePane);
                isLoaded = true;
            } catch (IOException e) {
                PopUpMessageController.createExceptionMessage(e);
            }
    }
}