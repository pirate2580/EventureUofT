package app.use_case.view_created_events;

import java.util.List;

/**
 * Output data for the "View Created Events" use case.
 * This class encapsulates the data returned by the use case, which includes
 * a list of events created by the user.
 */
public class ViewCreatedOutputData {

    private final List<String> createdEvents;

    /**
     * Constructor for ViewCreatedOutputData.
     * Initializes the object with a list of events that have been created
     * by the user.
     *
     * @param createdEvents The list of created events.
     */
    public ViewCreatedOutputData(List<String> createdEvents) {
        this.createdEvents = createdEvents;
    }

    /**
     * Retrieves the list of created events.
     * This method provides access to the list of events that the user has created.
     *
     * @return The list of created events.
     */
    public List<String> getCreatedEvents() {
        return createdEvents;
    }
}
