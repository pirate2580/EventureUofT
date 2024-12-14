package app.use_case.view_rsvp;

import java.util.List;

/**
 * Output data for the View RSVP Use Case.
 * This class contains the data to be passed to the output boundary for displaying the RSVP events for a user.
 * It holds a list of events that the user has RSVP'd to.
 */
public class ViewRSVPOutputData {

    private final List<String> rsvpEvents;

    /**
     * Constructor for ViewRSVPOutputData.
     * Initializes the output data with a list of RSVP'd events.
     * @param rsvpEvents The list of events the user has RSVP'd to. Must not be {@code null}.
     */
    public ViewRSVPOutputData(List<String> rsvpEvents) {
        this.rsvpEvents = rsvpEvents;
    }

    /**
     * Retrieves the list of RSVP'd events.
     * @return The list of events the user has RSVP'd to. Never {@code null}.
     */
    public List<String> getRSVPEvents() {
        return rsvpEvents;
    }
}
