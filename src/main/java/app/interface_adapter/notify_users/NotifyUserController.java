package app.interface_adapter.notify_users;

import app.use_case.notify_users.NotifyUserInputBoundary;
import app.use_case.notify_users.NotifyUserInputData;

/**
 * Controller for the Notify User Use Case.
 * This class handles the input for notifying users about an event. It acts as a bridge
 * between the view layer and the use case layer, preparing the input data and delegating
 * the execution to the appropriate interactor.
 */
public class NotifyUserController {

    private final NotifyUserInputBoundary notifyUserUseCaseInteractor;

    /**
     * Constructs a new {@link NotifyUserController}.
     * @param notifyUserUseCaseInteractor the interactor responsible for executing the notify user use case.
     *                                    Must not be {@code null}.
     */
    public NotifyUserController(NotifyUserInputBoundary notifyUserUseCaseInteractor) {
        this.notifyUserUseCaseInteractor = notifyUserUseCaseInteractor;
    }

    /**
     * Executes the Notify User Use Case.
     * Prepares the input data with the provided event title and passes it to the use case interactor
     * for processing.
     * @param eventTitle the title of the event to notify users about. Must not be {@code null}.
     */
    public void execute(String eventTitle) {
        final NotifyUserInputData notifyUserInputData = new NotifyUserInputData(eventTitle);
        this.notifyUserUseCaseInteractor.execute(notifyUserInputData);
    }
}
