package app.interface_adapter.login;

import app.interface_adapter.home.HomeState;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.login.LoginInputBoundary;
import app.interface_adapter.ViewManagerModel;

import app.use_case.login.LoginOutputBoundary;
import app.use_case.login.LoginOutputData;


public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final HomeViewModel homeViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoginViewModel loginViewModel,
                          HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.homeViewModel = homeViewModel;
    }

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
//        System.out.println("login error, not skibidi");
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsernameError(errorMessage);
        loginViewModel.firePropertyChanged();
    }
}
