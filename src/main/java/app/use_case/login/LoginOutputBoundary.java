package app.use_case.login;

/**
 * Output Boundary for the Login Use Case.
 * This interface defines the contract for handling the output of the Login Use Case.
 * It includes methods for preparing success and failure views and for navigating to
 * the register view.
 */
public interface LoginOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * This method is invoked when the login process completes successfully. It communicates
     * the results of the login operation to the presenter, which prepares the success view.
     * @param outputData The output data containing relevant information after a successful login.
     *                   Must not be {@code null}.
     * @param s          A message providing additional information about the success.
     */
    void prepareSuccessView(LoginOutputData outputData, String s);

    /**
     * Prepares the failure view for the Login Use Case.
     * This method is invoked when the login process fails. It communicates the failure
     * details to the presenter, which prepares the failure view.
     * @param errorMessage A descriptive message explaining the reason for the failure.
     *                     Must not be {@code null}.
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches the application to the register view.
     * This method is invoked to navigate the user to the register screen, allowing them
     * to create a new account.
     */
    void switchToRegisterView();
}
