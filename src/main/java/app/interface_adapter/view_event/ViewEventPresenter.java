package app.interface_adapter.view_event;

import app.interface_adapter.ViewManagerModel;
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
//        System.out.println("I'm Mr. Krabs, I like money");
        viewManagerModel.setState(viewEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        viewEventState.setViewEvent(outputData.getEvent());
        this.viewEventViewModel.setState(viewEventState);

        System.out.println("Current state in ViewManagerModel: " + viewManagerModel.getState());
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
