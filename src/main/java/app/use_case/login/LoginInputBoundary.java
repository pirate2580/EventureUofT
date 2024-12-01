package app.use_case.login;

/**
 * Input Boundary for the Login Use Case.
 * This interface defines the contract for handling the login functionality.
 * It provides methods to process login input data and navigate to the register view.
 */
public interface LoginInputBoundary {

    /**
     * Executes the Login Use Case.
     * This method processes the login request using the provided input data.
     * It handles validation, authentication, and any associated business logic.
     * @param loginInputData The input data containing the username and password. Must not be {@code null}.
     */
    void execute(LoginInputData loginInputData);

    /**
     * Switches the application to the register view.
     * This method is invoked to navigate the user to the register screen when they
     * choose to create a new account instead of logging in.
     */
    void switchToRegisterView();
}
