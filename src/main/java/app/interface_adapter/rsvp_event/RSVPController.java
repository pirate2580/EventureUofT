package app.interface_adapter.rsvp_event;

import app.use_case.rsvp_event.RSVPEventInputBoundary;
import app.use_case.rsvp_event.RSVPEventInputData;

/**
 * Controller for the RSVP Event Use Case.
 * This class handles the input for RSVP actions, preparing the necessary data
 * and delegating the logic to the appropriate use case interactor.
 */
public class RSVPController {
    private final RSVPEventInputBoundary rsvpEventUseCaseInteractor;

    /**
     * Constructs a new {@link RSVPController}.
     * @param rsvpEventUseCaseInteractor the interactor responsible for executing the RSVP event use case.
     *                                    Must not be {@code null}.
     */
    public RSVPController(RSVPEventInputBoundary rsvpEventUseCaseInteractor) {
        this.rsvpEventUseCaseInteractor = rsvpEventUseCaseInteractor;
    }

    /**
     * Executes the RSVP Event Use Case.
     * Prepares the input data with the provided username and event name, and
     * passes it to the use case interactor for processing.
     * @param username  the username of the user RSVPing to the event. Must not be {@code null}.
     * @param eventName the name of the event the user wants to RSVP to. Must not be {@code null}.
     */
    public void execute(String username, String eventName) {
        final RSVPEventInputData rsvpEventInputData = new RSVPEventInputData(username, eventName);
        this.rsvpEventUseCaseInteractor.execute(rsvpEventInputData);
    }
}
