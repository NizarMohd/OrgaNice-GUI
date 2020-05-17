package application.controller;

import application.task.Deadline;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeadlineTableController implements Initializable {

    public static final String DELETE_COMMAND = "DeleteCommand.fxml";
    @FXML
    private MenuItem delete;
   @FXML
    private MenuItem edit;
    @FXML
    private TableView<Deadline> deadlineTable;
    @FXML
    private TableColumn<Deadline, String> deadlineID;
    @FXML
    private TableColumn<Deadline, String> deadlineStatusID;
    @FXML
    private TableColumn<Deadline, String> deadlineDetailsID;
    @FXML
    private TableColumn<Deadline, String> deadlineDateID;
    @FXML
    private TableColumn<Deadline, String> deadlineDueTimeID;
    @FXML
    private TableColumn<Deadline, String> deadlinePriorityID;

    private static ObservableList<Deadline> deadlineObservableList = FXCollections.observableArrayList();
    static HashMap<LocalDate, LinkedList<Deadline>> deadlineHashMap = new HashMap<>();
    static boolean isAdd = false;
    static Deadline bufferDeadline;
    private static final String DEADLINE = "deadline";

    public static ObservableList<Deadline> getDeadlineObservableList() {
        return deadlineObservableList;
    }

    public static void setBufferDeadline(Deadline deadline) {
        bufferDeadline = deadline;
        isAdd = true;
    }

    private void setDeadlineCellValues() {
        deadlineID.setCellValueFactory(new PropertyValueFactory<Deadline, String>("deadlineID"));
        deadlineDetailsID.setCellValueFactory(new PropertyValueFactory<Deadline,String>("detailsID"));
        deadlineDateID.setCellValueFactory(new PropertyValueFactory<Deadline, String>("dateID"));
        deadlineDueTimeID.setCellValueFactory(new PropertyValueFactory<Deadline, String>("timeID"));
        deadlinePriorityID.setCellValueFactory(new PropertyValueFactory<Deadline, String>("priorityID"));
        deadlineStatusID.setCellValueFactory(new PropertyValueFactory<Deadline, String>("statusID"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(isAdd) {
            deadlineObservableList.add(bufferDeadline);
            storeToMap(bufferDeadline);
            isAdd = false;
        }

        setDeadlineCellValues();
        FilteredList<Deadline> deadlineFilteredList = new FilteredList<>(deadlineObservableList, b -> true);
        TaskManagerController.currentSearchField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            deadlineFilteredList.setPredicate(deadline -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String filter = newValue.toLowerCase();
                return deadline.getDetailsID().toLowerCase().contains(filter);
            });
        }));
        deadlineTable.setItems(deadlineFilteredList);
    }

    public void delete(ActionEvent actionEvent) {
        DeleteCommandController.stage = new Stage();
        try {
            DeleteCommandController.setTaskType(DEADLINE);
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(DeleteCommandController.class.getClassLoader().getResource(DELETE_COMMAND)));
            Scene scene = new Scene(anchorPane);
            DeleteCommandController.stage.setScene(scene);
            DeleteCommandController.stage.show();
        } catch (IOException e) {
            PopUpMessageController.createExceptionMessage(e);
        }
    }


    public void edit(ActionEvent actionEvent) {
        EditCommandController.setTaskType(DEADLINE);
        EditCommandController.setStage();
    }

    public static void storeToMap(Deadline deadline) {
        LocalDate localDate = LocalDate.parse(deadline.getDateID(), DateTimeFormatter.ISO_LOCAL_DATE);
        deadlineHashMap.computeIfAbsent(localDate, k -> new LinkedList<Deadline>());
        deadlineHashMap.get(localDate).add(deadline);
    }

    public static void recount() {
        ObservableList<Deadline> deadlines = application.controller.DeadlineTableController.getDeadlineObservableList();
        for(int i = 0; i < deadlines.size() ; i++) {
            int count = i;
            count++;
            deadlines.get(i).setDeadlineID(Integer.toString(count));
        }
    }
}


