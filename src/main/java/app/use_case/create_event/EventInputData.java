package app.use_case.create_event;

import java.util.List;

/**
 * Input Data for the Event Use Case.
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
     * Constructs an instance of {@link EventInputData} with all required event details.
     * This object is used as input to the Create Event use case, capturing all
     * user-provided information for registering an event.
     * @param usernameState the username of the user creating the event.
     * @param eventId       the unique identifier for the event.
     * @param organizer     the name of the event organizer.
     * @param title         the title of the event.
     * @param description   the description of the event.
     * @param dateTime      the date and time of the event in ISO format.
     * @param capacity      the maximum number of participants allowed.
     * @param latitude      the latitude of the event location.
     * @param longitude     the longitude of the event location.
     * @param tags          a list of tags associated with the event.
     */
    public EventInputData(String usernameState, String eventId, String organizer,
                          String title, String description, String dateTime,
                          int capacity, float latitude, float longitude, List<String> tags) {
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

    /**
     * Retrieves the username of the user creating the event.
     *
     * @return the username state.
     */
    public String getUsernameState() {
        return this.usernameState;
    }

    /**
     * Retrieves the title of the event.
     *
     * @return the event title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the description of the event.
     *
     * @return the event description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the date and time of the event.
     *
     * @return the event date and time in ISO format.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Retrieves the maximum capacity of the event.
     *
     * @return the maximum number of participants allowed.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Retrieves the latitude of the event location.
     *
     * @return the latitude of the event.
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Retrieves the longitude of the event location.
     *
     * @return the longitude of the event.
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Retrieves the tags associated with the event.
     *
     * @return a list of tags.
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Retrieves the unique identifier of the event.
     *
     * @return the event ID.
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Retrieves the name of the event organizer.
     *
     * @return the organizer name.
     */
    public String getOrganizer() {
        return organizer;
    }
}
