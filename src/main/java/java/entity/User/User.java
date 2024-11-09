package main.java.java.entity.User;
import main.java.java.entity.Event.Event;
import java.util.List;

/**
 * Representation of a user in the program
 */
public interface User {
    /**
     * Return the username of the user.
     * @return the username of the user
     */
    String getUsername();

    /**
     * Return the password of the user
     * @return the password of the user
     */
    String getPassword();

    /**
     * Add an Event element to the rsvpedList of the user
     * @param event an Event that the user RSVPs to
     */
    void addRsvpedEvent(Event event);

    /**
     * Remove an Event element IF IT EXISTS to the rsvpedList of the user
     * @param event an Event that the has RSVPED to but wants to cancel
     */
    void removeRsvpedEvent(Event event);

    /**
     * Method for when a user creates an Event, and this event is added to the users
     * CreatedEvent List
     * @param event an event that the user creates
     */
    void addCreatedEvent(Event event);

    /**
     * Remove an event that the user has created and remove it from the list
     * @param event an event that the user has created. PRECONDITION FROM THE GUI INPUT THAT THIS
     *              IS NECESSARILY AN EVENT THE USER CREATED
     */
    void removeCreatedEvent(Event event);

    /**
     * Return a list of the events the user RSVPs to
     * @return a list of the events user RSVPs
     */
    List<Event> getRsvpedEvents();

    /**
     * Return the list of events the user creates
     * @return the list of events the user creates
     */
    List<Event> getCreatedEvents();
}
