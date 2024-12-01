package app.use_case.view_rsvp;

/**
 * Output boundary for the View RSVP Use Case.
 * This interface defines the contract for preparing the views when viewing RSVP events.
 * It includes methods to handle the success scenario (displaying the RSVP events) and switching to the home view.
 */
public interface ViewRSVPOutputBoundary {

    /**
     * Prepares the success view for the View RSVP Use Case.
     * This method is called when the RSVP events are successfully retrieved. It communicates the results
     * of the operation to the presenter, which prepares the success view.
     * @param outputData The output data containing the list of RSVP events for the user. Must not be {@code null}.
     */
    void prepareSuccessView(ViewRSVPOutputData outputData);

    /**
     * Switches the view to the home screen.
     * This method is invoked when an action or failure leads to the need for the home view to be displayed.
     */
    void switchToHomeView();
}
