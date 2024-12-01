package app.interface_adapter.view_rsvp;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.home.HomeState;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.view_rsvp.ViewRSVPOutputBoundary;
import app.use_case.view_rsvp.ViewRSVPOutputData;

/**
 * Presenter for the View RSVP Use Case.
 * This class handles the presentation logic for the View RSVP feature.
 * It updates the view state with RSVP data provided by the use case and
 * manages transitions between the RSVP view and the home view.
 */
public class ViewRSVPPresenter implements ViewRSVPOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ViewRSVPViewModel viewRSVPViewModel;
    private final HomeViewModel homeViewModel;

    /**
     * Constructs a new {@link ViewRSVPPresenter}.
     * @param viewManagerModel   the model responsible for managing view transitions. Must not be {@code null}.
     * @param viewRSVPViewModel  the ViewModel representing the state of the RSVP view. Must not be {@code null}.
     * @param homeViewModel      the ViewModel representing the state of the home view. Must not be {@code null}.
     */
    public ViewRSVPPresenter(ViewManagerModel viewManagerModel,
                             ViewRSVPViewModel viewRSVPViewModel,
                             HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewRSVPViewModel = viewRSVPViewModel;
        this.homeViewModel = homeViewModel;
    }

    /**
     * Prepares the success view for the View RSVP Use Case.
     * Updates the state of the RSVP view with the username and RSVP event data
     * retrieved from the use case output. Notifies the ViewModel to reflect
     * these changes in the UI.
     * @param outputData the output data containing RSVP event details.
     */
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

    /**
     * Switches the application to the home view.
     * Updates the view manager to transition to the home view and triggers
     * the necessary property change notifications.
     */
    @Override
    public void switchToHomeView() {
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
