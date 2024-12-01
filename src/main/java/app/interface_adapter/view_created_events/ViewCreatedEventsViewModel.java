package app.interface_adapter.view_created_events;

import app.interface_adapter.ViewModel;

/**
 * ViewModel for the View Created Events feature.
 * This class manages the state and logic for displaying the events created by a user.
 * It extends the {@link ViewModel} class, utilizing {@link ViewCreatedEventsState} to store
 * and manage the state for this feature.
 */
public class ViewCreatedEventsViewModel extends ViewModel<ViewCreatedEventsState> {

    /**
     * Constructs a new {@link ViewCreatedEventsViewModel}.
     * Initializes the ViewModel with the view name "viewCreated" and sets the initial
     * state to a new instance of {@link ViewCreatedEventsState}.
     */
    public ViewCreatedEventsViewModel() {
        super("viewCreated");
        setState(new ViewCreatedEventsState());
    }
}
