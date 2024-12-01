package app.use_case.display_event;

/**
 * Output Boundary for the Display Event Use Case.
 * This interface defines the contract for handling the output of the Display Event
 * use case. It specifies methods for preparing the view in both success and failure scenarios.
 */
public interface DisplayEventOutputBoundary {

    /**
     * Prepares the success view for the Display Event Use Case.
     * This method is called when the event details are successfully retrieved.
     * It prepares the data to be displayed on the front end.
     * @param eventOutputData The output data containing event details. Must not be {@code null}.
     */
    void prepareSuccessView(DisplayEventOutputData eventOutputData);

    /**
     * Prepares the failure view for the Display Event Use Case.
     * This method is called when an error occurs during the execution of the use case.
     * It prepares an error message to be displayed on the front end.
     * @param errorMessage A description of the error. Must not be {@code null}.
     */
    void prepareFailView(String errorMessage);
}
