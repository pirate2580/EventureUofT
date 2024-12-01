package app.interface_adapter.display_event;

import java.util.ArrayList;

import app.use_case.display_event.DisplayEventInputBoundary;

/**
 * Controller for the Event Use Case.
 */
public class DisplayEventController {

    private final DisplayEventInputBoundary displayEventInteractor;

    public DisplayEventController(DisplayEventInputBoundary displayEventInteractor) {
        this.displayEventInteractor = displayEventInteractor;
    }

    /**
     * Executes the display event use case and retrieves event details.
     * @return a nested {@link ArrayList} containing event details. Each inner list represents an event,
     *         with its elements containing event-specific information.
     */
    public ArrayList<ArrayList<Object>> execute() {
        return this.displayEventInteractor.execute();
    }

}
