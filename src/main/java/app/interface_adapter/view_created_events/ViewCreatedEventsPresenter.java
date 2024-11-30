package app.interface_adapter.view_created_events;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.home.HomeState;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.view_created_events.ViewCreatedOutputBoundary;
import app.use_case.view_created_events.ViewCreatedOutputData;

public class ViewCreatedEventsPresenter implements  ViewCreatedOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ViewCreatedEventsViewModel viewCreatedEventsViewModel;
    private final HomeViewModel homeViewModel;

    public ViewCreatedEventsPresenter(ViewManagerModel viewManagerModel,
                                      ViewCreatedEventsViewModel viewCreatedEventsViewModel,
                                      HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewCreatedEventsViewModel = viewCreatedEventsViewModel;
        this.homeViewModel = homeViewModel;
    }

    @Override
    public void prepareSuccessView(ViewCreatedOutputData outputData) {
        final ViewCreatedEventsState viewCreatedEventsState = viewCreatedEventsViewModel.getState();
        final HomeState homeState = homeViewModel.getState();
        viewCreatedEventsState.setUsernameState(homeState.getUsernameState());
        this.viewCreatedEventsViewModel.firePropertyChanged();

        viewCreatedEventsState.setCreatedEvents(outputData.getCreatedEvents());
        this.viewCreatedEventsViewModel.setState(viewCreatedEventsState);
        viewCreatedEventsViewModel.firePropertyChanged();
    }

    @Override
    public void switchToHomeView() {
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
