package app.interface_adapter.filter_event;

import java.util.List;

import app.entity.Event.Event;

/**
 * Represents the state for filtering events.
 * This class manages the filtered events and associated user information.
 * It serves as part of the application's state management for the Filter Event Use Case.
 * <strong>Note:</strong> Dependency on {@link app.entity.Event.Event} may violate Clean Architecture
 * principles if this class resides in a layer that shouldn't directly depend on the {@code Event} entity.
 * Review and refactor if needed.
 */
public class FilterEventState {

    private List<Event> filteredEvents;
    private String usernameState;

    /**
     * Retrieves the username associated with this state.
     *
     * @return the username of the user, or {@code null} if not set.
     */
    public String getUsernameState() {
        return usernameState;
    }

    /**
     * Sets the username associated with this state.
     *
     * @param usernameState the username to set. Can be {@code null}.
     */
    public void setUsernameState(String usernameState) {
        this.usernameState = usernameState;
    }

    /**
     * Retrieves the list of filtered events.
     *
     * @return a list of {@link Event} objects representing the filtered events,
     *         or {@code null} if no events have been set.
     */
    public List<Event> getFilteredEvents() {
        return this.filteredEvents;
    }

    /**
     * Sets the list of filtered events.
     *
     * @param filteredEvents a list of {@link Event} objects. Can be {@code null}.
     */
    public void setFilteredEvents(List<Event> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }

    /**
     * Returns a string representation of the filtered events state.
     * This method is currently a placeholder and should be updated to provide
     * more meaningful details about the state.
     * @return a string representation of this state.
     */
    @Override
    public String toString() {
        return "Filtered events";
    }

}
