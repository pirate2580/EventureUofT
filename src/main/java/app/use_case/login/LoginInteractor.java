package app.use_case.login;

import app.entity.User.User;

/**
 * Interactor for the Login Use Case.
 * This class handles the business logic for user login, including validating
 * input credentials, checking user existence, and verifying the password.
 * It interacts with the data access object (DAO) to retrieve user data and
 * the presenter to prepare the output view.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    /**
     * Constructs a new {@link LoginInteractor}.
     * @param userDataAccessObject The DAO for accessing user data. Must not be {@code null}.
     * @param loginPresenter       The output boundary for preparing views. Must not be {@code null}.
     */
    public LoginInteractor(LoginUserDataAccessInterface userDataAccessObject, LoginOutputBoundary loginPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.loginPresenter = loginPresenter;
    }

    /**
     * Executes the Login Use Case.
     * This method validates the provided login credentials, checks if the user exists,
     * and verifies the password. It communicates success or failure to the presenter.
     * @param loginInputData The input data containing the username and password. Must not be {@code null}.
     * @throws NullPointerException If {@code loginInputData} is {@code null}.
     */
    @Override
    public void execute(LoginInputData loginInputData) {
        if (loginInputData == null) {
            throw new NullPointerException("LoginInputData cannot be null");
        }

        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();

        if (username == null || password == null) {
            loginPresenter.prepareFailView("Username or password cannot be empty.");
            return;
        }
        User user = userDataAccessObject.findUserByUsername(loginInputData.getUsername());
        if (user == null) {
            loginPresenter.prepareFailView("Username does not exist.");
        }
        else if (!user.verifyPassword(password)) {
            loginPresenter.prepareFailView("Incorrect password.");
        }
        else {
            LoginOutputData outputData = new LoginOutputData(user.getUsername(), true);
            loginPresenter.prepareSuccessView(outputData, "User logged in successfully.");
        }
    }

    /**
     * Switches the application to the register view.
     * This method is invoked to navigate the user to the register screen.
     */
    @Override
    public void switchToRegisterView() {
        loginPresenter.switchToRegisterView();
    }
}
