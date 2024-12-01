package app.use_case.home;

/**
 * Interactor for the Home Use Case.
 * This class implements the business logic for the home use case. It acts as a mediator
 * between the input boundary and the output boundary by handling navigation requests
 * from the home view and forwarding them to the presenter.
 */
public class HomeInteractor implements HomeInputBoundary {
    private final HomeOutputBoundary homePresenter;

    /**
     * Constructs a new {@link HomeInteractor}.
     *
     * @param homePresenter The output boundary responsible for preparing the home use case views.
     *                      Must not be {@code null}.
     */
    public HomeInteractor(HomeOutputBoundary homePresenter) {
        this.homePresenter = homePresenter;
    }

    /**
     * Executes the default action for the Home Use Case.
     * Currently, this method is a placeholder and can be extended with
     * additional logic as needed.
     */
    @Override
    public void execute() {
        // Placeholder for default execution logic
    }

    /**
     * Switches the application to the login view.
     */
    @Override
    public void switchToLoginView() {
        homePresenter.switchToLoginView();
    }

    /**
     * Switches the application to the create event view.
     */
    @Override
    public void switchtoCreateEventView() {
        homePresenter.switchToCreateEventView();
    }

    /**
     * Switches the application to the filter event view.
     */
    @Override
    public void switchToFilterEventView() {
        homePresenter.switchToFilterEventView();
    }

    /**
     * Switches the application to the view RSVP view.
     */
    @Override
    public void switchToViewRSVPView() {
        homePresenter.switchToViewRSVPView();
    }

    /**
     * Switches the application to the view created events view.
     */
    @Override
    public void switchToViewCreatedEventsView() {
        homePresenter.switchToViewCreatedEventsView();
    }
}
