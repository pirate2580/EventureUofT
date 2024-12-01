package app.interface_adapter.register;

import app.interface_adapter.ViewModel;

/**
 * ViewModel for the Register view.
 * This class manages the state and UI labels for the registration feature.
 * It extends the {@link ViewModel} class with {@link RegisterState} as its state.
 * The ViewModel serves as a bridge between the view and the application's state,
 * ensuring that the UI reflects the current state of the registration process.
 */
public class RegisterViewModel extends ViewModel<RegisterState> {

    /** The title label for the registration view. */
    public static final String TITLE_LABEL = "Sign Up View";

    /** The label for the username input field. */
    public static final String USERNAME_LABEL = "Choose username";

    /** The label for the email input field. */
    public static final String EMAIL_LABEL = "Enter your UofT email";

    /** The label for the password input field. */
    public static final String PASSWORD_LABEL = "Choose password";

    /** The label for the register button. */
    public static final String REGISTER_BUTTON_LABEL = "Sign Up";

    /**
     * Constructs a new {@link RegisterViewModel}.
     * Initializes the ViewModel with the view name "register" and sets the initial state
     * to a new instance of {@link RegisterState}.
     */
    public RegisterViewModel() {
        super("register");
        setState(new RegisterState());
    }
}
