package app.interface_adapter.modify_event;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.home.HomeState;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.modify_event.ModifyEventOutputBoundary;
import app.use_case.modify_event.ModifyEventOutputData;

/**
 * Presenter for the Modify Event Use Case.
 * This class prepares the views for the Modify Event Use Case. It updates the
 * state of the view models and communicates with the ViewManagerModel to handle
 * transitions between views based on the success or failure of the use case.
 */
public class ModifyEventPresenter implements ModifyEventOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ModifyEventViewModel modifyEventViewModel;
    private final HomeViewModel homeViewModel;

    /**
     * Constructs a new {@link ModifyEventPresenter}.
     * @param viewManagerModel    The model managing transitions between views. Must not be {@code null}.
     * @param modifyEventViewModel The view model for the modify event view. Must not be {@code null}.
     * @param homeViewModel       The view model for the home view. Must not be {@code null}.
     */
    public ModifyEventPresenter(ViewManagerModel viewManagerModel,
                                ModifyEventViewModel modifyEventViewModel,
                                HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.modifyEventViewModel = modifyEventViewModel;
        this.homeViewModel = homeViewModel;
    }

    /**
     * Prepares the success view for the Event user use case.
     * This will probably just lead to the main screen of the application
     * whatever that ends up being
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(ModifyEventOutputData outputData) {
        final ModifyEventState modifyEventState = modifyEventViewModel.getState();
        final HomeState homeState = homeViewModel.getState();
        modifyEventState.setUsernameState(homeState.getUsernameState());
        this.modifyEventViewModel.firePropertyChanged();

        this.modifyEventViewModel.setState(modifyEventState);
        modifyEventViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the Event user use case.
     * In this case, the result will probably be that you stay in the Event
     * view but it tells you there is an error with what you have done
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final ModifyEventState modifyEventState = modifyEventViewModel.getState();
        modifyEventState.setCapacityError(errorMessage);
        modifyEventViewModel.firePropertyChanged();
    }

    @Override
    public void switchToHomeView() {
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
