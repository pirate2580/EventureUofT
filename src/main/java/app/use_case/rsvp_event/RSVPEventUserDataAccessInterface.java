package app.use_case.rsvp_event;

import app.entity.Event.Event;
import app.entity.User.User;

public interface RSVPEventUserDataAccessInterface {
    /**
     * Finds an event by its ID.
     * @param eventId The ID of the event to find.
     * @return The Event object if found, null otherwise.
     */

    void addUserToRSVPList(String username, String eventId);
    //Adds a user to an event's attendees list.
}
