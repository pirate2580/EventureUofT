package app.interface_adapter.modify_user;

import org.springframework.stereotype.Component;

import app.use_case.modify_user.ModifyUserOutputBoundary;
import app.use_case.modify_user.ModifyUserOutputData;

/**
 * Presenter for the Modify User Use Case.
 * This class handles the presentation logic for the modify user operation.
 * It prepares the success or failure views by updating the state with
 * appropriate messages and success flags, allowing the view layer to
 * display relevant feedback to the user.
 */
@Component
public class ModifyUserPresenter implements ModifyUserOutputBoundary {

    private String message;
    private boolean success;

    /**
     * Prepares the success view for the Modify User Use Case.
     * Updates the presenter state with the success message from the use case output
     * and sets the success flag to {@code true}.
     * @param outputData the output data containing the success message and other details.
     */
    @Override
    public void prepareSuccessView(ModifyUserOutputData outputData) {
        this.message = outputData.getMessage();
        this.success = true;
        // TODO: need update
    }

    /**
     * Prepares the failure view for the Modify User Use Case.
     * Updates the presenter state with an error message and sets the success flag to {@code false}.
     * @param errorMessage the error message explaining the failure reason.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        this.message = errorMessage;
        this.success = false;
        // TODO: need update
    }

    /**
     * Retrieves the success status of the operation.
     * @return {@code true} if the operation was successful, {@code false} otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Retrieves the message associated with the operation.
     * This can either be a success message or an error message, depending on the outcome
     * of the modify user operation.
     * @return the message, or {@code null} if no message is set.
     */
    public String getMessage() {
        return message;
    }
}
