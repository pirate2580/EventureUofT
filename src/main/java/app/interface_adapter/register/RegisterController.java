package app.interface_adapter.register;

import app.use_case.register.RegisterInputBoundary;
import app.use_case.register.RegisterInputData;

/**
 * Controller for the Register Use Case.
 */
public class RegisterController {

    private final RegisterInputBoundary userRegisterUseCaseInteractor;

    /**
     * Constructs a new {@link RegisterController}.
     * @param userRegisterUseCaseInteractor the interactor responsible for handling the register use case.
     *                                      Must not be {@code null}.
     */
    public RegisterController(RegisterInputBoundary userRegisterUseCaseInteractor) {
        this.userRegisterUseCaseInteractor = userRegisterUseCaseInteractor;
    }

    /**
     * Executes the Register Use Case.
     * Prepares the input data with the provided user details and passes it to the
     * use case interactor for processing.
     * @param username the username of the user. Must not be {@code null}.
     * @param email    the email address of the user. Must not be {@code null}.
     * @param password the password of the user. Must not be {@code null}.
     */
    public void execute(String username, String email, String password) {
        final RegisterInputData registerInputData = new RegisterInputData(
                username, email, password);

        this.userRegisterUseCaseInteractor.execute(registerInputData);
    }

    /**
     * Execute the switch to LoginView Use case.
     */
    public void switchToLoginView() {
        this.userRegisterUseCaseInteractor.switchToLoginView();
    }
}
