package app.use_case.view_event;

import app.entity.Event.Event;

/**
 * Output data for the View Event Use Case.
 * This class encapsulates the data that is returned when a user successfully views an event. It contains
 * the event details that are passed to the presenter for display in the view.
 */
public class ViewEventOutputData {
    private final Event event;

    /**
     * Constructor for ViewEventOutputData.
     * Initializes the output data with the event details that will be presented to the user.
     * @param event The event to be displayed in the view. Must not be {@code null}.
     */
    public ViewEventOutputData(Event event) {
        this.event = event;
    }

    /**
     * Retrieves the event associated with this output data.
     * @return The event details to be displayed. Will never be {@code null}.
     */
    public Event getEvent() {
        return event;
    }
}
