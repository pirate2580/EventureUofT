package app.interface_adapter.view_event;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.home.HomeState;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.view_event.ViewEventOutputBoundary;
import app.use_case.view_event.ViewEventOutputData;

/**
 * Presenter for the View Event Use Case.
 * This class handles the presentation logic for the View Event feature.
 * It updates the view state with data provided by the use case and manages
 * transitions between the event details view and the home view.
 */
public class ViewEventPresenter implements ViewEventOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ViewEventViewModel viewEventViewModel;
    private final HomeViewModel homeViewModel;

    /**
     * Constructs a new {@link ViewEventPresenter}.
     * @param viewManagerModel   the model responsible for managing view transitions. Must not be {@code null}.
     * @param viewEventViewModel the ViewModel representing the state of the event view. Must not be {@code null}.
     * @param homeViewModel      the ViewModel representing the state of the home view. Must not be {@code null}.
     */
    public ViewEventPresenter(ViewManagerModel viewManagerModel,
                              ViewEventViewModel viewEventViewModel,
                              HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewEventViewModel = viewEventViewModel;
        this.homeViewModel = homeViewModel;
    }

    /**
     * Prepares the success view for the View Event Use Case.
     * Updates the state of the event view with the event details and username.
     * Notifies the ViewModel to reflect these changes in the UI.
     * @param outputData the output data containing the event details and additional information.
     */
    @Override
    public void prepareSuccessView(ViewEventOutputData outputData) {
        final ViewEventState viewEventState = viewEventViewModel.getState();
        final HomeState homeState = homeViewModel.getState();
        viewEventState.setUsernameState(homeState.getUsernameState());
        this.viewEventViewModel.firePropertyChanged();

        viewManagerModel.setState(viewEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        viewEventState.setViewEvent(outputData.getEvent());
        this.viewEventViewModel.setState(viewEventState);
        viewEventViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the View Event Use Case.
     * Currently, this method does not implement any failure handling logic.
     * Future implementations should update the view with the provided error message.
     * @param errorMessage the error message describing the failure.
     *                     Can be used to update the view with relevant details.
     */
    @Override
    public void prepareFailView(String errorMessage) {
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
