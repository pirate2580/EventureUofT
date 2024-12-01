
package app.use_case.notify_users;

/**
 * Output boundary for the Notify User use case.
 * This interface defines the contract for preparing the output views for the Notify User use case.
 * It includes methods for handling success and failure scenarios when notifying users about an event.
 */
public interface NotifyUserOutputBoundary {

    /**
     * Prepares the success view for the Notify User use case.
     * This method is called when the user notification is successfully processed.
     * It communicates the result of the operation, including any relevant data (e.g., notification status),
     * to the presenter, which then updates the view accordingly.
     * @param outputData The output data containing the result of the notification operation.
     *                   Must not be {@code null}.
     */
    void prepareSuccessView(NotifyUserOutputData outputData);

    /**
     * Prepares the failure view for the Notify User use case.
     * This method is called when there is an error in notifying users. It communicates the error message
     * to the presenter, which will then update the view to reflect the failure.
     * @param errorMessage A descriptive message explaining the reason for the failure.
     *                     Must not be {@code null}.
     */
    void prepareFailView(String errorMessage);
}
