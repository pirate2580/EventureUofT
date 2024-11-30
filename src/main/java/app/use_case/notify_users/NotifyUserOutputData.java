package app.use_case.notify_users;

public class NotifyUserOutputData {
    private final String eventTitle;

    public NotifyUserOutputData(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventTitle() {return eventTitle;}
}
