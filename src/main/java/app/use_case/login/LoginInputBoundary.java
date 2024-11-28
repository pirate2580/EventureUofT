package app.use_case.login;

/**
 * Input Boundary for the actions relating to login.
 */
public interface LoginInputBoundary {
    void execute(LoginInputData loginInputData);
    void switchToRegisterView();
}
