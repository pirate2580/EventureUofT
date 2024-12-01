package app.interface_adapter.home;

import app.use_case.home.HomeInputBoundary;
/**
 * Convert raw user data to something useful, and then store it in
 * the input data or input boundary.
 * AFAIK don't need anything for the home view specifically
 * create an input data object containing that info.
 * */

public class HomeController {

    private final HomeInputBoundary homeUseCaseInteractor;

    /**
     * Constructs a new {@link HomeController}.
     *
     * @param homeUseCaseInteractor the interactor responsible for handling navigation actions.
     *                               Must not be {@code null}.
     */
    public HomeController(HomeInputBoundary homeUseCaseInteractor) {
        this.homeUseCaseInteractor = homeUseCaseInteractor;
    }

    /**
     * Switches the application to the login view.
     */
    public void switchToLoginView() {
        this.homeUseCaseInteractor.switchToLoginView();
    }

    /**
     * Switches the application to the create event view.
     */
    public void switchToCreateEventView() {
        this.homeUseCaseInteractor.switchtoCreateEventView();
    }

    /**
     * Switches the application to the filter event view.
     */
    public void switchToFilterEventView() {
        this.homeUseCaseInteractor.switchToFilterEventView();
    }

    /**
     * Switches the application to the RSVP view.
     */
    public void switchToViewRSVPView() {
        this.homeUseCaseInteractor.switchToViewRSVPView();
    }

    /**
     * Switches the application to the view created events view.
     */
    public void switchToViewCreatedEventsView() {
        this.homeUseCaseInteractor.switchToViewCreatedEventsView();
    }
}
