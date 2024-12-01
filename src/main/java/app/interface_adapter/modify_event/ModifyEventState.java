package app.interface_adapter.modify_event;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the state of the Modify Event feature.
 * This class manages all the attributes related to an event being modified,
 * including its titles, description, date and time, capacity, location, tags,
 * and organizer. It also tracks whether the event is marked for deletion.
 */
public class ModifyEventState {
    private String oldTitle;
    private String newTitle;
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
    private boolean deleteEvent;
    private String usernameState;

    /**
     * Retrieves the username associated with this state.
     * @return the username of the user, or {@code null} if not set.
     */
    private String getUsernameState() {
        return usernameState;
    }

    /**
     * Sets the username associated with this state.
     * @param usernameState the username to set. Can be {@code null}.
     */
    private void setUsernameState(String usernameState) {
        this.usernameState = usernameState;
    }

    /**
     * Retrieves whether the event is marked for deletion.
     * @return {@code true} if the event is marked for deletion, {@code false} otherwise.
     */
    public boolean getDeleteEvent() {
        return deleteEvent;
    }

    /**
     * Sets whether the event is marked for deletion.
     * @param deleteEvent {@code true} to mark the event for deletion, {@code false} otherwise.
     */
    public void setDeleteEvent(boolean deleteEvent) {
        this.deleteEvent = deleteEvent;
    }

    /**
     * Retrieves the original title of the event.
     * @return the original title of the event.
     */
    public String getOldTitle() {
        return oldTitle;
    }

    /**
     * Sets the original title of the event.
     * @param oldTitle the original title to set.
     */
    public void setOldTitle(String oldTitle) {
        this.oldTitle = oldTitle;
    }

    /**
     * Retrieves the new title of the event.
     * @return the new title of the event.
     */
    public String getNewTitle() {
        return newTitle;
    }

    /**
     * Sets the new title of the event.
     * @param newTitle the new title to set.
     */
    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    /**
     * Retrieves the description of the event.
     * @return the event description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the event.
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the date and time of the event.
     * @return the date and time of the event, or {@code null} if not set.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time of the event.
     * @param dateTime the date and time to set. Can be {@code null}.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Retrieves the error message associated with the date and time.
     * @return the error message for the date and time, or {@code null} if no error exists.
     */
    public String getDateTimeError() {
        return dateTimeError;
    }

    /**
     * Sets the error message for the date and time.
     * @param dateTimeError the error message to set. Can be {@code null}.
     */
    public void setDateTimeError(String dateTimeError) {
        this.dateTimeError = dateTimeError;
    }

    /**
     * Retrieves the unique identifier of the event.
     * @return the event ID, or {@code null} if not set.
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Sets the unique identifier of the event.
     * @param eventId the event ID to set. Can be {@code null}.
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * Retrieves the name of the event organizer.
     * @return the name of the organizer, or {@code null} if not set.
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * Sets the name of the event organizer.
     * @param organizer the organizer name to set. Can be {@code null}.
     */
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    /**
     * Retrieves the capacity of the event.
     * @return the capacity as a string, or {@code null} if not set.
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the event.
     * @param capacity the capacity to set. Can be {@code null}.
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     * Retrieves the error message associated with the event capacity.
     * @return the capacity error message, or {@code null} if no error exists.
     */
    public String getCapacityError() {
        return capacityError;
    }

    /**
     * Sets the error message for the event capacity.
     * @param capacityError the capacity error message to set. Can be {@code null}.
     */
    public void setCapacityError(String capacityError) {
        this.capacityError = capacityError;
    }

    /**
     * Retrieves the longitude of the event location.
     * @return the longitude as a string, or {@code null} if not set.
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the event location.
     * @param longitude the longitude to set. Can be {@code null}.
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Retrieves the error message associated with the event longitude.
     * @return the longitude error message, or {@code null} if no error exists.
     */
    public String getLongitudeError() {
        return longitudeError;
    }

    /**
     * Sets the error message for the event longitude.
     * @param longitudeError the longitude error message to set. Can be {@code null}.
     */
    public void setLongitudeError(String longitudeError) {
        this.longitudeError = longitudeError;
    }

    /**
     * Retrieves the latitude of the event location.
     * @return the latitude as a string, or {@code null} if not set.
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the event location.
     * @param latitude the latitude to set. Can be {@code null}.
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Retrieves the error message associated with the event latitude.
     * @return the latitude error message, or {@code null} if no error exists.
     */
    public String getLatitudeError() {
        return latitudeError;
    }

    /**
     * Sets the error message for the event latitude.
     * @param latitudeError the latitude error message to set. Can be {@code null}.
     */
    public void setLatitudeError(String latitudeError) {
        this.latitudeError = latitudeError;
    }

    /**
     * Retrieves the list of tags associated with the event.
     * @return the list of tags, or {@code null} if not set.
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags for the event.
     * @param tags a comma-separated string of tags. Can be {@code null}.
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
     * Returns a string representation of the Modify Event state.
     * @return a string containing the event details.
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("SignupState{"
                + "oldTitle='" + oldTitle + '\''
                + "newTitle='" + newTitle + '\''
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
