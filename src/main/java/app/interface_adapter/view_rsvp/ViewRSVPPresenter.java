package app.interface_adapter.view_rsvp;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.home.HomeState;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.view_rsvp.ViewRSVPOutputBoundary;
import app.use_case.view_rsvp.ViewRSVPOutputData;

public class ViewRSVPPresenter implements  ViewRSVPOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ViewRSVPViewModel viewRSVPViewModel;
    private final HomeViewModel homeViewModel;

    public ViewRSVPPresenter(ViewManagerModel viewManagerModel,
                             ViewRSVPViewModel viewRSVPViewModel,
                             HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewRSVPViewModel = viewRSVPViewModel;
        this.homeViewModel = homeViewModel;
    }


    @Override
    public void prepareSuccessView(ViewRSVPOutputData outputData) {
        final ViewRSVPState viewRSVPState = viewRSVPViewModel.getState();
        final HomeState homeState = homeViewModel.getState();
        viewRSVPState.setUsernameState(homeState.getUsernameState());
        this.viewRSVPViewModel.firePropertyChanged();

        viewRSVPState.setViewRSVP(outputData.getRSVPEvents());
        this.viewRSVPViewModel.setState(viewRSVPState);
        viewRSVPViewModel.firePropertyChanged();
    }

    @Override
    public void switchToHomeView() {
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
