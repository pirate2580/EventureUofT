package app.interface_adapter.view_event;

import app.entity.Event.Event;

/**
 * Represents the state for the View Event feature.
 * This class holds the state information for viewing a specific event, including
 * the event details and the username of the user currently interacting with the view.
 */
public class ViewEventState {
    private Event event;
    private String usernameState;

    /**
     * Retrieves the username associated with the state.
     * @return the username of the user, or {@code null} if not set.
     */
    public String getUsernameState() {
        return usernameState;
    }

    /**
     * Sets the username for the event view state.
     * @param usernameState the username of the user. Can be {@code null}.
     */
    public void setUsernameState(String usernameState) {
        this.usernameState = usernameState;
    }

    /**
     * Retrieves the event currently being viewed.
     * @return the event, or {@code null} if not set.
     */
    public Event getViewEvent() {
        return this.event;
    }

    /**
     * Sets the event for the event view state.
     * @param event the event to set. Can be {@code null}.
     */
    public void setViewEvent(Event event) {
        this.event = event;
    }
}

