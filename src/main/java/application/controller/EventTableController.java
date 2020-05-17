package application.controller;

import application.task.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ResourceBundle;

public class EventTableController implements Initializable {

    private static final String EVENTS = "event";
    public static final String DELETE_COMMAND = "DeleteCommand.fxml";

    @FXML
    private MenuItem delete;
    @FXML
    private MenuItem edit;
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event, String> eventID;
    @FXML
    private TableColumn<Event, String> eventDetailsID;
    @FXML
    private TableColumn<Event, String> eventDateID;
    @FXML
    private TableColumn<Event, String> eventStartID;
    @FXML
    private TableColumn<Event, String> eventEndID;
    @FXML
    private TableColumn<Event, String> eventPriorityID;

    private static ObservableList<Event> eventObservableList = FXCollections.observableArrayList();
    static HashMap<LocalDate, LinkedList<Event>> eventHashMap = new HashMap<>();
    static boolean isAdd = false;
    static Event bufferEvent;


    private void setEventCellValues() {
        eventID.setCellValueFactory(new PropertyValueFactory<Event, String>("eventID"));
        eventDetailsID.setCellValueFactory(new PropertyValueFactory<Event,String>("detailsID"));
        eventDateID.setCellValueFactory(new PropertyValueFactory<Event, String>("dateID"));
        eventStartID.setCellValueFactory(new PropertyValueFactory<Event, String>("startID"));
        eventEndID.setCellValueFactory(new PropertyValueFactory<Event, String>("endID"));
        eventPriorityID.setCellValueFactory(new PropertyValueFactory<Event, String>("priorityID"));
    }

    public static ObservableList<Event> getEventObservableList() {
        return eventObservableList;
    }

    public static void setBufferEvent(Event event) {
        bufferEvent = event;
        isAdd = true;
    }

    public static void storeToMap(Event event) {
        LocalDate localDate = LocalDate.parse(event.getDateID(), DateTimeFormatter.ISO_LOCAL_DATE);
        eventHashMap.computeIfAbsent(localDate, k -> new LinkedList<Event>());
        eventHashMap.get(localDate).add(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(isAdd) {
            eventObservableList.add(bufferEvent);
            storeToMap(bufferEvent);
            isAdd = false;
        }
        setEventCellValues();

        FilteredList<Event> eventFilteredList = new FilteredList<>(eventObservableList, b -> true);
        TaskManagerController.currentSearchField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            eventFilteredList.setPredicate(event -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String filter = newValue.toLowerCase();
                return event.getDetailsID().toLowerCase().contains(filter);
            });
        }));
        eventTable.setItems(eventFilteredList);
    }

    public void delete(ActionEvent actionEvent) {
        DeleteCommandController.stage = new Stage();
        try {
            DeleteCommandController.setTaskType(EVENTS);
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(DeleteCommandController.class.getClassLoader().getResource(DELETE_COMMAND)));
            Scene scene = new Scene(anchorPane);
            DeleteCommandController.stage.setScene(scene);
            DeleteCommandController.stage.show();
        } catch (Exception e) {
            PopUpMessageController.createExceptionMessage(e);
        }
    }

    public void edit(ActionEvent actionEvent) {
        EditCommandController.setTaskType(EVENTS);
        EditCommandController.setStage();
    }

    public static void recount() {
        ObservableList<Event> events = application.controller.EventTableController.getEventObservableList();
        for(int i = 0; i < events.size() ; i++) {
            int count = i;
            count++;
            events.get(i).setEventID(Integer.toString(count));
        }
    }
}
