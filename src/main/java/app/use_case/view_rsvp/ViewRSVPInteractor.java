package app.use_case.view_rsvp;

import app.use_case.rsvp_event.RSVPEventInputData;
import app.entity.Event.Event;
import java.util.List;

public class ViewRSVPInteractor implements ViewRSVPInputBoundary{

    private final ViewRSVPDataAccessInterface rsvpDataAccessObject;
    private final ViewRSVPOutputBoundary viewRsvpEventPresenter;

    public ViewRSVPInteractor(ViewRSVPDataAccessInterface rsvpDataAccessObject,
                              ViewRSVPOutputBoundary viewRsvpEventPresenter) {
        this.rsvpDataAccessObject = rsvpDataAccessObject;
        this.viewRsvpEventPresenter = viewRsvpEventPresenter;
    }


    @Override
    public void execute(ViewRSVPInputData RSVPInputData) {
        List<String> viewRSVPEvents = rsvpDataAccessObject.getRSVPEvents(RSVPInputData.getUsername());
        final ViewRSVPOutputData viewRSVPOutputData = new ViewRSVPOutputData(viewRSVPEvents);
        viewRsvpEventPresenter.prepareSuccessView(viewRSVPOutputData);
    }

    @Override
    public void switchToHomeView() {
        viewRsvpEventPresenter.switchToHomeView();
    }
}
