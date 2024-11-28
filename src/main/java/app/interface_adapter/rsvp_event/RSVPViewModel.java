package app.interface_adapter.rsvp_event;

import app.interface_adapter.ViewModel;

public class RSVPViewModel extends ViewModel<RSVPState> {
//    public static final String TITLE_LABEL = ""
    // There is no view for this, just a use case

    public RSVPViewModel() {
        super ("rsvpEvent");
        setState(new RSVPState());
    }
}
