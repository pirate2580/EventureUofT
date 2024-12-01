package app.use_case.notify_users;

/**
 * Represents the input data required to notify users about an event.
 * This class encapsulates the event name that will be used to notify users.
 */
public class NotifyUserInputData {

    private final String eventName;

    /**
     * Constructs an instance of NotifyUserInputData with the specified event name.
     * @param eventName The name of the event for which users should be notified.
     */
    public NotifyUserInputData(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Retrieves the event name associated with this notification.
     * @return The name of the event.
     */
    public String getEventName() {
        return eventName;
    }
}
