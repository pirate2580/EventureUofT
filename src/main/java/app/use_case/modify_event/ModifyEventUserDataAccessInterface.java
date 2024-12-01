package app.use_case.modify_event;

import java.util.Map;

/**
 * Data Access Object for the Modify Event Use Case.
 */
public interface ModifyEventUserDataAccessInterface {

    /**
     * Function to delete an event from the Firebase Database.
     *
     * @param eventName the name of the event to delete.
     */
    void deleteEvent(String eventName);

    /**
     * Modifies an existing event in the Firestore database.
     *
     * @param eventName The name of the event to modify.
     * @param updatedFields A map containing the fields to update and their new values.
     */
    void modifyEvent(String eventName, Map<String, Object> updatedFields);
}