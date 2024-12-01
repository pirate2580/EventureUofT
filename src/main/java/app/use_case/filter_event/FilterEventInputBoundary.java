package app.use_case.filter_event;

/**
 * Input Boundary for the Filter Event Use Case.
 * This interface defines the contract for interacting with the Filter Event
 * use case. It specifies methods for executing the filtering logic and managing view transitions.
 */
public interface FilterEventInputBoundary {

    /**
     * Executes the Filter Event Use Case.
     * This method filters events based on the criteria provided in the input data.
     * @param filterEventInputData The input data containing the event filter criteria.
     *                             Must not be {@code null}.
     */
    void execute(FilterEventInputData filterEventInputData);

    /**
     * Switches the application to the home view.
     * This method is invoked to transition back to the home view after completing
     * or canceling the filtering process.
     */
    void switchToHomeView();
}
