package app.interface_adapter.register;

import app.interface_adapter.ViewModel;

public class RegisterViewModel extends ViewModel <RegisterState> {
    public static final String TITLE_LABEL = "Sign Up View";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String EMAIL_LABEL = "Enter your UofT email";
    public static final String PASSWORD_LABEL = "Choose password";
    // public static final String REPEAT_PASSWORD_LABEL = "Enter password again";

    public static final String REGISTER_BUTTON_LABEL = "Sign Up";
    // public static final String CANCEL_BUTTON_LABEL = "Cancel";

    // public static final String TO_LOGIN_BUTTON_LABEL = "Go to Login";

    public RegisterViewModel() {
        super("register");
        setState(new RegisterState());
    }

}
