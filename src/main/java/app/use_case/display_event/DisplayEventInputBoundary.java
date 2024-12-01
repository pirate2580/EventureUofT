package app.use_case.display_event;

import java.util.ArrayList;

/**
 * Input Boundary for the actions relating to creating an event.
 */
public interface DisplayEventInputBoundary {
    /**
     * Executes the Display Event use case.
     * Retrieves details of all events and prepares them for display.
     * @return A nested {@link ArrayList} containing event details.
     *         Each inner {@link ArrayList} represents a single event's properties.
     */
    ArrayList<ArrayList<Object>> execute();
}
