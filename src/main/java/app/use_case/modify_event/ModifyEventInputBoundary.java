package app.use_case.modify_event;

/**
 * Input Boundary for the Modify Event Use Case.
 * This interface defines the contract for handling the modification of events,
 * including actions such as editing or deleting an event.
 */
public interface ModifyEventInputBoundary {
    /**
     * Execute the event modification use case such as edit or delete an event.
     * @param modifyEventInputData The input data containing details for modifying the event.
     */
    void execute(ModifyEventInputData modifyEventInputData);
}
