package app.use_case.view_created_events;

/**
 * Output boundary for the "View Created Events" use case.
 * This interface defines the contract for preparing the output views in response
 * to the "view created events" use case. It handles the success view preparation
 * with the output data and provides a method to switch to the home view.
 */
public interface ViewCreatedOutputBoundary {

    /**
     * Prepares the success view for the "View Created Events" use case.
     * This method is called when the events created by the user are successfully
     * retrieved, and it passes the relevant output data to prepare the view.
     * @param outputData The output data containing the list of created events.
     */
    void prepareSuccessView(ViewCreatedOutputData outputData);

    /**
     * Switches to the home view.
     * This method instructs the system to navigate to the home view, typically
     * used for canceling or navigating back to the main screen.
     */
    void switchToHomeView();
}
