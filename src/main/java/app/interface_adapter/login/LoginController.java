package app.interface_adapter.login;

import app.use_case.login.LoginInputBoundary;
import app.use_case.login.LoginInputData;
import app.use_case.login.LoginOutputData;

public class LoginController {
    private final LoginInputBoundary userLoginUseCaseInteractor;
    public LoginController(LoginInputBoundary userLoginUseCaseInteractor) {
        this.userLoginUseCaseInteractor = userLoginUseCaseInteractor;
    }

    public void execute(String username, String password) {
        final LoginInputData loginInputData = new LoginInputData(username, password);
        //this.userLoginUseCaseInteractor.execute(loginInputData);
    }
}
