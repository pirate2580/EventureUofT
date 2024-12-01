package app.interface_adapter.notify_users;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.notify_users.NotifyUserOutputBoundary;
import app.use_case.notify_users.NotifyUserOutputData;

/**
 * Presenter for the Notify User Use Case.
 * This class is responsible for preparing the success and failure views for the notify user operation.
 * It updates the view state and triggers necessary property change notifications to ensure the UI reflects
 * the outcome of the use case execution.
 */
public class NotifyUserPresenter implements NotifyUserOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final HomeViewModel homeViewModel;

    /**
     * Constructs a new {@link NotifyUserPresenter}.
     * @param viewManagerModel the model responsible for managing views and transitions. Must not be {@code null}.
     * @param homeViewModel    the model representing the state of the home view. Must not be {@code null}.
     */
    public NotifyUserPresenter(ViewManagerModel viewManagerModel,
                               HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homeViewModel = homeViewModel;
    }

    /**
     * Prepares the success view for the Notify User Use Case.
     * Updates the view manager to transition to the home view upon successful execution of the use case.
     * @param outputData the output data containing details of the successful notification operation.
     */
    @Override
    public void prepareSuccessView(NotifyUserOutputData outputData) {
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the Notify User Use Case.
     * Currently, this method does not implement any logic for failure handling. Future implementations
     * should update the view to display the provided error message.
     * @param errorMessage the error message describing the failure. Can be used to update the view with
     *                     relevant failure details.
     */
    @Override
    public void prepareFailView(String errorMessage) {

    }
}
