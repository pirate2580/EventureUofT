package app.use_case.rsvp_event;

/**
 * Represents the output data for the RSVP Event use case.
 * This class encapsulates the details of a successful RSVP event, including the
 * username of the user who RSVPed and the title of the event they RSVPed to.
 */
public class RSVPEventOutputData {
    private final String username;
    private final String eventTitle;

    /**
     * Containing the details for a successful RSVP.
     * @param username The username of the user who RSVPed.
     * @param eventTitle The title of the event that the user RSVPed to.
     */
    public RSVPEventOutputData(String username, String eventTitle) {
        this.username = username;
        this.eventTitle = eventTitle;
    }

    /**
     * Retrieves the username of the user who RSVPed to the event.
     * @return the username of the user who RSVPed.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the title of the event that the user RSVPed to.
     * @return the event title.
     */
    public String getEventTitle() {
        return eventTitle;
    }
}
