package app.interface_adapter.notify_users;

import app.use_case.notify_users.NotifyUserInputBoundary;
import app.use_case.notify_users.NotifyUserInputData;

public class NotifyUserController {

    private final NotifyUserInputBoundary notifyUserUseCaseInteractor;

    public NotifyUserController(NotifyUserInputBoundary notifyUserUseCaseInteractor) {
        this.notifyUserUseCaseInteractor = notifyUserUseCaseInteractor;
    }

    public void execute(String eventTitle) {
        final NotifyUserInputData notifyUserInputData = new NotifyUserInputData(eventTitle);
        this.notifyUserUseCaseInteractor.execute(notifyUserInputData);
    }
}
