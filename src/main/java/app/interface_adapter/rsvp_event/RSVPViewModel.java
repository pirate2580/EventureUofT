package app.interface_adapter.rsvp_event;

import app.interface_adapter.ViewModel;

/**
 * ViewModel for the RSVP Event feature.
 * This class manages the state for the RSVP process. While there is no dedicated
 * view for this feature, the ViewModel facilitates the management of state for
 * use cases related to RSVP operations.
 */
public class RSVPViewModel extends ViewModel<RSVPState> {
    // public static final String TITLE_LABEL = ""
    // There is no view for this, just a use case

    /**
     * Constructs a new {@link RSVPViewModel}.
     * Initializes the ViewModel with the view name "rsvpEvent" and sets the initial
     * state to a new instance of {@link RSVPState}.
     */
    public RSVPViewModel() {
        super("rsvpEvent");
        setState(new RSVPState());
    }
}
