package app.use_case.filter_event;

import java.util.List;

import app.entity.Event.Event;

/**
 * Data Access Interface for the Filter Event Use Case.
 * This interface defines the contract for accessing event data based on filtering criteria.
 * Implementations of this interface interact with the data source (e.g., database)
 * to retrieve the list of events that match the specified tags.
 */
public interface FilterEventUserDataAccessInterface {

    /**
     * Finds events based on the provided tags.
     * This method queries the data source to retrieve events that match the given filtering criteria.
     * @param tags A list of tags representing the filtering criteria. Must not be {@code null}.
     * @return A {@link List} of {@link Event} objects that match the provided tags.
     */
    List<Event> findEvents(List<String> tags);
}
