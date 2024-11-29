package app.interface_adapter.rsvp_event;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.rsvp_event.RSVPEventOutputBoundary;
import app.use_case.rsvp_event.RSVPEventOutputData;
import app.view.ViewManager;


public class RSVPPresenter implements  RSVPEventOutputBoundary{
    private final  ViewManagerModel viewManagerModel;
//    private final RSVPViewModel rsvpEventViewModel;
    private final HomeViewModel homeViewModel;

    public RSVPPresenter(ViewManagerModel viewManagerModel,
                         HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
//        this.rsvpEventViewModel = rsvpEventViewModel;
        this.homeViewModel = homeViewModel;
    }

    @Override
    public void prepareSuccessView(RSVPEventOutputData outputData) {
        // move to home view and then ping saying successfully rvsped event
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
