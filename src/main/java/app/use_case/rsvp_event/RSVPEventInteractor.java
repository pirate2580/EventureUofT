package app.use_case.rsvp_event;

import app.entity.Event.Event;
import app.entity.User.User;

public class RSVPEventInteractor implements RSVPEventInputBoundary {
    private final RSVPEventUserDataAccessInterface rsvpEventDataAccessObject;
    private final RSVPEventOutputBoundary rsvpEventPresenter;

    public RSVPEventInteractor(RSVPEventUserDataAccessInterface rsvpEventDataAccessObject,
                               RSVPEventOutputBoundary rsvpEventPresenter) {
        this.rsvpEventDataAccessObject = rsvpEventDataAccessObject;
        this.rsvpEventPresenter = rsvpEventPresenter;
    }

    @Override
    public void execute(RSVPEventInputData rsvpEventInputData) {
        rsvpEventDataAccessObject.addUserToRSVPList(rsvpEventInputData.getUsername(), rsvpEventInputData.getEventId());
        rsvpEventPresenter.prepareSuccessView(new RSVPEventOutputData(rsvpEventInputData.getUsername(),
                rsvpEventInputData.getEventId()));
    }
}