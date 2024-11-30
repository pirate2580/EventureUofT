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
        if (rsvpEventInputData.getUsername() == null) {
            rsvpEventPresenter.prepareFailView("Username cannot be null.");
            return;
        }

        if (rsvpEventInputData.getEventId() == null) {
            rsvpEventPresenter.prepareFailView("Event ID cannot be null.");
            return;
        }

        rsvpEventDataAccessObject.addUserToRSVPList(rsvpEventInputData.getUsername(), rsvpEventInputData.getEventId());
        rsvpEventPresenter.prepareSuccessView(new RSVPEventOutputData(rsvpEventInputData.getUsername(),
                rsvpEventInputData.getEventId()));
    }
}