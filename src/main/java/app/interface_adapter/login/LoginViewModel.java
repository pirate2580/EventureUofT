package app.interface_adapter.login;

import app.interface_adapter.ViewModel;

public class LoginViewModel extends ViewModel <LoginState> {
    public static final String TITLE_LABEL = "Log In View";
    public static final String USERNAME_LABEL = "Enter username";
    public static final String PASSWORD_LABEL = "Enter password";

    public static final String LOGIN_BUTTON_LABEL = "Log In";

    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }

}
