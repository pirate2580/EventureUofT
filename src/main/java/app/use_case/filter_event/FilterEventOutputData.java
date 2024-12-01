package app.use_case.filter_event;

import java.util.List;

import app.entity.Event.Event;

/**
 * Output Data for the Filter Event Use Case.
 * This class encapsulates the data generated as a result of the Filter Event use case.
 * It holds the list of events that meet the filtering criteria and is passed to the output boundary
 * for preparing the success view.
 */
public class FilterEventOutputData {
    private final List<Event> filteredEvents;

    /**
     * Constructs a new {@link FilterEventOutputData}.
     * @param filteredEvents The list of events that match the filtering criteria. Must not be {@code null}.
     */
    public FilterEventOutputData(List<Event> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }

    /**
     * Retrieves the list of events that match the filtering criteria.
     * @return A {@link List} of {@link Event} objects representing the filtered events.
     */
    public List<Event> getFilteredEvents() {
        return filteredEvents;
    }
}
