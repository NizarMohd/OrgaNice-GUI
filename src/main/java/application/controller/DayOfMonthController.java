package application.controller;

import application.comparator.TaskComparator;
import application.task.Deadline;
import application.task.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Pair;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class DayOfMonthController implements Initializable {

    public static final String EVENT_SYMBOL = "[E] ";
    public static final String DEADLINE_SYMBOL = "[D] ";
    public static final String EMPTY_STRING = "";
    public static final int NULL_INT = 0;

    @FXML
    public Label date;

    @FXML
    public Label taskTwo;

    @FXML
    public Label taskThree;

    @FXML
    public Label taskFour;

    @FXML
    public Label taskOne;


    public void setTaskTwo(String taskTwo) {
        this.taskTwo.setText(taskTwo);
    }

    public void setTaskThree(String taskThree) {
        this.taskThree.setText(taskThree);
    }

    public void setTaskFour(String taskFour) {
        this.taskFour.setText(taskFour);
    }

    public void setTaskOne(String taskOne) {
        this.taskOne.setText(taskOne);
    }

    public void setDay(String date) {
        this.date.setText(date);
    }

    public void setTask(LinkedList<Pair<String, Integer>> pairs) {
        int size = 0;
        size = Math.min(pairs.size(), 4);

        for( int i =0; i < size ; i ++) {
            if (!pairs.get(i).getKey().isEmpty()) {
                switch (i) {
                    case 0:
                        setTaskOne(pairs.get(i).getKey());
                        break;
                    case 1:
                        setTaskTwo(pairs.get(i).getKey());
                        break;
                    case 2:
                        setTaskThree(pairs.get(i).getKey());
                        break;
                    case 3:
                        setTaskFour(pairs.get(i).getKey());
                        break;
                    default:
                        break;
                }
            }
        }

        for( int i = size ; i < 4 ; i++) {
            switch(i) {
                case 0:
                    setTaskOne(EMPTY_STRING);
                    break;
                case 1:
                    setTaskTwo(EMPTY_STRING);
                    break;
                case 2:
                    setTaskThree(EMPTY_STRING);
                    break;
                case 3:
                    setTaskFour(EMPTY_STRING);
                    break;
                default:
                    break;
            }
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int day = CalendarViewController.daysOfMonths[CalendarViewController.dayOfMonthIndex];
        LinkedList<Pair<String, Integer>> pairs = new LinkedList<>();

        if (day == NULL_INT) {
            setDay(EMPTY_STRING);
        } else {
            LocalDate localDate = LocalDate.of(CalendarViewDialogController.year, CalendarViewDialogController.month, day);
            LinkedList<Event> events = EventTableController.eventHashMap.get(localDate);
            LinkedList<Deadline> deadlines = DeadlineTableController.deadlineHashMap.get(localDate);
            setDay(Integer.toString(day));
            if (events != null) {
                for (Event event : events) {
                    Pair<String, Integer> pair = new Pair<>(EVENT_SYMBOL + event.getDetailsID(), Integer.decode(event.getPriorityID()));
                    pairs.add(pair);
                }
            }
            if (deadlines != null) {
                for (Deadline deadline : deadlines) {
                    Pair<String, Integer> pair = new Pair<>(DEADLINE_SYMBOL + deadline.getDetailsID(), Integer.decode(deadline.getPriorityID()));
                    pairs.add(pair);
                }
            }
        }

        if (!pairs.isEmpty()) {
            pairs.sort(new TaskComparator());
        }
        setTask(pairs);
    }
}
