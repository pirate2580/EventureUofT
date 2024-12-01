package app.use_case.view_rsvp;

/**
 * Input Data for the View RSVP Use Case.
 * This class holds the data required to execute the View RSVP use case. The primary data
 * is the username of the user, which is used to retrieve the events that the user has RSVPed to.
 */
public class ViewRSVPInputData {
    private final String username;

    /**
     * Constructor for ViewRSVPInputData.
     * This constructor initializes the input data with the provided username, which will be used
     * to fetch the list of events the user has RSVPed to.
     * @param username The username of the user whose RSVP events will be viewed.
     */
    public ViewRSVPInputData(String username) {
        this.username = username;
    }

    /**
     * Retrieves the username for the View RSVP use case.
     * This method provides access to the username, which is used to fetch the RSVP events for the user.
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }
}
