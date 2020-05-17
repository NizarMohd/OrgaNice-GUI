package application.controller;

import application.storage.Storage;
import application.task.Deadline;
import application.task.Event;
import application.validator.TaskManagerInputValidator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddCommandController implements Initializable {

    public static final String EVENTS = "Please ensure that all fields for events are entered";
    public static final String DEADLINES = "Please ensure that all fields for deadlines";
    public static final int DETAILS = 0;
    public static final int DATE = 1;
    public static final int TIME = 2;
    public static final int START = 2;
    public static final int END = 3;
    public static final int PRIORITY = 4;
    public static final int DEADLINE_PRIORITY = 3;
    public static final int DEADLINE_STATUS = 4;
    public static final String EVENT_TABLE = "EventTable.fxml";
    public static final String EVENT_SUCCESS = "Task creation success!/Successfully added a new Event!";
    public static final String DEADLINE_SUCCESS = "Task creation success!/Successfully added a new Deadline!";
    public static final String DEADLINE_TABLE = "DeadlineTable.fxml";
    public static final String ADD_COMMAND = "AddCommand.fxml";

    @FXML
    private DatePicker eventDate;

    @FXML
    private JFXTimePicker startTime;

    @FXML
    private JFXTimePicker endTime;

    @FXML
    private JFXTextField eventDetails;

    @FXML
    private JFXButton addEvent;

    @FXML
    private JFXButton addDeadline;

    @FXML
    private JFXTimePicker dueTime;

    @FXML
    private DatePicker dueDate;

    @FXML
    private JFXTextField deadlineDetails;

    @FXML
    private JFXTextField priorityTxt;

    @FXML
    private JFXTextField eventPriority;

    static Stage currentStage;


    public static void setAddCommandStage() {
        currentStage = new Stage();
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(Objects.requireNonNull(application.controller.AddCommandController.class.getClassLoader().getResource(ADD_COMMAND)));
            Scene scene = new Scene(anchorPane);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            PopUpMessageController.createExceptionMessage(e);
        }
    }


    @FXML
    void addDeadlineTask(ActionEvent event) {

        try {
            TaskManagerInputValidator.validateDeadlineInput(deadlineDetails, dueDate, dueTime, priorityTxt);
            String[] input = TaskManagerInputValidator.extractDeadlineStrings(deadlineDetails,priorityTxt,dueTime, dueDate);
            int count = DeadlineTableController.getDeadlineObservableList().size() + 1;
            Deadline deadline = new Deadline( count ,input[DETAILS], input[DATE], input[TIME], input[DEADLINE_PRIORITY]);
            DeadlineTableController.setBufferDeadline(deadline);
            FXMLLoader.load(Objects.requireNonNull(DeadlineTableController.class.getClassLoader().getResource(DEADLINE_TABLE)));
            PopUpMessageController.createSuccessMessage(DEADLINE_SUCCESS);
            Storage.saveDeadlines();
            currentStage.close();
        } catch (Exception e) {
            PopUpMessageController.createExceptionMessage(e);
        }

    }

    @FXML
    void addEventTask(ActionEvent actionEvent) {

        try {
            TaskManagerInputValidator.validateEventsInput(eventDetails, eventPriority, startTime, endTime, eventDate);
            String[] input = TaskManagerInputValidator.extractEventsStrings(eventDetails, eventPriority, startTime,
                    endTime, eventDate);
            int count = EventTableController.getEventObservableList().size() + 1;
            Event event = new Event( count, input[DETAILS], input[DATE], input[START], input[END], input[PRIORITY]);
            EventTableController.setBufferEvent(event);
            FXMLLoader.load(Objects.requireNonNull(TaskManagerController.class.getClassLoader().getResource(EVENT_TABLE)));
            PopUpMessageController.createSuccessMessage(EVENT_SUCCESS);
            Storage.saveEvents();
            currentStage.close();
        } catch (Exception e) {
            PopUpMessageController.createExceptionMessage(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       JFXTextField[] textFields = {eventDetails, deadlineDetails, priorityTxt, eventPriority};
        RequiredFieldValidator validator = new RequiredFieldValidator();
        for(JFXTextField textField : textFields){
            TaskManagerInputValidator.setValidator(validator,textField);
        }
    }
}
