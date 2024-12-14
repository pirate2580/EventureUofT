package app.use_case.modify_user;

/**
 * Input Boundary for the Modify User Use Case.
 * This interface defines the contract for modifying a user's account details. It provides
 * the method to execute the modify user use case, which includes actions such as updating
 * a user's username, email, or password.
 */
public interface ModifyUserInputBoundary {
    /**
     * Executes the modify user use case.
     * @param modifyUserInputData An object containing all the information needed to modify a user's account details.
     *                            It includes fields such as current username, new username, new email, old password,
     *                            and new password.
     */
    void execute(ModifyUserInputData modifyUserInputData);

}
