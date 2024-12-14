package app.use_case.view_rsvp;

/**
 * Input Boundary for the View RSVP Use Case.
 * This interface defines the contract for handling the actions related to viewing the user's RSVP events.
 * It includes methods to execute the RSVP viewing process and to switch to the home view.
 */
public interface ViewRSVPInputBoundary {

    /**
     * Executes the View RSVP use case, retrieving the events that the user has RSVP'd to.
     * This method is invoked to initiate the process of viewing the events that the specified user has RSVPed to.
     * @param RSVPInputData The data containing the username for which to retrieve the RSVP events.
     */
    void execute(ViewRSVPInputData RSVPInputData);

    /**
     * Switches to the home view.
     * This method is used to switch the application back to the home view once the current RSVP process is completed.
     */
    void switchToHomeView();
}
