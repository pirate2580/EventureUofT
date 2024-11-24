package app.interface_adapter.login;

import app.use_case.login.LoginInputBoundary;
import app.interface_adapter.ViewManagerModel;

import app.use_case.login.LoginOutputBoundary;
import app.use_case.login.LoginOutputData;


public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagereModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoginViewModel loginViewModel) {
        this.viewManagereModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response, String s) {
        // TODO: implement switch to another view
    }


    @Override
    public void prepareFailView(String errorMessage) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername(errorMessage);                      // error occurs when username or password wrong
        loginViewModel.firePropertyChanged();
    }
}
