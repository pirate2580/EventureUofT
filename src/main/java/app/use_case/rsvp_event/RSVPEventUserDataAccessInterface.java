package app.use_case.rsvp_event;

/**
 * Data Access Interface for the RSVP Event use case.
 * This interface defines the methods for interacting with the underlying data layer
 * to manage users and events, specifically for adding users to the RSVP list of events.
 */
public interface RSVPEventUserDataAccessInterface {

    /**
     * Adds a user to the RSVP list of an event.
     * This method associates the user with an event by adding their username
     * to the list of attendees for the specified event.
     * @param username The username of the user to be added to the event's RSVP list.
     * @param eventId  The ID of the event the user is RSVPing to.
     */
    void addUserToRSVPList(String username, String eventId);
}
