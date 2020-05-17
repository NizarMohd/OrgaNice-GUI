package application.validator;

import application.exception.GUIException;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static application.controller.AddCommandController.DEADLINES;
import static application.controller.AddCommandController.EVENTS;

public class TaskManagerInputValidator {
    public static final String NULL_INPUT = "No input given";
    public static final String DELIMITER = "~";
    public static final String REFRAIN_FROM_USING_DELIMITER = "Details Input Error/Refrain from using Delimiter";
    public static final String START_TIME_MUST_BE_BEFORE_END_TIME = "Start Time Input Error/Start time must be before end time";
    public static final String PRIORITY_CANNOT_BE_NEGATIVE = "Priority Input Error/Priority cannot be negative!";
    public static final String PRIORITY_IS_NOT_AN_INTEGER = "Priority Input Error/Priority is not an integer";
    public static final String DATE_INPUT_ERROR = "Date Input Error/Ensure due date is not before current time and date";
    public static boolean isNull = false;

    public static void setValidator(RequiredFieldValidator validator, JFXTextField textField) {
        textField.getValidators().add(validator);
        validator.setMessage(NULL_INPUT);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue,
                                Boolean newValue) {
                if (!newValue) {
                    textField.validate();
                }
            }});
    }

    public static String[] extractEventsStrings(JFXTextField eventDetails, JFXTextField eventPriority,
                                          JFXTimePicker startTime, JFXTimePicker endTime, DatePicker eventDate)
            throws GUIException {

        if(isNull) {
            throw new GUIException(EVENTS);
        }
        String details = eventDetails.getText();
        String start = startTime.getValue().toString();
        String end = endTime.getValue().toString();
        String date = eventDate.getValue().toString();
        String priority = eventPriority.getText();
        return new String[]{details, date, start, end, priority};
    }

    public static void checkNullInput(String[] input) {
        boolean isBlank = false;
        for (String s : input) {
            isBlank = isBlank || s.isBlank();
        }
        isNull &= !isBlank;
    }

    public static void checkEventsTimeInput(JFXTimePicker startTime, JFXTimePicker endTime, DatePicker eventDate)
            throws GUIException {
        if(endTime.getValue() == null || eventDate.getValue() == null || startTime.getValue() == null ) {
            isNull = true;
            return;
        }
        checkTimeOrder(Objects.requireNonNull(startTime.getValue()), endTime.getValue());
    }

    public static void validateEventsInput(JFXTextField eventDetails, JFXTextField eventPriority,
                                           JFXTimePicker startTime, JFXTimePicker endTime, DatePicker eventDate)
            throws GUIException {

        isNull = false;
        checkEventsTimeInput(startTime, endTime, eventDate);
        String[] input = extractEventsStrings(eventDetails, eventPriority,startTime,endTime,eventDate);
        checkNullInput(input);
        checkDetails(input[0]);
        checkPriority(input[4]);
    }

    public static void checkDetails(String input) throws GUIException {
        if(!isNull) {
            if (input.contains(DELIMITER)) {
                throw new GUIException(REFRAIN_FROM_USING_DELIMITER);
            }
        }
    }

    public static void checkTimeOrder(LocalTime start, LocalTime end) throws GUIException {
        if (start.isAfter(end)) {
            throw new GUIException(START_TIME_MUST_BE_BEFORE_END_TIME);
        }
    }

    public static void checkPositiveInteger(Integer priority) throws GUIException {
        if(priority <= 0) {
            throw new GUIException(PRIORITY_CANNOT_BE_NEGATIVE);
        }
    }

    public static void checkPriority(String input) throws GUIException {
        try {
            Integer priority = Integer.parseInt(input);
            checkPositiveInteger(priority);
        } catch (NumberFormatException e) {
            throw new GUIException(PRIORITY_IS_NOT_AN_INTEGER);
        }

    }


    public static void checkDeadlineTimeInput(DatePicker dueDate, JFXTimePicker dueTime) {
        if(dueDate.getValue() == null || dueTime.getValue() == null ) {
            isNull = true;
        }
    }

    public static String[] extractDeadlineStrings(JFXTextField deadlineDetails, JFXTextField priorityTxt,
                                          JFXTimePicker dueTime, DatePicker dueDate) throws GUIException {
        if(isNull) {
            throw new GUIException(DEADLINES);
        }
        String details = deadlineDetails.getText();
        String priority = priorityTxt.getText();
        String date = dueDate.getValue().toString();
        String time = dueTime.getValue().toString();
        return new String[]{details, date, time, priority};
    }

    public static void checkDateOrder(DatePicker dueDate) throws GUIException {
        if(dueDate.getValue().isBefore(LocalDate.now())) {
            throw new GUIException(DATE_INPUT_ERROR);
        }
    }

    public static void validateDeadlineInput(JFXTextField details, DatePicker dueDate, JFXTimePicker dueTime,
                                             JFXTextField priorityTxt) throws GUIException {
        isNull = false;
        checkDeadlineTimeInput(dueDate, dueTime);
        String[] input = extractDeadlineStrings(details,priorityTxt, dueTime, dueDate);
        checkNullInput(input);
        checkDateOrder(dueDate);
        checkDetails(input[0]);
        checkPriority(input[3]);
    }
}
