package app.use_case.create_event;

import java.util.List;

/**
 * Input Data for the Event Use Case
 * The View sends data to the controller which should have an actionListener, and then
 * will prepare data one triggered
 */
public class EventInputData {
    private String usernameState;
    private String title;
    private String description;
    private String dateTime;
    private int capacity;
    private float latitude;
    private float longitude;
    private List<String> tags;
    private String eventId;
    private String organizer;

    /**
     * Constructor for EventInputData, the user registers an event by entering
     * the event title, description, dateTime, capacity, latitude, longitude, tags, eventId
     * and nothing else. The two other attributes createdEvents
     * and rsvpEvents are added to later
     * @param title
     * @param description
     * @param dateTime
     * @param capacity
     * @param latitude
     * @param longitude
     * @param tags
     * @param eventId
     * @param organizer
     */
    public EventInputData(String usernameState, String eventId, String organizer, String title, String description, String dateTime, int capacity, float latitude,
                          float longitude, List<String> tags) {
        this.usernameState = usernameState;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tags = tags;
        this.eventId = eventId;
        this.organizer = organizer;
    }

    public String getUsernameState() {return this.usernameState;}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getEventId() { return eventId; }

    public String getOrganizer() { return organizer; }
}