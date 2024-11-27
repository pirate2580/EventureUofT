package app.interface_adapter.home;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.create_event.CreateEventState;
import app.interface_adapter.create_event.CreateEventViewModel;
import app.interface_adapter.filter_event.FilterEventState;
import app.interface_adapter.filter_event.FilterEventViewModel;
import app.interface_adapter.login.LoginState;
import app.interface_adapter.login.LoginViewModel;
import app.use_case.home.HomeOutputBoundary;

/**
 * Take output data and turn it into raw strings
 * or numbers to be displayed. Can be used to store information
 * needed for map markers, such as event names, descriptions, etc.
 * must send it to the view model (??) not sure, according to
 * clean architecture diagram it does that, but imo it would
 * make more sense to send it to the home view.
 * */
public class HomePresenter implements HomeOutputBoundary {
    //TODO: Take output data and turn it into raw strings to store info

    //TODO: Send info to view model(?)

    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final CreateEventViewModel createEventViewModel;
    private final FilterEventViewModel filterEventViewModel;

    public HomePresenter(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, CreateEventViewModel createEventViewModel, FilterEventViewModel filterEventViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.createEventViewModel = createEventViewModel;
        this.filterEventViewModel = filterEventViewModel;
    }

    @Override
    public void switchToCreateEventView() {
        final CreateEventState createEventState = createEventViewModel.getState();
        final LoginState loginState = loginViewModel.getState();
        createEventState.setUsernameState(loginState.getUsername());
        this.createEventViewModel.firePropertyChanged();
        viewManagerModel.setState(createEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToFilterEventView() {
        final FilterEventState filterEventState = filterEventViewModel.getState();
        final LoginState loginState = loginViewModel.getState();
        filterEventState.setUsernameState(loginState.getUsername());
        this.filterEventViewModel.firePropertyChanged();
        viewManagerModel.setState(filterEventViewModel.getViewName());
        System.out.println(filterEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }



}
