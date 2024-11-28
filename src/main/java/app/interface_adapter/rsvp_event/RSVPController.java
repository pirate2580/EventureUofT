package app.interface_adapter.rsvp_event;

import app.use_case.rsvp_event.RSVPEventInputBoundary;
import app.use_case.rsvp_event.RSVPEventInputData;

public class RSVPController {
    private final RSVPEventInputBoundary rsvpEventUseCaseInteractor;

    public RSVPController(RSVPEventInputBoundary rsvpEventUseCaseInteractor) {
        this.rsvpEventUseCaseInteractor = rsvpEventUseCaseInteractor;
    }

    public void execute(String username, String eventName) {
        final RSVPEventInputData rsvpEventInputData = new RSVPEventInputData(username, eventName);
        this.rsvpEventUseCaseInteractor.execute(rsvpEventInputData);
    }
}
