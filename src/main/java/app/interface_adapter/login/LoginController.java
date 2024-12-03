package app.interface_adapter.login;

import app.use_case.login.LoginInputBoundary;
import app.use_case.login.LoginInputData;

/**
 * Controller for the Login Use Case.
 * This class manages the user input for the login feature and delegates the
 * execution to the corresponding use case interactor. It also provides navigation
 * to the registration view.
 */
public class LoginController {

    private final LoginInputBoundary userLoginUseCaseInteractor;

    /**
     * Constructs a new LoginController.
     * @param userLoginUseCaseInteractor the interactor responsible for executing the login use case.
     *                                   Must not be null.
     */
    public LoginController(LoginInputBoundary userLoginUseCaseInteractor) {
        this.userLoginUseCaseInteractor = userLoginUseCaseInteractor;
    }

    /**
     * Executes the login use case.
     * Creates a LoginInputData object with the provided username and password
     * and delegates the execution to the use case interactor.
     * @param username the username of the user. Must not be null.
     * @param password the password of the user. Must not be null.
     */
    public void execute(String username, String password) {
        final LoginInputData loginInputData = new LoginInputData(username, password);
        this.userLoginUseCaseInteractor.execute(loginInputData);
    }

    /**
     * Switches the application to the registration view.
     */
    public void switchToRegisterView() {
        this.userLoginUseCaseInteractor.switchToRegisterView();
    }
}
