package app.interface_adapter.create_event;

import java.util.ArrayList;
import java.util.List;

/**
 * State class representing the input data and validation state for creating an event.
 */
public class CreateEventState {
    private String title;
    private String description;
    private String eventId;
    private String organizer;
    private String longitude;
    private String latitude;
    private String capacity;
    private String dateTimeError;
    private String capacityError;
    private String latitudeError;
    private String longitudeError;
    private String dateTime;
    private List<String> tags;
    private String usernameState;

    /**
     * Gets the username associated with this event state.
     *
     * @return the username of the user.
     */
    public String getUsernameState() {
        return usernameState;
    }

    /**
     * Sets the username associated with this event state.
     *
     * @param usernameState the username of the user.
     */
    public void setUsernameState(String usernameState) {
        this.usernameState = usernameState;
    }

    /**
     * Gets the title of the event.
     *
     * @return the event title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the event.
     *
     * @param title the event title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the event.
     *
     * @return the event description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the event.
     *
     * @param description the event description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the date and time of the event.
     *
     * @return the date and time of the event.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time of the event.
     *
     * @param dateTime the date and time of the event.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the error message for the date and time field.
     *
     * @return the error message for the date and time.
     */
    public String getDateTimeError() {
        return dateTimeError;
    }

    /**
     * Sets the error message for the date and time field.
     *
     * @param dateTimeError the error message for the date and time.
     */
    public void setDateTimeError(String dateTimeError) {
        this.dateTimeError = dateTimeError;
    }

    /**
     * Gets the unique identifier for the event.
     *
     * @return the event ID.
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Sets the unique identifier for the event.
     *
     * @param eventId the event ID.
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * Gets the name of the event organizer.
     *
     * @return the organizer's name.
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * Sets the name of the event organizer.
     *
     * @param organizer the organizer's name.
     */
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    /**
     * Gets the capacity of the event.
     *
     * @return the event capacity.
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the event.
     *
     * @param capacity the event capacity.
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the error message for the capacity field.
     *
     * @return the error message for the capacity field.
     */
    public String getCapacityError() {
        return capacityError;
    }

    /**
     * Sets the error message for the capacity field.
     *
     * @param capacityError the error message for the capacity.
     */
    public void setCapacityError(String capacityError) {
        this.capacityError = capacityError;
    }

    /**
     * Gets the longitude of the event location.
     *
     * @return the longitude of the event location.
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the event location.
     *
     * @param longitude the longitude of the event location.
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the error message for the longitude field.
     *
     * @return the error message for the longitude field.
     */
    public String getLongitudeError() {
        return longitudeError;
    }

    /**
     * Sets the error message for the longitude field.
     *
     * @param longitudeError the error message for the longitude field.
     */
    public void setLongitudeError(String longitudeError) {
        this.longitudeError = longitudeError;
    }

    /**
     * Gets the latitude of the event location.
     *
     * @return the latitude of the event location.
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the event location.
     *
     * @param latitude the latitude of the event location.
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the error message for the latitude field.
     *
     * @return the error message for the latitude field.
     */
    public String getLatitudeError() {
        return latitudeError;
    }

    /**
     * Sets the error message for the latitude field.
     *
     * @param latitudeError the error message for the latitude field.
     */
    public void setLatitudeError(String latitudeError) {
        this.latitudeError = latitudeError;
    }

    /**
     * Gets the tags associated with the event.
     *
     * @return a list of tags.
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags associated with the event.
     *
     * @param tags a comma-separated string of tags.
     */
    public void setTags(String tags) {
        ArrayList<String> tagger = new ArrayList<>();
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < tags.length(); i++) {
            if (tags.charAt(i) == ',') {
                tagger.add(ret.toString());
                ret = new StringBuilder();
            }
            else {
                ret.append(tags.charAt(i));
            }
        }
        tagger.add(ret.toString());
        this.tags = tagger;
    }

    /**
     * Converts the state of the event to a string representation.
     *
     * @return a string representation of the event state.
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("SignupState{"
                + "title='" + title + '\''
                + ", description='" + description + '\''
                + ", dateTime='" + dateTime + '\''
                + ", eventId='" + eventId + '\''
                + ", organizer='" + organizer + '\''
                + ", capacity='" + capacity + '\''
                + ", latitude='" + latitude + '\''
                + ", longitude='" + longitude + '\''
                + ", tags='");

        for (String t : tags) {
            ret.append(t).append(' ');
        }
        ret.append('\'');
        ret.append('}');
        return ret.toString();
    }
}
