package app.interface_adapter.view_event;

import app.interface_adapter.ViewModel;

/**
 * ViewModel for the View Event feature.
 * This class manages the state and logic for displaying the details of a specific event.
 * It extends the {@link ViewModel} class, utilizing {@link ViewEventState} to store
 * and manage the state for this feature.
 */
public class ViewEventViewModel extends ViewModel<ViewEventState> {
    public static final String TITLE_LABEL = "View Event";

    /**
     * Constructs a new {@link ViewEventViewModel}.
     * Initializes the ViewModel with the view name "viewEvent" and sets the initial
     * state to a new instance of {@link ViewEventState}.
     */
    public ViewEventViewModel() {
        super("viewEvent");
        setState(new ViewEventState());
    }
}
