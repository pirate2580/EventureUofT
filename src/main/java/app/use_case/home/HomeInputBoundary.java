package app.use_case.home;

/**
 * Input Boundary for the Home Use Case.
 * This interface defines the contract for handling actions related to the home screen,
 * including navigation to other views and executing home-specific logic.
 */
public interface HomeInputBoundary {

    /**
     * Executes the default action for the Home Use Case.
     * This method is typically invoked when the home screen is loaded or requires
     * initialization.
     */
    void execute();

    /**
     * Switches the application to the login view.
     * This method is invoked to navigate the user to the login screen.
     */
    void switchToLoginView();

    /**
     * Switches the application to the create event view.
     * This method is invoked to navigate the user to the create event screen.
     */
    void switchtoCreateEventView();

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
