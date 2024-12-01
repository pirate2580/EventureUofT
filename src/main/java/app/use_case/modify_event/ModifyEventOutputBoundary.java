package app.use_case.modify_event;

/**
 * Output Boundary for the Modify Event Use Case.
 * This interface defines the contract for preparing the output views for
 * modifying events. It includes methods for handling success and failure scenarios.
 */
public interface ModifyEventOutputBoundary {
    /**
     * Prepares the success view for the Modify Event Use Case.
     * This method is invoked when the event modification is successful. It communicates
     * the results of the operation to the presenter, which prepares the success view.
     *
     * @param outputData The output data containing details of the modified event.
     *                   Must not be {@code null}.
     */
    void prepareSuccessView(ModifyEventOutputData outputData);

    /**
     * Prepares the failure view for the Modify Event Use Case.
     * This method is invoked when the event modification fails. It communicates
     * the reason for the failure to the presenter, which prepares the failure view.
     *
     * @param errorMessage A descriptive message explaining the reason for the failure.
     *                     Must not be {@code null}.
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the home view.
     * This method notifies the presenter to navigate back to the home view
     * after completing the modify event operation.
     */
    void switchToHomeView();
}
