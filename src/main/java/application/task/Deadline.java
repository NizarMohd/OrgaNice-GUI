package application.task;

import javafx.beans.property.SimpleStringProperty;

import static application.storage.Storage.DELIMITER;

public class Deadline extends Task {

     private  SimpleStringProperty deadlineID;
     private  SimpleStringProperty detailsID;
     private  SimpleStringProperty dateID;
     private  SimpleStringProperty timeID;
     private  SimpleStringProperty priorityID;
     private SimpleStringProperty statusID;
     private static Integer loadCount = 0;

    public Deadline(int count, String details, String date, String time, String priority) {
        this.deadlineID = new SimpleStringProperty(Integer.toString(count));
        this.detailsID = new SimpleStringProperty(details);
        this.dateID = new SimpleStringProperty(date);
        this.timeID = new SimpleStringProperty(time);
        this.priorityID = new SimpleStringProperty(priority);
        this.statusID = new SimpleStringProperty(Boolean.toString(false));
    }

    public Deadline(String details, String date, String time, String priority, String status) {
        loadCount++ ;
        this.deadlineID = new SimpleStringProperty(loadCount.toString());
        this.detailsID = new SimpleStringProperty(details);
        this.dateID = new SimpleStringProperty(date);
        this.timeID = new SimpleStringProperty(time);
        this.priorityID = new SimpleStringProperty(priority);
        this.statusID = new SimpleStringProperty(status);
    }

    public void setStatus() {
        this.statusID = new SimpleStringProperty(Boolean.toString(true));
    }

    public String getPriorityID() {
        return priorityID.get();
    }


    public SimpleStringProperty priorityIDProperty() {
        return priorityID;
    }

    public String getTimeID() {
        return timeID.get();
    }

    public SimpleStringProperty timeIDProperty() {
        return timeID;
    }

    public String getDateID() {
        return dateID.get();
    }

    public SimpleStringProperty dateIDProperty() {
        return dateID;
    }

    public String getDetailsID() {
        return detailsID.get();
    }

    public SimpleStringProperty detailsIDProperty() {
        return detailsID;
    }

    public String getDeadlineID() {
        return deadlineID.get();
    }

    public SimpleStringProperty deadlineIDProperty() {
        return deadlineID;
    }

    public String getStatusID() {
        return statusID.get();
    }

    public SimpleStringProperty statusIDProperty() {
        return statusID;
    }

    public void setDeadlineID(String deadlineID) {
        this.deadlineID.set(deadlineID);
    }

    public void setDetailsID(String detailsID) {
        this.detailsID.set(detailsID);
    }

    public void setDateID(String dateID) {
        this.dateID.set(dateID);
    }

    public void setTimeID(String timeID) {
        this.timeID.set(timeID);
    }

    public void setPriorityID(String priorityID) {
        this.priorityID.set(priorityID);
    }

    public void setStatusID(String statusID) {
        this.statusID.set(statusID);
    }

    public String toFile() {
        String out = this.getDetailsID() + DELIMITER + this.getDateID() + DELIMITER + this.getTimeID() + DELIMITER +
                this.getPriorityID() + DELIMITER + this.getStatusID() + System.lineSeparator();
        return out;
    }


}
