package app.interface_adapter.notify_users;

import app.interface_adapter.ViewModel;

/**
 * ViewModel for the Notify User feature.
 * This class manages the state and view logic for the notification process.
 * It extends the {@link ViewModel} class and utilizes {@link NotifyUserState}
 * to store and manage the state related to notifying users.
 */
public class NotifyUserViewModel extends ViewModel<NotifyUserState> {
    public NotifyUserViewModel() {
        super("notifyUser");
        setState(new NotifyUserState());
    }
}
