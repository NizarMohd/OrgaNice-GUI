package application.controller;

import application.storage.Storage;
import application.task.Event;
import application.validator.TaskManagerInputValidator;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class EditEventsDialogController implements Initializable {
    public static final String EVENT_EDIT_SUCCESS = "Event Edit Success!/Successfully edited event information!";
    @FXML
    private Label labelDetails;

    @FXML
    private Label labelDate;

    @FXML
    private Label labelStart;

    @FXML
    private Label labelEnd;

    @FXML
    private Label labelPriority;

    @FXML
    private JFXDatePicker newDate;

    @FXML
    private JFXTimePicker newStart;

    @FXML
    private JFXTimePicker newEnd;

    @FXML
    private JFXTextField newPriority;

    @FXML
    private JFXTextField newDetails;

    @FXML
    private JFXTextField eventID;

    @FXML
    private Button done;

    private void getDetails(Event event) {

        try {
            if(!newDetails.getText().isBlank()) {
                TaskManagerInputValidator.checkDetails(newDetails.getText());
                event.setDetailsID(newDetails.getText());
            }
            if(!newPriority.getText().isBlank()){
                TaskManagerInputValidator.checkPriority(newPriority.getText());
                event.setPriorityID(newPriority.getText());
            }
            if (newDate.getValue() != null) {
                TaskManagerInputValidator.checkDateOrder(newDate);
                event.setDateID(newDate.getValue().toString());
            }
            if (newStart.getValue() != null) {
                TaskManagerInputValidator.checkTimeOrder(newStart.getValue(), LocalTime.parse(event.getEndID()));
                event.setStartID(newStart.getValue().toString());
            }
            if (newEnd.getValue() != null) {
                TaskManagerInputValidator.checkTimeOrder(LocalTime.parse(event.getStartID()), newEnd.getValue());
                event.setEndID(newEnd.getValue().toString());
            }

            Storage.saveEvents();
            PopUpMessageController.createSuccessMessage(EVENT_EDIT_SUCCESS);

            EditCommandController.stage.close();
        } catch (Exception e) {
            PopUpMessageController.createErrorMessage(e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int index = EditCommandController.getIndex();
        index++;
        eventID.setText(Integer.toString(index));
        index--;
        Event event = EventTableController.getEventObservableList().get(index);
        labelDetails.setText(event.getDetailsID());
        labelDate.setText(event.getDateID());
        labelStart.setText(event.getStartID());
        labelEnd.setText(event.getEndID());
        labelPriority.setText(event.getPriorityID());
        done.setOnAction( e-> getDetails(event));
    }
}
