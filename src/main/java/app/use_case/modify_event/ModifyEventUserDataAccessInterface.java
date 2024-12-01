package app.use_case.modify_event;

import app.entity.Event.Event;

import java.util.Map;

/**
 * Data Access Object for the Modify Event Use Case.
 */
public interface ModifyEventUserDataAccessInterface {
    /**
     * Function to save an event to the Firebase Database.
     *
     * @param event, the event we want to save.
     */
    public void saveEvent(Event event);


    /**
     * Function to delete an event from the Firebase Database.
     *
     * @param eventName the name of the event to delete.
     */
    public void deleteEvent(String eventName);


    /**
     * Function to get an event by its ID from the Firebase Database.
     *
     * @param eventId, the event we want to save.
     */
    public Event getEventById(String eventId);

    /**
     * Modifies an existing event in the Firestore database.
     *
     * @param eventName The name of the event to modify.
     * @param updatedFields A map containing the fields to update and their new values.
     */
    public void modifyEvent(String eventName, Map<String, Object> updatedFields);
}