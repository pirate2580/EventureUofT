package app.use_case.view_rsvp;

import java.util.List;

/**
 * Data Access Interface for the View RSVP Use Case.
 * This interface defines the contract for accessing the RSVP data from the data source.
 * It allows retrieving a list of events that a user has RSVPed to.
 */
public interface ViewRSVPDataAccessInterface {

    /**
     * Retrieves a list of events that the user has RSVP'd to.
     * This method is called to fetch all events that the specified user has RSVP'd to.
     * @param username The username of the user whose RSVP events are to be retrieved.
     * @return A list of event titles that the user has RSVP'd to.
     */
    List<String> getRSVPEvents(String username);
}
