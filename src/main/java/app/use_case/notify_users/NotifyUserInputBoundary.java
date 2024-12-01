package app.use_case.notify_users;

/**
 * Interface for notifying users about a specific event.
 * This interface defines a contract for sending notifications to users
 * related to an event, identified by its title. The actual notification mechanism
 * should be implemented by the class that adheres to this interface.
 */
public interface NotifyUserInputBoundary {

    /**
     * Notifies users about an event based on the event's title.
     * The implementation of this method should define how users are notified via email
     * @param notifyUserInputData The title of the event that users are being notified about.
     *                   This will serve as the reference for the event notification.
     */
    void execute(NotifyUserInputData notifyUserInputData);
}
