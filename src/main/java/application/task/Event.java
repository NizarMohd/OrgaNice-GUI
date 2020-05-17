package application.task;

import javafx.beans.property.SimpleStringProperty;

import static application.storage.Storage.DELIMITER;

public class Event extends Task {

    private  SimpleStringProperty eventID;
    private  SimpleStringProperty detailsID;
    private  SimpleStringProperty dateID;
    private  SimpleStringProperty startID;
    private  SimpleStringProperty endID;
    private  SimpleStringProperty priorityID;
    private static Integer count = 0;


    public Event(String details, String date, String startTime, String endTime, String priority) {
        count++;
        this.eventID = new SimpleStringProperty(count.toString());
        this.detailsID = new SimpleStringProperty(details);
        this.dateID = new SimpleStringProperty(date);
        this.startID = new SimpleStringProperty(startTime);
        this.endID = new SimpleStringProperty(endTime);
        this.priorityID = new SimpleStringProperty(priority);
    }

    public Event(int count ,String details, String date, String startTime, String endTime, String priority) {
        this.eventID = new SimpleStringProperty(Integer.toString(count));
        this.detailsID = new SimpleStringProperty(details);
        this.dateID = new SimpleStringProperty(date);
        this.startID = new SimpleStringProperty(startTime);
        this.endID = new SimpleStringProperty(endTime);
        this.priorityID = new SimpleStringProperty(priority);
    }


    public String getEventID() {
        return eventID.get();
    }

    public SimpleStringProperty eventIDProperty() {
        return eventID;
    }

    public String getDetailsID() {
        return detailsID.get();
    }

    public SimpleStringProperty detailsIDProperty() {
        return detailsID;
    }

    public String getDateID() {
        return dateID.get();
    }

    public SimpleStringProperty dateIDProperty() {
        return dateID;
    }

    public String getStartID() {
        return startID.get();
    }

    public SimpleStringProperty startIDProperty() {
        return startID;
    }

    public String getEndID() {
        return endID.get();
    }

    public SimpleStringProperty endIDProperty() {
        return endID;
    }

    public String getPriorityID() {
        return priorityID.get();
    }

    public SimpleStringProperty priorityIDProperty() {
        return priorityID;
    }

    public String toFile() {
        String out = this.getDetailsID() + DELIMITER + this.getDateID() + DELIMITER + this.getStartID() + DELIMITER +
                this.getEndID() + DELIMITER + this.getPriorityID() + System.lineSeparator();
        return out;
    }

    public void setEventID(String eventID) {
        this.eventID.set(eventID);
    }

    public void setDetailsID(String detailsID) {
        this.detailsID.set(detailsID);
    }

    public void setDateID(String dateID) {
        this.dateID.set(dateID);
    }

    public void setStartID(String startID) {
        this.startID.set(startID);
    }

    public void setEndID(String endID) {
        this.endID.set(endID);
    }

    public void setPriorityID(String priorityID) {
        this.priorityID.set(priorityID);
    }
}
