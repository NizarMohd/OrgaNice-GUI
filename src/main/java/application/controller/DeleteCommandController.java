package application.controller;

import application.storage.Storage;
import application.task.Deadline;
import application.task.Event;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteCommandController implements Initializable {

    public static final String EMPTY_ID = " ID not entered/Please enter the index number of the task that you wish to delete";
    public static final String EVENT_TYPE = "Event";
    public static final String DEADLINE_TYPE = "Deadline";
    public static final String DEADLINE_INPUT_REQUEST = "Please enter the index of the deadline task that you wish to delete";
    public static final String EVENT_INPUT_REQUEST = "Please enter the index of the event task that you wish to delete";
    public static final String INDEX_INPUT_REQUEST = "Enter index";
    @FXML
    public Label label;
    @FXML
    public Button delete;
    @FXML
    public ChoiceBox<String> choiceBox;
    private static final String DEADLINE = "deadline";
    private static final String EVENTS = "event";
    private static String taskType;
    private static int index;
    static Stage stage;

    public void deleteTask() {
        index--;
        if(taskType.equals(EVENTS)) {
            EventTableController.getEventObservableList().remove(index);
            index++;
            PopUpMessageController.createSuccessMessage("Task deletion success/Successfully deleted Event with ID: " + index);
            EventTableController.recount();
            Storage.saveEvents();
        } else if(taskType.equals(DEADLINE)) {
            DeadlineTableController.getDeadlineObservableList().remove(index);
            index++;
            PopUpMessageController.createSuccessMessage("Task deletion success/Successfully deleted Deadline with ID: " + index);
            DeadlineTableController.recount();
            Storage.saveDeadlines();
        }
        application.controller.DeleteCommandController.stage.close();
    }

    private void getChoice(ChoiceBox<String> choiceBox) {
        String choice = choiceBox.getValue();
        try {
            index = Integer.parseInt(choice);
            deleteTask();
        } catch (NumberFormatException e) {
            String type = taskType.equals(EVENTS) ? EVENT_TYPE : DEADLINE_TYPE;
            PopUpMessageController.createErrorMessage( type + EMPTY_ID);
        }
    }

    public static void setTaskType(String type) {
        taskType = type;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().clear();
        if(taskType.equals(DEADLINE)) {
            label.setText(DEADLINE_INPUT_REQUEST);
            ObservableList<Deadline> deadlines = DeadlineTableController.getDeadlineObservableList();
            int count = 0;
            for ( Deadline deadline : deadlines) {
                count++;
                choiceBox.getItems().add(Integer.toString(count));
            }
        } else if(taskType.equals(EVENTS)) {
            label.setText(EVENT_INPUT_REQUEST);
            ObservableList<Event> events = EventTableController.getEventObservableList();
            int count = 0;
            for (Event event : events) {
                count++;
                choiceBox.getItems().add(Integer.toString(count));
            }
        }
        choiceBox.setValue(INDEX_INPUT_REQUEST);
        delete.setOnAction( e -> getChoice(choiceBox));
    }
}
