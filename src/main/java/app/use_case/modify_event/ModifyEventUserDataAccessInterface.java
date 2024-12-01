package app.use_case.modify_event;

import java.util.Map;

import app.entity.Event.Event;

/**
 * Data Access Object for the Modify Event Use Case.
 * This interface defines the methods for interacting with the data layer
 * to perform operations such as saving, deleting, and retrieving events.
 */
public interface ModifyEventUserDataAccessInterface {

    /**
     * Saves an event to the database.
     * This method persists the given event to the database.
     *
     * @param event The event to save. Must not be {@code null}.
     */
    void saveEvent(Event event);

    /**
     * Deletes an event from the database.
     * This method removes the event with the specified name from the database.
     *
     * @param eventName The name of the event to delete. Must not be {@code null}.
     */
    void deleteEvent(String eventName);

    /**
     * Retrieves an event by its ID from the database.
     * This method fetches the event corresponding to the given ID.
     *
     * @param eventId The ID of the event to retrieve. Must not be {@code null}.
     * @return The {@link Event} object if found, or {@code null} if no event with the specified ID exists.
     */
    Event getEventById(String eventId);

    /**
     * Modifies an existing event in the Firestore database.
     *
     * @param eventName     The name of the event to modify.
     * @param updatedFields A map containing the fields to update and their new values.
     */
    void modifyEvent(String eventName, Map<String, Object> updatedFields);
}
