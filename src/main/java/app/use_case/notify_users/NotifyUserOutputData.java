
package app.use_case.notify_users;

/**
 * Output data for the Notify User use case.
 * This class encapsulates the output data for the Notify User use case, specifically
 * the title of the event for which users are being notified. It provides a way
 * to communicate the event details to the presenter after the notification operation.
 */
public class NotifyUserOutputData {

    private final String eventTitle;

    /**
     * Constructs an instance of NotifyUserOutputData with the given event title.
     * @param eventTitle The title of the event that users are being notified about.
     */
    public NotifyUserOutputData(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * Retrieves the title of the event.
     * @return The title of the event for which users are being notified.
     */
    public String getEventTitle() {
        return eventTitle;
    }
}
