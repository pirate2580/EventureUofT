package app.use_case.view_event;

import app.entity.Event.Event;

/**
 * Data Access Interface for the View Event Use Case.
 * This interface defines the contract for accessing event data from the data source.
 * It allows retrieving event details based on specific criteria, such as the event title.
 */
public interface ViewEventUserDataAccessInterface {

    /**
     * Retrieves an event based on its title.
     * This method is called when the user views an event, and it returns the event
     * details corresponding to the provided title.
     * @param title The title of the event to retrieve.
     * @return The event associated with the provided title, or {@code null} if no event is found.
     */
    Event viewEvent(String title);
}
