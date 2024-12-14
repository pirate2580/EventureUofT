package app.interface_adapter.home;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.create_event.CreateEventState;
import app.interface_adapter.create_event.CreateEventViewModel;
import app.interface_adapter.filter_event.FilterEventState;
import app.interface_adapter.filter_event.FilterEventViewModel;
import app.interface_adapter.login.LoginState;
import app.interface_adapter.login.LoginViewModel;
import app.interface_adapter.modify_event.ModifyEventState;
import app.interface_adapter.modify_event.ModifyEventViewModel;
import app.interface_adapter.view_created_events.ViewCreatedEventsState;
import app.interface_adapter.view_created_events.ViewCreatedEventsViewModel;
import app.interface_adapter.view_rsvp.ViewRSVPState;
import app.interface_adapter.view_rsvp.ViewRSVPViewModel;
import app.use_case.home.HomeOutputBoundary;
import app.view.ViewRSVPView;

/**
 * Take output data and turn it into raw strings
 * or numbers to be displayed. Can be used to store information
 * needed for map markers, such as event names, descriptions, etc.
 * must send it to the view model
 * */
public class HomePresenter implements HomeOutputBoundary {
    // initialize all variables
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final CreateEventViewModel createEventViewModel;
    private final FilterEventViewModel filterEventViewModel;
    private final ViewRSVPViewModel viewRSVPViewModel;
    private final ViewCreatedEventsViewModel viewCreatedEventsViewModel;

    private final ModifyEventViewModel modifyEventViewModel;

    // set values
    public HomePresenter(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, CreateEventViewModel createEventViewModel, FilterEventViewModel filterEventViewModel, ViewRSVPViewModel viewRSVPViewModel, ViewCreatedEventsViewModel viewCreatedEventsViewModel
            , ModifyEventViewModel modifyEventViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.createEventViewModel = createEventViewModel;
        this.filterEventViewModel = filterEventViewModel;
        this.viewRSVPViewModel = viewRSVPViewModel;
        this.viewCreatedEventsViewModel = viewCreatedEventsViewModel;
        this.modifyEventViewModel = modifyEventViewModel;
    }

    /**
     * Function to switch from home view to create event view.
     * */
    @Override
    public void switchToCreateEventView() {
        // Get states
        final CreateEventState createEventState = createEventViewModel.getState();
        final LoginState loginState = loginViewModel.getState();
        createEventState.setUsernameState(loginState.getUsername());
        // Track the property
        this.createEventViewModel.firePropertyChanged();
        viewManagerModel.setState(createEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Function to switch from home view to log in view.
     * */
    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Function to switch from home view to filter event view.
     * */
    @Override
    public void switchToFilterEventView() {
        final FilterEventState filterEventState = filterEventViewModel.getState();
        final LoginState loginState = loginViewModel.getState();
        filterEventState.setUsernameState(loginState.getUsername());
        this.filterEventViewModel.firePropertyChanged();
        viewManagerModel.setState(filterEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Function to switch from home view to RSVP view.
     * */
    @Override
    public void switchToViewRSVPView() {
        final ViewRSVPState viewRSVPState = viewRSVPViewModel.getState();
        final LoginState loginState = loginViewModel.getState();
        viewRSVPState.setUsernameState(loginState.getUsername());
        this.viewRSVPViewModel.firePropertyChanged();
        viewManagerModel.setState(viewRSVPViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Function to switch from home view to view created events view.
     * */
    @Override
    public void switchToViewCreatedEventsView() {
        final ViewCreatedEventsState viewCreatedEventsState = viewCreatedEventsViewModel.getState();
        final LoginState loginState = loginViewModel.getState();
        viewCreatedEventsState.setUsernameState(loginState.getUsername());
        this.viewCreatedEventsViewModel.firePropertyChanged();

        viewManagerModel.setState(viewCreatedEventsViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Function to switch from home view to modify event view.
     * */
    @Override
    public void switchToModifyEventView() {
        final ModifyEventState modifyEventState = modifyEventViewModel.getState();
        final LoginState loginState = loginViewModel.getState();
        modifyEventState.setUsernameState(loginState.getUsername());
        this.modifyEventViewModel.firePropertyChanged();

        viewManagerModel.setState(modifyEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}