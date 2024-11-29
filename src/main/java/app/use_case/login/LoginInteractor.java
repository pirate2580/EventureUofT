package app.use_case.login;
import app.entity.User.User;

public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessObject, LoginOutputBoundary loginPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.loginPresenter = loginPresenter;
    }

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
        } else if (!user.verifyPassword(password)) {
            loginPresenter.prepareFailView("Incorrect password.");
        } else {
            LoginOutputData outputData = new LoginOutputData(user.getUsername(), true);
            loginPresenter.prepareSuccessView(outputData, "User logged in successfully.");
        }
    }
    @Override
    public void switchToRegisterView() {
        loginPresenter.switchToRegisterView();;
    }
}
