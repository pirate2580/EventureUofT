package app.use_case.view_event;

/**
 * The output boundary for the View Event Use Case.
 */
public interface ViewEventOutputBoundary {
    /**
     * Prepares the success view for the View Event use case.
     *
     * @param outputData The output data including the list of relevant events.
     */
    void prepareSuccessView(ViewEventOutputData outputData);

    /**
     * Prepares the failure view for the View Event use case.
     *
     * @param errorMessage The explanation of the failure.
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the home view.
     * This method is used to transition to the home view,
     * typically after a failure or when the user navigates away from
     * the current view.
     */
    void switchToHomeView();
}
