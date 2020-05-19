package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;
import java.util.ResourceBundle;

public class CalendarViewController implements Initializable {


    public static final String CALENDAR_VIEW = "CalendarView.fxml";
    public static final String DAY_OF_WEEK = "DayOfWeek.fxml";
    public static final String DAY_OF_MONTH = "DayOfMonth.fxml";
    @FXML
    public GridPane gridPane;

    static int[] daysOfMonths;
    static int dayOfWeekIndex;
    static int dayOfMonthIndex;


    static void processDates() {
        LocalDate firstDay = YearMonth.of(CalendarViewDialogController.year , CalendarViewDialogController.month).atDay(1);
        int lastDay = firstDay.getMonth().length(firstDay.isLeapYear());
        int index = firstDay.getDayOfWeek().getValue();
        index = index % 7;
        daysOfMonths = new int[35];
        for (int i = 1; i <= lastDay; i++) {
            if (index >= 35) {
                index = 0;
            }
            daysOfMonths[index] = i;
            index++;
        }
    }

    static void showCalendar() {
        Stage stage = new Stage();
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(Objects.requireNonNull(application.controller.CalendarViewController.class.getClassLoader().getResource(CALENDAR_VIEW)));
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            PopUpMessageController.createExceptionMessage(e);
        }

    }

     void setDaysOfWeek() {
        for (int i =0; i < 7 ; i++ ) {
            try {
                dayOfWeekIndex = i;
                AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(DayOfWeekController.class.getClassLoader().getResource(DAY_OF_WEEK)));
                gridPane.add(anchorPane, i, 0);
            } catch (IOException e) {
                PopUpMessageController.createExceptionMessage(e);
            }
        }
    }

    void setDaysOfMonths() {

        for( int i = 0; i < 5 ; i++) {
            for (int j = 0; j < 7 ; j ++){
                try {
                    dayOfMonthIndex = i*7 + j;
                    AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(DayOfMonthController.class.getClassLoader().getResource(DAY_OF_MONTH)));
                    int gridPaneRow = i + 1;
                    gridPane.add(anchorPane, j, gridPaneRow);
                } catch (IOException e) {
                    PopUpMessageController.createExceptionMessage(e);
                }
            }
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            gridPane.setGridLinesVisible(true);
            setDaysOfWeek();
            setDaysOfMonths();
    }
}
