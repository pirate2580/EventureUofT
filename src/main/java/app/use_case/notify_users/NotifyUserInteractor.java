package app.use_case.notify_users;

public class NotifyUserInteractor implements NotifyUserInputBoundary {
    private final NotifyUserDataAccessInterface notifyUserDataAccessInterface;
    private final NotifyUserOutputBoundary notifyUserPresenter;

    public NotifyUserInteractor(NotifyUserDataAccessInterface notifyUserDataAccessInterface,
                                NotifyUserOutputBoundary notifyUserPresenter) {
        this.notifyUserDataAccessInterface = notifyUserDataAccessInterface;
        this.notifyUserPresenter = notifyUserPresenter;
    }

    @Override
    public void execute(NotifyUserInputData notifyUserInputData) {
        // Send notifications to RSVPed users for the given event
        notifyUserDataAccessInterface.notifyUsers(notifyUserInputData.getEventName());

        NotifyUserOutputData notifyUserOutputData = new NotifyUserOutputData("notification sent");
        // Prepare and pass a success view to the presenter
        notifyUserPresenter.prepareSuccessView(notifyUserOutputData);
    }
}
