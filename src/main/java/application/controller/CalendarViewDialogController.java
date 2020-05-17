package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;
import java.util.ResourceBundle;

public class CalendarViewDialogController implements Initializable {

    public static final int JAN = 1;
    public static final int DEC = 12;
    public static final int NULL_OFFSET = 0;
    public static final int DECADE_OFFSET = 10;
    public static final String CALENDAR_VIEW = "CalendarViewDialog.fxml";
    public static final String INPUT_REQUEST = "Please enter the desired month and year value";
    static boolean isYearChanged = false;
    @FXML
    private Label label;

    @FXML
    private Button done;

    @FXML
    private ChoiceBox<String> monthChoice;

    @FXML
    private ChoiceBox<String> yearChoice;
    static Month month;
    static int year;
    private static Stage currentStage;

    static void setCalendarDialog() {
        try{
            currentStage = new Stage();
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(CalendarViewController.class.getClassLoader().getResource(CALENDAR_VIEW)));
            Scene scene = new Scene(anchorPane);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (Exception e) {
            PopUpMessageController.createExceptionMessage(e);
        }
    }

    void setMonth() {
        month = Month.valueOf(monthChoice.getValue());
        year = Integer.parseInt(yearChoice.getValue());
        CalendarViewController.processDates();
        CalendarViewController.showCalendar();
        currentStage.close();
    }
    void checkValue() {
        int choiceOfYear = Integer.parseInt(yearChoice.getValue());
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonth().getValue();
        if(choiceOfYear > currentYear){
            isYearChanged = true;
            monthChoice.getItems().clear();
            for(int i = JAN; i <= DEC; i++) {
                Month month = Month.of(i);
                monthChoice.getItems().add(month.toString());
            }
            monthChoice.setValue(Month.of(JAN).toString());
        }
        if(isYearChanged && choiceOfYear == currentYear) {
            isYearChanged = false;
            monthChoice.getItems().clear();
            for(int i = currentMonth ; i <= DEC; i++) {
                Month month = Month.of(i);
                monthChoice.getItems().add(month.toString());
            }
            monthChoice.setValue(Month.of(currentMonth).toString());
        }
    }

    void setChoice() {
        Month currentMonth = LocalDate.now().getMonth();
        int currentYear = LocalDate.now().getYear();

        for( int i = currentMonth.getValue() ; i <= DEC ; i++) {
            Month month = Month.of(i);
            monthChoice.getItems().add(month.toString());
        }

        for (int i = NULL_OFFSET; i <= DECADE_OFFSET; i++) {
            yearChoice.getItems().add(Integer.toString(currentYear + i));
        }

        monthChoice.setValue(currentMonth.toString());
        yearChoice.setValue(Integer.toString(currentYear));
        yearChoice.setOnAction( e -> checkValue());
        done.setOnAction(e -> setMonth());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            label.setText(INPUT_REQUEST);
            setChoice();
    }
}
