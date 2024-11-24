package app.interface_adapter.register;

import app.interface_adapter.ViewModel;

public class RegisterViewModel extends ViewModel <RegisterState> {
    public static final String TITLE_LABEL = "Sign Up View";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String EMAIL_LABEL = "Enter your UofT email";
    public static final String PASSWORD_LABEL = "Choose password";

    public static final String REGISTER_BUTTON_LABEL = "Sign Up";

    public RegisterViewModel() {
        super("register");
        setState(new RegisterState());
    }

}
