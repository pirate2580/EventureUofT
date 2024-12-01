package app.use_case.filter_event;

/**
 * Output Boundary for the Filter Event Use Case.
 * This interface defines the contract for handling the output of the Filter Event
 * use case. It includes methods to prepare the success and failure views and
 * to handle view transitions back to the home screen.
 */
public interface FilterEventOutputBoundary {
    /**
     * Prepares the success view for the Filter Event use case.
     * @param outputData The output data including the list of filtered events.
     */
    void prepareSuccessView(FilterEventOutputData outputData);

    /**
     * Prepares the failure view for the Filter Event use case.
     * @param errorMessage The explanation of the failure.
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches the application to the home view.
     * This method is invoked to transition back to the home screen, typically
     * after completing or canceling the filtering process.
     */
    void switchToHomeView();
}
