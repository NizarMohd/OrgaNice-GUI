package application.storage;

import application.controller.DeadlineTableController;
import application.controller.EventTableController;
import application.controller.PopUpMessageController;
import application.task.Deadline;
import application.task.Event;
import application.task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static application.controller.AddCommandController.*;


public class Storage {

    public static final String DELIMITER = "~";
    public static final String FILEPATH_EVENTS = "data" + File.separator + "events.txt";
    public static final String FILEPATH_DEADLINE = "data" +File.separator + "deadline.txt";

    public static void saveEvents() {
        try {
            ObservableList<Event> tasks = EventTableController.getEventObservableList();
            FileWriter buffer = new FileWriter(FILEPATH_EVENTS);
            for (Task task: tasks) {
                buffer.write(task.toFile());
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDeadlines() {
        try {
            ObservableList<Deadline> tasks = DeadlineTableController.getDeadlineObservableList();
            FileWriter buffer = new FileWriter(FILEPATH_DEADLINE);
            for (Task task: tasks) {
                buffer.write(task.toFile());
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Event parseEventDetails(String detail) {
        String[] input = detail.split(DELIMITER);
        Event event = new Event(input[DETAILS], input[DATE], input[START], input[END], input[PRIORITY]);
        return event;
    }

    public static Deadline parseDeadlineDetails(String detail) {
        String[] input = detail.split(DELIMITER);
        Deadline deadline = new Deadline(input[DETAILS], input[DATE], input[TIME], input[DEADLINE_PRIORITY],
                input[DEADLINE_STATUS]);
        return deadline;
    }

    public static ObservableList<Event> loadEvents() {
            ObservableList<Event> tasks = FXCollections.observableArrayList();
            File dataFile;
            Scanner s;
            try {
                dataFile = new File(FILEPATH_EVENTS);
                s = new Scanner(dataFile);
                while (s.hasNext()) {
                    String details = s.nextLine();
                    Event event = parseEventDetails(details);
                    LocalDate date = LocalDate.parse(event.getDateID(), DateTimeFormatter.ISO_LOCAL_DATE);
                    if(date.isAfter(LocalDate.now())) {
                        EventTableController.storeToMap(event);
                        tasks.add(event);
                    }
                }
            }  catch (FileNotFoundException e) {
                createNewDataFile(FILEPATH_EVENTS);
            }
            return tasks;
    }

    public static ObservableList<Deadline> loadDeadline() {
        ObservableList<Deadline> tasks = FXCollections.observableArrayList();
        File dataFile;
        Scanner s;
        try {
            dataFile = new File(FILEPATH_DEADLINE);
            s = new Scanner(dataFile);
            while (s.hasNext()) {
                String details = s.nextLine();
                Deadline deadline = parseDeadlineDetails(details);
                LocalDate date = LocalDate.parse(deadline.getDateID() ,DateTimeFormatter.ISO_LOCAL_DATE);
                if(date.isAfter(LocalDate.now())) {
                    DeadlineTableController.storeToMap(deadline);
                    tasks.add(deadline);
                }
            }
        }  catch (FileNotFoundException e) {
            createNewDataFile(FILEPATH_DEADLINE);
        }
        return tasks;
    }

    private static void createNewDataFile(String filePath) {
        Path dir = Paths.get(filePath).getParent();
        try {
            Files.createDirectories(dir);
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            PopUpMessageController.createExceptionMessage(e);
        }
    }
}
