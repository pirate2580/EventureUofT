package app.use_case.notify_users;

/**
 * Interactor for the Notify User use case.
 * This class handles the logic for notifying users about an event. It uses the
 * {@link NotifyUserDataAccessInterface} to notify users and the
 * {@link NotifyUserOutputBoundary} to pass the result of the notification operation.
 */
public class NotifyUserInteractor implements NotifyUserInputBoundary {
    private final NotifyUserDataAccessInterface notifyUserDataAccessInterface;
    private final NotifyUserOutputBoundary notifyUserPresenter;

    /**
     * Constructs a NotifyUserInteractor instance with the given data access and presenter.
     * @param notifyUserDataAccessInterface The data access interface for notifying users.
     * @param notifyUserPresenter The output boundary that prepares the success view after notification.
     */
    public NotifyUserInteractor(NotifyUserDataAccessInterface notifyUserDataAccessInterface,
                                NotifyUserOutputBoundary notifyUserPresenter) {
        this.notifyUserDataAccessInterface = notifyUserDataAccessInterface;
        this.notifyUserPresenter = notifyUserPresenter;
    }

    /**
     * Executes the notification process for notifying users about an event.
     * This method uses the event name provided in the input data to notify the users
     * and then prepares a success view with the result of the operation.
     * @param notifyUserInputData The input data containing the event name to notify users about.
     */
    @Override
    public void execute(NotifyUserInputData notifyUserInputData) {
        // Send notifications to RSVPed users for the given event
        notifyUserDataAccessInterface.notifyUsers(notifyUserInputData.getEventName());

        NotifyUserOutputData notifyUserOutputData = new NotifyUserOutputData("notification sent");
        // Prepare and pass a success view to the presenter
        notifyUserPresenter.prepareSuccessView(notifyUserOutputData);
    }
}
