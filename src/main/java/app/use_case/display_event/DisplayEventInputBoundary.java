package app.use_case.display_event;

import app.entity.Event.CommonEvent;
import app.use_case.create_event.EventInputData;

import java.util.ArrayList;

/**
 * Input Boundary for the actions relating to creating an event.
 */
public interface DisplayEventInputBoundary {

    /**
     * Execute the event creation use case.
     */
    ArrayList<CommonEvent> execute();

    /**
     * Executes the switch to the login view use case once
     * event created.
     * TODO note this idea of switching to different usecases after a certain
     * use case outcome
     */
}