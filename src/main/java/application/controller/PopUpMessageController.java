package application.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

public class PopUpMessageController {


    public static final String WARNING_DIALOG = "Warning Dialog";
    public static final String EMPTY_INPUTS = "Empty inputs";
    public static final String EXCEPTION_DIALOG = "Exception Dialog";
    public static final String INPUT_ERROR = "Input Error";
    public static final String STACKTRACE_DETAIL = "The exception stacktrace was:";
    public static final String SUCCESS = "Success!";
    public static final String DELIMITER = "/";
    public static final int HEADER = 0;
    public static final int MESSAGE = 1;

    public static void createErrorMessage(String message) {
        String[] text = message.split(DELIMITER);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(WARNING_DIALOG);
        alert.setHeaderText(text[HEADER]);
        alert.setContentText(text[MESSAGE]);
        alert.showAndWait();
    }

    public static void createSuccessMessage(String message){
        String[] text = message.split(DELIMITER);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS);
        alert.setHeaderText(text[HEADER]);
        alert.setContentText(text[MESSAGE]);
        alert.showAndWait();
    }

    public static void createExceptionMessage(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(EXCEPTION_DIALOG);
        alert.setHeaderText(INPUT_ERROR);
        alert.setContentText(e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label(STACKTRACE_DETAIL);

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}


