package app.interface_adapter.modify_user;

import org.springframework.stereotype.Component;

import app.use_case.modify_user.ModifyUserInputBoundary;
import app.use_case.modify_user.ModifyUserInputData;

/**
 * Controller for the Modify User Use Case.
 * This class handles user input for modifying user details and delegates the logic
 * to the appropriate use case interact. It acts as a bridge between the view
 * layer and the use case layer, preparing the necessary input data.
 */
@Component
public class ModifyUserController {
    private final ModifyUserInputBoundary modifyUserInputBoundary;

    /**
     * Constructs a new {@link ModifyUserController}.
     * @param modifyUserInputBoundary the interactor responsible for handling the modify user use case.
     *                                Must not be {@code null}.
     */
    public ModifyUserController(ModifyUserInputBoundary modifyUserInputBoundary) {
        this.modifyUserInputBoundary = modifyUserInputBoundary;
    }

    /**
     * Executes the Modify User Use Case.
     * Prepares the input data for modifying user details and passes it to the use case interactor.
     * @param username     the current username of the user. Must not be {@code null}.
     * @param newUsername  the new username to set. Can be {@code null} if not being updated.
     * @param newEmail     the new email to set. Can be {@code null} if not being updated.
     * @param oldPassword  the user's current password for verification. Must not be {@code null}.
     * @param newPassword  the new password to set. Can be {@code null} if not being updated.
     */
    public void modifyUser(String username, String newUsername, String newEmail,
                           String oldPassword, String newPassword) {
        ModifyUserInputData inputData = new ModifyUserInputData(username, newUsername,
                newEmail, oldPassword, newPassword);
        modifyUserInputBoundary.execute(inputData);
    }
}
