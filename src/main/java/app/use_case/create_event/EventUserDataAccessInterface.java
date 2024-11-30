package app.use_case.create_event;


import app.entity.Event.Event;

/**
 * Data Access Object for the Event Use Case.
 * TODO: notice that although this file is in Use Case, it is implemented in the data_access
 * folder
 */
public interface EventUserDataAccessInterface {
    /**
     * Saves the event to the database
     * @param event to save onto the database
     */
    void saveEvent(String usernameState, Event event);

}