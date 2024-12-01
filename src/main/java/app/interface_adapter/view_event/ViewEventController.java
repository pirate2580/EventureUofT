package app.interface_adapter.view_event;

import app.use_case.view_event.ViewEventInputBoundary;
import app.use_case.view_event.ViewEventInputData;

/**
 * Controller for the View Event Use Case.
 * This class handles user input for viewing details of a specific event. It prepares
 * the input data and delegates execution to the appropriate use case interactor.
 * Additionally, it provides functionality to transition back to the home view.
 */
public class ViewEventController {

    private final ViewEventInputBoundary viewEventUseCaseInteractor;

    /**
     * Constructs a new {@link ViewEventController}.
     * @param viewEventUseCaseInteractor the interactor responsible for handling the view event use case.
     *                                    Must not be {@code null}.
     */
    public ViewEventController(ViewEventInputBoundary viewEventUseCaseInteractor) {
        this.viewEventUseCaseInteractor = viewEventUseCaseInteractor;
    }

    /**
     * Executes the View Event Use Case.
     * Prepares the input data with the provided event title and delegates the processing
     * to the use case interactor.
     * @param title the title of the event to be viewed. Must not be {@code null}.
     */
    public void execute(String title) {

        final ViewEventInputData viewEventInputData = new ViewEventInputData(title);

        this.viewEventUseCaseInteractor.execute(viewEventInputData);
    }

    /**
     * Switches the application to the home view.
     * Delegates the view transition logic to the use case interactor.
     */
    public void switchToHomeView() {
        this.viewEventUseCaseInteractor.switchToHomeView();
    }
}
