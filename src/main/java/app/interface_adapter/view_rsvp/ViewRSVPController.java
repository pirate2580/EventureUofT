package app.interface_adapter.view_rsvp;

import app.use_case.view_rsvp.ViewRSVPInputBoundary;
import app.use_case.view_rsvp.ViewRSVPInputData;

/**
 * Controller for the View RSVP Use Case.
 * This class handles user input for viewing RSVP details. It prepares
 * the input data and delegates execution to the appropriate use case interactor.
 * Additionally, it provides functionality to transition back to the home view.
 */
public class ViewRSVPController {
    private final ViewRSVPInputBoundary viewRSVPUseCaseInteractor;

    /**
     * Constructs a new {@link ViewRSVPController}.
     * @param viewRSVPUseCaseInteractor the interactor responsible for handling the View RSVP use case.
     *                                  Must not be {@code null}.
     */
    public ViewRSVPController(ViewRSVPInputBoundary viewRSVPUseCaseInteractor) {
        this.viewRSVPUseCaseInteractor = viewRSVPUseCaseInteractor;
    }

    /**
     * Executes the View RSVP Use Case.
     * Prepares the input data with the provided username and delegates the processing
     * to the use case interactor.
     * @param username the username of the user whose RSVP details are to be viewed.
     *                 Must not be {@code null}.
     */
    public void execute(String username) {
        final ViewRSVPInputData viewRSVPInputData = new ViewRSVPInputData(username);
        this.viewRSVPUseCaseInteractor.execute(viewRSVPInputData);
    }

    /**
     * Switches the application to the home view.
     * Delegates the view transition logic to the use case interactor.
     */
    public void switchToHomeView() {
        this.viewRSVPUseCaseInteractor.switchToHomeView();
    }

}
