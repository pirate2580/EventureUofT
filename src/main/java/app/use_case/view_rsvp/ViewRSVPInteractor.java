package app.use_case.view_rsvp;

import java.util.List;

/**
 * Interactor for the View RSVP Use Case.
 * This class handles the business logic for retrieving RSVP events for a specific user.
 * It communicates with the data access object to fetch the list of events that the user has RSVPed to
 * and prepares the output to be presented to the user.
 */
public class ViewRSVPInteractor implements ViewRSVPInputBoundary {

    private final ViewRSVPDataAccessInterface rsvpDataAccessObject;
    private final ViewRSVPOutputBoundary viewRsvpEventPresenter;

    /**
     * Constructor for ViewRSVPInteractor.
     * Initializes the interactor with the data access object and output boundary. The data access
     * object is used to fetch the RSVP events, and the output boundary prepares the success or failure view.
     * @param rsvpDataAccessObject      The data access object to interact with the RSVP events data.
     * @param viewRsvpEventPresenter    The output boundary to prepare and present the success or failure view.
     */
    public ViewRSVPInteractor(ViewRSVPDataAccessInterface rsvpDataAccessObject,
                              ViewRSVPOutputBoundary viewRsvpEventPresenter) {
        this.rsvpDataAccessObject = rsvpDataAccessObject;
        this.viewRsvpEventPresenter = viewRsvpEventPresenter;
    }

    /**
     * Executes the View RSVP Use Case.
     * This method retrieves the list of events that the user has RSVPed to by using the provided
     * {@link ViewRSVPInputData} and fetching the relevant RSVP events. The output is then passed to
     * the output boundary to prepare the success view.
     * @param RSVPInputData The input data containing the user's username.
     */
    @Override
    public void execute(ViewRSVPInputData RSVPInputData) {
        List<String> viewRSVPEvents = rsvpDataAccessObject.getRSVPEvents(RSVPInputData.getUsername());
        final ViewRSVPOutputData viewRSVPOutputData = new ViewRSVPOutputData(viewRSVPEvents);
        viewRsvpEventPresenter.prepareSuccessView(viewRSVPOutputData);
    }

    /**
     * Switches the view to the home screen.
     * This method is invoked to navigate to the home view in case of a failure or user action.
     */
    @Override
    public void switchToHomeView() {
        viewRsvpEventPresenter.switchToHomeView();
    }
}
