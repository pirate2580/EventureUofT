package app.interface_adapter.view_created_events;

import java.util.List;

/**
 * Represents the state for the View Created Events feature.
 * This class holds the state information related to the created events view,
 * including the username of the user and a list of events they have created.
 */
public class ViewCreatedEventsState {

    private List<String> createdEvents;
    private String usernameState;

    /**
     * Retrieves the username associated with the state.
     * @return the username of the user, or {@code null} if not set.
     */
    public String getUsernameState() {
        return usernameState;
    }

    /**
     * Sets the username for the created events state.
     * @param usernameState the username of the user. Can be {@code null}.
     */
    public void setUsernameState(String usernameState) {
        this.usernameState = usernameState;
    }

    /**
     * Retrieves the list of created events associated with the user.
     * @return a list of event names, or {@code null} if not set.
     */
    public List<String> getCreatedEvents() {
        return this.createdEvents;
    }

    /**
     * Sets the list of created events for the state.
     * @param createdEvents a list of event names created by the user. Can be {@code null}.
     */
    public void setCreatedEvents(List<String> createdEvents) {
        this.createdEvents = createdEvents;
    }
}
