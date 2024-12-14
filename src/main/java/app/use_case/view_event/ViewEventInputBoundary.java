package app.use_case.view_event;

/**
 * Input Boundary for the View Event use case.
 * This interface defines the contract for the actions related to viewing an event.
 * It contains methods to execute the event viewing logic and to switch to the home view.
 */
public interface ViewEventInputBoundary {

    /**
     * Executes the View Event use case.
     * This method takes in the necessary data to view a specific event, retrieves its details,
     * and prepares the appropriate response to be presented to the user.
     * @param viewEventInputData The data needed to view the event, such as the event's title.
     */
    void execute(ViewEventInputData viewEventInputData);

    /**
     * Switches to the home view.
     * This method will be invoked when the user needs to return to the home view from the current event view.
     */
    void switchToHomeView();
}
