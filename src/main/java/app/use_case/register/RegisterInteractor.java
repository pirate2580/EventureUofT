package app.use_case.register;

import app.entity.User.User;
import app.entity.User.UserFactory;

/**
 * The Register Interactor
 * The Register Interactor handles the input
 * If a user with the inputted username exists, then get the userPresenter to prepare a fail view on frontend
 * Do a similar thing for other errors
 * Otherwise, interact with the database through hte userDAO to save the user and prepare a success view to the
 * front end.
 * NOTE: userPresenter is just the outputBoundary class
 * Note: RegisterInteractor is an implementation of the inputBoundary interface
 */
public class RegisterInteractor implements RegisterInputBoundary {

    private final RegisterUserDataAccessInterface userDataAccessObject;
    private final RegisterOutputBoundary userPresenter;
    private final UserFactory userFactory;

    /**
     * Constructs a new RegisterInteractor.
     * @param registerUserDataAccessInterface The data access object for interacting with user data storage.
     * @param registerOutputBoundary The presenter for preparing success or failure views.
     * @param userFactory The factory for creating User objects.
     */
    public RegisterInteractor(RegisterUserDataAccessInterface registerUserDataAccessInterface,
                              RegisterOutputBoundary registerOutputBoundary,
                              UserFactory userFactory) {

        this.userDataAccessObject = registerUserDataAccessInterface;
        this.userPresenter = registerOutputBoundary;
        this.userFactory = userFactory;
    }

    /**
     * Executes the user registration process.
     * Verifies that the username does not already exist, creates a new user,
     * and saves the user data to the database.
     * If successful, prepares a success view; if there is an error, prepares a failure view.
     * @param registerInputData The data entered by the user for registration, such as username, email, and password.
     */
    @Override
    public void execute(RegisterInputData registerInputData) {
        // Check if input data is null
        if (registerInputData == null) {
            userPresenter.prepareFailView("Register input data cannot be null.");
            return;
        }

        // Check if the username already exists
        if (userDataAccessObject.existsByUsername(registerInputData.getUsername())) {
            userPresenter.prepareFailView("Username already exists.");
        }
        else {
            // Create a new user using the user factory
            final User user = userFactory.create(registerInputData.getUsername(),
                    registerInputData.getEmail(),
                    registerInputData.getPassword());
            // Save the user to the database
            userDataAccessObject.save(user);

            // Prepare success view with the user's username and a success message
            final RegisterOutputData registerOutputData = new RegisterOutputData(user.getUsername(), false);
            userPresenter.prepareSuccessView(registerOutputData, "User created successfully! Please log in.");
        }
    }

    /**
     * Switches the view to the login view.
     */
    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
