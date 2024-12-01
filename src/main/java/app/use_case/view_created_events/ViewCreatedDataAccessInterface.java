package app.use_case.view_created_events;

import java.util.List;

/**
 * Data Access Interface for retrieving created events.
 * This interface defines the methods for interacting with the underlying data layer
 * to fetch events that have been created by a specific user.
 */
public interface ViewCreatedDataAccessInterface {

    /**
     * Retrieves a list of events created by a specific user.
     * This method queries the database or data source to get the events that the
     * user (identified by their username) has created.
     * @param username The username of the user whose created events are to be fetched.
     * @return A list of event titles (or identifiers) created by the user.
     */
    List<String> getCreatedEvents(String username);
}
