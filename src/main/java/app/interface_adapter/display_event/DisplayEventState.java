package app.interface_adapter.display_event;

import java.util.ArrayList;

/**
 * Represents the state for displaying events, including a list of events and user information.
 */
public class DisplayEventState {

    private ArrayList<ArrayList<Object>> events;
    private String usernameState;

    /**
     * Retrieves the username associated with the current state.
     *
     * @return the username of the user, or {@code null} if not set.
     */
    private String getUsernameState() {
        return usernameState;
    }

    /**
     * Sets the username for the current state.
     * @param usernameState the username to set. Can be {@code null}.
     */
    private void setUsernameState(String usernameState) {
        this.usernameState = usernameState;
    }

    /**
     * Sets the list of events for the current state.
     *
     * @param events the list of events to set. Can be {@code null}.
     */
    public void setEvents(ArrayList<ArrayList<Object>> events) {
        this.events = events;
    }

    /**
     * Retrieves all events.
     * @return List of events.
     */
    public ArrayList<ArrayList<Object>> getEvents() {
        return this.events;
    }
}
