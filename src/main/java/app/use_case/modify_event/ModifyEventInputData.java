package app.use_case.modify_event;

import java.util.List;

/**
 * Input Data for the Modify Event Use Case.
 * This class encapsulates the data required to modify an event. It includes
 * all updated event details, such as the title, description, date/time,
 * capacity, location, and additional tags or organizer information. It
 * also handles deletion requests for the event.
 */
public class ModifyEventInputData {
    private final String eventId;
    private final String oldTitle;
    private final String updatedTitle;
    private final String updatedDescription;
    private final String updatedDateTime;
    private final int updatedCapacity;
    private final float updatedLatitude;
    private final float updatedLongitude;
    private final boolean deleteEvent;
    private final List<String> updatedTags;
    private final String updatedOrganizer;

    /**
     * Constructs a new {@link ModifyEventInputData}.
     *
     * @param eventId            The unique ID of the event to modify. Must not be {@code null}.
     * @param oldTitle           The original title of the event. Must not be {@code null}.
     * @param updatedTitle       The updated title of the event. Can be {@code null} if unchanged.
     * @param updatedDescription The updated description of the event. Can be {@code null} if unchanged.
     * @param updatedDateTime    The updated date and time of the event. Can be {@code null} if unchanged.
     * @param updatedCapacity    The updated capacity of the event. Can be {@code -1} if unchanged.
     * @param updatedLatitude    The updated latitude of the event. Can be {@code Float.NaN} if unchanged.
     * @param updatedLongitude   The updated longitude of the event. Can be {@code Float.NaN} if unchanged.
     * @param deleteEvent        A boolean indicating if the event should be deleted.
     * @param updatedTags        A {@link List} of updated tags for the event. Can be {@code null} if unchanged.
     * @param updatedOrganizer   The updated organizer's name. Can be {@code null} if unchanged.
     */
    public ModifyEventInputData(String eventId, String oldTitle, String updatedTitle, String updatedDescription,
                                String updatedDateTime, int updatedCapacity, float updatedLatitude,
                                float updatedLongitude, boolean deleteEvent, List<String> updatedTags,
                                String updatedOrganizer) {
        this.eventId = eventId;
        this.oldTitle = oldTitle;
        this.updatedTitle = updatedTitle;
        this.updatedDescription = updatedDescription;
        this.updatedDateTime = updatedDateTime;
        this.updatedCapacity = updatedCapacity;
        this.updatedLatitude = updatedLatitude;
        this.updatedLongitude = updatedLongitude;
        this.deleteEvent = deleteEvent;
        this.updatedTags = updatedTags;
        this.updatedOrganizer = updatedOrganizer;
    }

    /**
     * Indicates whether the event should be deleted.
     *
     * @return {@code true} if the event is to be deleted; {@code false} otherwise.
     */
    public boolean getDeleteEvent() {
        return this.deleteEvent;
    }

    /**
     * Retrieves the original title of the event.
     *
     * @return The original title as a {@link String}.
     */
    public String getOldTitle() {
        return oldTitle;
    }

    /**
     * Retrieves the unique ID of the event to modify.
     *
     * @return The event ID as a {@link String}.
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Retrieves the updated title of the event.
     *
     * @return The updated title as a {@link String}.
     */
    public String getUpdatedTitle() {
        return updatedTitle;
    }

    /**
     * Retrieves the updated description of the event.
     *
     * @return The updated description as a {@link String}.
     */
    public String getUpdatedDescription() {
        return updatedDescription;
    }

    /**
     * Retrieves the updated date and time of the event.
     *
     * @return The updated date and time as a {@link String}.
     */
    public String getUpdatedDateTime() {
        return updatedDateTime;
    }

    /**
     * Retrieves the updated capacity of the event.
     *
     * @return The updated capacity as an {@code int}.
     */
    public int getUpdatedCapacity() {
        return updatedCapacity;
    }

    /**
     * Retrieves the updated latitude of the event.
     *
     * @return The updated latitude as a {@code float}.
     */
    public float getUpdatedLatitude() {
        return updatedLatitude;
    }

    /**
     * Retrieves the updated longitude of the event.
     *
     * @return The updated longitude as a {@code float}.
     */
    public float getUpdatedLongitude() {
        return updatedLongitude;
    }

    /**
     * Retrieves the updated tags for the event.
     *
     * @return A {@link List} of updated tags.
     */
    public List<String> getUpdatedTags() {
        return this.updatedTags;
    }

    /**
     * Retrieves the updated organizer's name.
     *
     * @return The updated organizer's name as a {@link String}.
     */
    public String getUpdatedOrganizer() {
        return this.updatedOrganizer;
    }
}
