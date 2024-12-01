package app.use_case.home;

/**
 * Interactor for the Home Use Case.
 * This class implements the business logic for navigating between different views
 * in the application from the home view. It interacts with the presenter to handle
 * transitions based on user actions.
 */
public class HomeInteractor implements HomeInputBoundary {

    private HomeOutputBoundary homePresenter;

    /**
     * Constructs a new {@link HomeInteractor}.
     * @param homePresenter The output boundary responsible for preparing the appropriate views.
     *                      Must not be {@code null}.
     */
    public HomeInteractor(HomeOutputBoundary homePresenter) {
        this.homePresenter = homePresenter;
    }

    /**
     * Executes the default logic for the home use case.
     * This method is currently a placeholder and may be used for additional logic in the future.
     */
    @Override
    public void execute() {
    }

    /**
     * Switches to the login view.
     * Notifies the presenter to prepare the login view.
     */
    @Override
    public void switchToLoginView() {
        homePresenter.switchToLoginView();
    }

    /**
     * Switches to the create event view.
     * Notifies the presenter to prepare the create event view.
     */
    @Override
    public void switchtoCreateEventView() {
        homePresenter.switchToCreateEventView();
    }

    /**
     * Switches to the filter event view.
     * Notifies the presenter to prepare the filter event view.
     */
    @Override
    public void switchToFilterEventView() {
        homePresenter.switchToFilterEventView();
    }

    /**
     * Switches to the RSVP view.
     * Notifies the presenter to prepare the view for RSVP events.
     */
    @Override
    public void switchToViewRSVPView() {
        homePresenter.switchToViewRSVPView();
    }

    /**
     * Switches to the view created events view.
     * Notifies the presenter to prepare the view for created events.
     */
    @Override
    public void switchToViewCreatedEventsView() {
        homePresenter.switchToViewCreatedEventsView();
    }

    /**
     * Switches to the modify event view.
     * Notifies the presenter to prepare the view for modifying events.
     */
    @Override
    public void switchToModifyEventView() {
        homePresenter.switchToModifyEventView();
    }
}
