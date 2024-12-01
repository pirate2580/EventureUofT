package app.use_case.view_created_events;

/**
 * Input Boundary interface for the View Created Events Use Case.
 * This interface defines the contract for the use case that handles the
 * viewing of events that a user has created.
 */
public interface ViewCreatedInputBoundary {

    /**
     * Executes the use case for viewing created events.
     * This method processes the input data, retrieves the list of created events,
     * and then prepares the necessary view for the user.
     * @param CreatedInputData The input data that contains the user's information
     *                         (e.g., username) to retrieve the list of created events.
     */
    void execute(ViewCreatedInputData CreatedInputData);

    /**
     * Switches to the home view.
     * This method handles switching the user to the home view if needed.
     */
    void switchToHomeView();
}
