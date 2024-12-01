package app.use_case.rsvp_event;

/**
 * Output Boundary for the RSVP Event Use Case.
 * This interface defines the contract for preparing the views after processing an RSVP event.
 * It includes methods for handling success and failure scenarios.
 */
public interface RSVPEventOutputBoundary {

    /**
     * Prepares the success view for the RSVP Event use case.
     * This method is invoked when the RSVP operation is successful. It communicates
     * the results of the operation to the presenter, which prepares the success view.
     * @param outputData The output data containing details of the RSVP event.
     *                   Must not be {@code null}.
     */
    void prepareSuccessView(RSVPEventOutputData outputData);

    /**
     * Prepares the failure view for the RSVP Event use case.
     * This method is invoked when the RSVP operation fails. It communicates
     * the reason for the failure to the presenter, which prepares the failure view.
     * @param errorMessage A descriptive message explaining the reason for the failure.
     *                     Must not be {@code null}.
     */
    void prepareFailView(String errorMessage);
}
