package app.use_case.display_event;

import java.util.ArrayList;

/**
 * Data Access Object Interface for the Display Event Use Case.
 * This interface defines the contract for retrieving event details from the
 * database or other storage mechanisms. Implementations of this interface
 * handle the fetching of event data.
 */
public interface DisplayEventDataAccessInterface {

    /**
     * Retrieves details of all events.
     * The details are returned as a nested list, where each inner list represents
     * the properties of a single event.
     * @return A nested {@link ArrayList} containing event details.
     *         Each inner {@link ArrayList} represents a single event's properties.
     */
    ArrayList<ArrayList<Object>> eventDetails();
}
