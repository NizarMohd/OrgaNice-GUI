package application.controller;


import application.task.Deadline;
import application.task.Event;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditCommandController implements Initializable {

    public static final String EDIT_COMMAND = "EditCommand.fxml";
    public static final String EDIT_EVENT_DIALOG = "EditEventsDialog.fxml";
    public static final String EMPTY_EVENT_ID = "Event ID not entered/Please enter the index number of the event that you wish to edit!";
    public static final String EMPTY_DEADLINE_ID = "Deadline ID not entered/Please enter the index number of the deadline that you wish to edit!";
    public static final String EDIT_DEADLINE_DIALOG = "EditDeadlineDialog.fxml";
    public static final String EVENT_INPUT_REQUEST = "Please Enter the ID of the Event that you wish to edit";
    public static final String DEADLINE_INPUT_REQUEST = "Please Enter the ID of the Deadline that you wish to edit";
    @FXML
    private SplitPane splitPlane;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button edit;

    @FXML
    private Label label;

    private static final String EVENT = "event";
    private static final String DEADLINE = "deadline";
    private static int index;
    static String taskType;
    static Stage stage;


    static void setStage() {
        stage = new Stage();
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(Objects.requireNonNull(application.controller.EditCommandController.class.getClassLoader().getResource(EDIT_COMMAND)));
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            PopUpMessageController.createExceptionMessage(e);
        }

    }

    static void setTaskType(String type) {
        taskType = type;
    }

    void getEventChoice() {
        String choice = choiceBox.getValue();
        try {
            index = Integer.parseInt(choice);
            index--;
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(EditEventsDialogController.class.getClassLoader().getResource(EDIT_EVENT_DIALOG)));
            splitPlane.getItems().set(1, anchorPane);
        } catch (NumberFormatException e) {
            PopUpMessageController.createErrorMessage(EMPTY_EVENT_ID);
        } catch (Exception e) {
            PopUpMessageController.createExceptionMessage(e);
        }
    }

    void getDeadlineChoice() {
        String choice = choiceBox.getValue();
        try {
            index = Integer.parseInt(choice);
            index--;
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull((EditDeadlineDialogController.class.getClassLoader().getResource(EDIT_DEADLINE_DIALOG))));
            splitPlane.getItems().set(1, anchorPane);
        } catch (NumberFormatException e) {
            PopUpMessageController.createErrorMessage(EMPTY_DEADLINE_ID);
        } catch (Exception e) {
            PopUpMessageController.createExceptionMessage(e);
        }
    }

    static int getIndex() {
        return index;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            choiceBox.getItems().clear();
            if(taskType.equals(EVENT)) {
                label.setText(EVENT_INPUT_REQUEST);
                ObservableList<Event> events = EventTableController.getEventObservableList();
                for(int i =0; i< events.size() ; i++) {
                    int index = i + 1;
                    choiceBox.getItems().add(Integer.toString(index));
                }
                edit.setOnAction( e -> getEventChoice());

            } else if (taskType.equals(DEADLINE)){
                label.setText(DEADLINE_INPUT_REQUEST);
                ObservableList<Deadline> deadlines = DeadlineTableController.getDeadlineObservableList();
                for(int i =0; i< deadlines.size() ; i++) {
                    int index = i + 1;
                    choiceBox.getItems().add(Integer.toString(index));
                }
                edit.setOnAction( e -> getDeadlineChoice());
            }
        } catch (Exception e) {
            PopUpMessageController.createExceptionMessage(e);
        }

    }
}

