package app.interface_adapter.notify_users;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.notify_users.NotifyUserOutputBoundary;
import app.use_case.notify_users.NotifyUserOutputData;
import app.view.ViewManager;


public class NotifyUserPresenter implements NotifyUserOutputBoundary{
    private final ViewManagerModel viewManagerModel;
    private final HomeViewModel homeViewModel;

    public NotifyUserPresenter(ViewManagerModel viewManagerModel,
                               HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homeViewModel = homeViewModel;
    }
    @Override
    public void prepareSuccessView(NotifyUserOutputData outputData) {
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
