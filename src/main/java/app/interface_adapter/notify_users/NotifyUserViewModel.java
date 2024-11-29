package app.interface_adapter.notify_users;

import app.interface_adapter.ViewModel;

public class NotifyUserViewModel extends ViewModel<NotifyUserState>{
    public NotifyUserViewModel() {
        super("notifyUser");
        setState(new NotifyUserState());
    }
}
