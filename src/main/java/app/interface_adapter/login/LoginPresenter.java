package app.interface_adapter.login;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.home.HomeState;
import app.interface_adapter.home.HomeViewModel;
import app.interface_adapter.register.RegisterViewModel;
import app.use_case.login.LoginOutputBoundary;
import app.use_case.login.LoginOutputData;

/**
 * Constructs a new {@link LoginPresenter}.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final HomeViewModel homeViewModel;
    private final RegisterViewModel registerViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoginViewModel loginViewModel,
                          RegisterViewModel registerViewModel,
                          HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.registerViewModel = registerViewModel;
        this.homeViewModel = homeViewModel;
    }

    /**
     * Prepares the success view for the Login Use Case.
     * Updates the home view's state with the username from the login state,
     * transitions to the home view, and notifies the view manager to reflect
     * these changes.
     * @param response the output data from the login use case containing login details.
     * @param s        additional string data for processing, if applicable.
     */
    @Override
    public void prepareSuccessView(LoginOutputData response, String s) {

        final HomeState homeState = homeViewModel.getState();
        final LoginState loginState = loginViewModel.getState();
        homeState.setUsernameState(loginState.getUsername());
        this.homeViewModel.firePropertyChanged();
        this.viewManagerModel.setState(homeViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsernameError(errorMessage);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToRegisterView() {
        viewManagerModel.setState(registerViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
