package app.use_case.create_event;

import app.entity.Event.Event;

/**
 * Data Access Object for the Event Use Case folder.
 */
public interface EventUserDataAccessInterface {
    /**
     * Saves the event to the database.
     * @param usernameState The username of the user creating the event.
     * @param event         The event to save. Must not be {@code null}.
     */
    void saveEvent(String usernameState, Event event);
}
