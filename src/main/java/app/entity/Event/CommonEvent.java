package app.entity.Event;

import app.entity.User.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Event interface
 */
public class CommonEvent implements Event {
    private String eventId;
    private String organizer;
    private String title;
    private String description;
    private String dateTime;
    private List<User> attendeesIdList;
    private int capacity;
    private float latitude;
    private float longitude;
    private List<String> tags;

    // No-argument constructor (required by Firestore)
    public CommonEvent() {
        this.eventId = "";
        this.organizer = "";
        this.title = "";
        this.description = "";
        this.dateTime = "";
        this.capacity = 0;
        this.latitude = 0.0f;
        this.longitude = 0.0f;
        this.tags = new ArrayList<>();
        this.attendeesIdList = new ArrayList<>();
    }

    // All-argument constructor
    public CommonEvent(String eventId, String organizer, String title, String description, String dateTime,
                       int capacity, float latitude, float longitude, List<String> tags) {
        this.eventId = eventId;
        this.organizer = organizer;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tags = tags;
        this.attendeesIdList = new ArrayList<>();
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public String getOrganizer() {
        return organizer;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDateTime() {
        return dateTime;
    }

    @Override
    public List<User> getAttendeesIdList() {
        return attendeesIdList;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public float getLatitude() {
        return latitude;
    }

    @Override
    public float getLongitude() {
        return longitude;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    // Setter for organizer
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    @Override
    public boolean isFull() {
        return attendeesIdList.size() >= capacity;
    }

    // Method to add an attendee
    public void addAttendee(User user) {
        if (!isFull() && !attendeesIdList.contains(user)) {
            attendeesIdList.add(user);
        }
    }

}
