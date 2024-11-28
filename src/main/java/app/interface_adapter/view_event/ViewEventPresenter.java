package app.interface_adapter.view_event;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.home.HomeState;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.view_event.ViewEventOutputBoundary;
import app.use_case.view_event.ViewEventOutputData;

public class ViewEventPresenter implements ViewEventOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ViewEventViewModel viewEventViewModel;
    private final HomeViewModel homeViewModel;

    public ViewEventPresenter(ViewManagerModel viewManagerModel,
                              ViewEventViewModel viewEventViewModel,
                              HomeViewModel homeViewModel){
        this.viewManagerModel = viewManagerModel;
        this.viewEventViewModel = viewEventViewModel;
        this.homeViewModel = homeViewModel;
    }

    @Override
    public void prepareSuccessView(ViewEventOutputData outputData) {
        final ViewEventState viewEventState = viewEventViewModel.getState();
        final HomeState homeState = homeViewModel.getState();
        viewEventState.setUsernameState(homeState.getUsernameState());
        this.viewEventViewModel.firePropertyChanged();;

        viewManagerModel.setState(viewEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

//        System.out.println("sigma patrick bateman clown");
//        System.out.println(viewEventState.getUsernameState());

        viewEventState.setViewEvent(outputData.getEvent());
        this.viewEventViewModel.setState(viewEventState);
        viewEventViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // unimplemented
    }

    @Override
    public void switchToHomeView(){

        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
