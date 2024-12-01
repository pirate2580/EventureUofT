package app.interface_adapter.view_created_events;

import app.use_case.view_created_events.ViewCreatedInputBoundary;
import app.use_case.view_created_events.ViewCreatedInputData;

/**
 * Controller for the View Created Events Use Case.
 * This class handles user input for viewing events created by a user. It prepares
 * the input data and delegates the execution to the appropriate use case interactor.
 * Additionally, it provides functionality to transition back to the home view.
 */
public class ViewCreatedEventsController {
    private final ViewCreatedInputBoundary viewCreatedUseCaseInteractor;

    /**
     * Constructs a new {@link ViewCreatedEventsController}.
     * @param viewCreatedUseCaseInteractor the interactor responsible for handling the view created events use case.
     *                                      Must not be {@code null}.
     */
    public ViewCreatedEventsController(ViewCreatedInputBoundary viewCreatedUseCaseInteractor) {
        this.viewCreatedUseCaseInteractor = viewCreatedUseCaseInteractor;
    }

    /**
     * Executes the View Created Events Use Case.
     * Prepares the input data with the provided username and delegates the processing
     * to the use case interactor.
     * @param username the username of the user whose created events are to be viewed.
     *                 Must not be {@code null}.
     */
    public void execute(String username) {
        final ViewCreatedInputData viewCreatedInputData = new ViewCreatedInputData(username);
        this.viewCreatedUseCaseInteractor.execute(viewCreatedInputData);
    }

    /**
     * Switches the application to the home view.
     * Delegates the view transition logic to the use case interactor.
     */
    public void switchToHomeView() {
        this.viewCreatedUseCaseInteractor.switchToHomeView();
    }
}
