package app.interface_adapter.login;

import app.interface_adapter.ViewModel;

/**
 * ViewModel for the Login view.
 * This class manages the state and UI labels for the login feature.
 * It extends the {@link ViewModel} class with {@link LoginState} as its state.
 */
public class LoginViewModel extends ViewModel<LoginState> {

    /** The title label for the login view. */
    public static final String TITLE_LABEL = "Log In View";

    /** The label for the username input field. */
    public static final String USERNAME_LABEL = "Enter username";

    /** The label for the password input field. */
    public static final String PASSWORD_LABEL = "Enter password";

    /** The label for the login button. */
    public static final String LOGIN_BUTTON_LABEL = "Log In";

    /**
     * Constructs a new {@link LoginViewModel}.
     * Initializes the ViewModel with the view name "login" and sets the initial state
     * to a new instance of {@link LoginState}.
     */
    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }

}
