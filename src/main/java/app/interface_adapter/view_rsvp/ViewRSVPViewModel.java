package app.interface_adapter.view_rsvp;

import app.interface_adapter.ViewModel;

/**
 * ViewModel for the View RSVP feature.
 * This class manages the state and logic for displaying RSVP details.
 * It extends the {@link ViewModel} class, utilizing {@link ViewRSVPState}
 * to store and manage the state for this feature.
 */
public class ViewRSVPViewModel extends ViewModel<ViewRSVPState> {

    /**
     * Constructs a new {@link ViewRSVPViewModel}.
     * Initializes the ViewModel with the view name "viewRSVP" and sets the initial
     * state to a new instance of {@link ViewRSVPState}.
     */
    public ViewRSVPViewModel() {
        super("viewRSVP");
        setState(new ViewRSVPState());
    }
}
