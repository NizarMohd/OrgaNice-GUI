package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DayOfWeekController implements Initializable {

    @FXML
    private Label day;
    private static String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        day.setText(days[CalendarViewController.dayOfWeekIndex]);
    }
}
