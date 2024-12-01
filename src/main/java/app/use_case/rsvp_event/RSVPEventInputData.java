package app.use_case.rsvp_event;

/**
 * Input Data for the RSVP Event Use Case.
 * This class contains the data required for a user to RSVP to an event.
 * It includes the username of the user who wishes to RSVP and the event ID of the event they want to join.
 */
public class RSVPEventInputData {
    private final String username;
    private final String eventId;

    /**
     * Constructor for RSVPEventInputData.
     * This constructor initializes the data object with the user's username and the event ID for the RSVP.
     * @param username The username of the user who wants to RSVP.
     * @param eventId The ID of the event that the user wants to RSVP to.
     */
    public RSVPEventInputData(String username, String eventId) {
        this.username = username;
        this.eventId = eventId;
    }

    /**
     * Retrieves the username of the user who wants to RSVP.
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the ID of the event that the user wants to RSVP to.
     * @return the event ID.
     */
    public String getEventId() {
        return eventId;
    }
}
