package application.controller;

import application.storage.Storage;
import application.task.Deadline;
import application.validator.TaskManagerInputValidator;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EditDeadlineDialogController implements Initializable {

    public static final String DEADLINE_EDIT_SUCCESS = "Deadline Edit Success!/Successfully edited deadline information!";
    public static final String EDIT_STATUS = "Edit Status";
    public static final String FALSE = "false";
    public static final String TRUE = "true";
    @FXML
    public Label labelDetails;

    @FXML
    public Label labelDate;

    @FXML
    public Label labelDueTime;

    @FXML
    public Label labelStatus;

    @FXML
    public Label labelPriority;

    @FXML
    public JFXDatePicker newDate;

    @FXML
    public JFXTimePicker newTime;

    @FXML
    public JFXTextField newPriority;

    @FXML
    public JFXTextField newDetails;

    @FXML
    public JFXTextField deadlineID;

    @FXML
    public Button done;

    @FXML
    public ChoiceBox<String> newStatus;

    private void getDetails(Deadline deadline) {

        if(!newStatus.getValue().equals(EDIT_STATUS)){
            deadline.setStatusID(newStatus.getValue());
        }

        try {
            if(!newDetails.getText().isEmpty()) {
                TaskManagerInputValidator.checkDetails(newDetails.getText());
                deadline.setDetailsID(newDetails.getText());
            }
            if(!newPriority.getText().isEmpty()){
                TaskManagerInputValidator.checkPriority(newPriority.getText());
                deadline.setPriorityID(newPriority.getText());
            }
            if (newDate.getValue() != null) {
                TaskManagerInputValidator.checkDateOrder(newDate);
                deadline.setDateID(newDate.getValue().toString());
            }
            if (newTime.getValue() != null) {
                deadline.setTimeID(newTime.getValue().toString());
            }

            PopUpMessageController.createSuccessMessage(DEADLINE_EDIT_SUCCESS);
            Storage.saveDeadlines();
            EditCommandController.stage.close();
        } catch (Exception e) {
            PopUpMessageController.createErrorMessage(e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int index = EditCommandController.getIndex();
        index++;
        deadlineID.setText(Integer.toString(index));
        index--;
        Deadline deadline = DeadlineTableController.getDeadlineObservableList().get(index);
        labelDetails.setText(deadline.getDetailsID());
        labelDate.setText(deadline.getDateID());
        labelDueTime.setText(deadline.getTimeID());
        labelStatus.setText(deadline.getStatusID());
        labelPriority.setText(deadline.getPriorityID());
        newStatus.getItems().addAll(TRUE, FALSE);
        newStatus.setValue(EDIT_STATUS);
        done.setOnAction( e-> getDetails(deadline));
    }
}

