package app.use_case.notify_users;

public class NotifyUserInputData {
    private final String eventName;

    public NotifyUserInputData(String eventName) {
        this.eventName = eventName;
    }
    public String getEventName() {return eventName;}
}
