package app.use_case.home;

/**
 * Output Boundary for the Home Use Case.
 * This interface defines the contract for preparing views related to the home use case.
 * It provides methods for switching between different views from the home screen.
 */
public interface HomeOutputBoundary {

    /**
     * Switches the application to the create event view.
     * This method is invoked to navigate the user to the create event screen.
     */
    void switchToCreateEventView();

    /**
     * Switches the application to the login view.
     * This method is invoked to navigate the user to the login screen.
     */
    void switchToLoginView();

    /**
     * Switches the application to the filter event view.
     * This method is invoked to navigate the user to the filter event screen.
     */
    void switchToFilterEventView();

    /**
     * Switches the application to the view RSVP view.
     * This method is invoked to navigate the user to the RSVP events screen.
     */
    void switchToViewRSVPView();

    /**
     * Switches the application to the view created events view.
     * This method is invoked to navigate the user to the screen displaying events
     * created by the user.
     */
    void switchToViewCreatedEventsView();
}
