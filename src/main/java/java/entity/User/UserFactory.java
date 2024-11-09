package main.java.java.entity.User;

/**
 * Factory for creating users
 */
public interface UserFactory {
    /**
     * Creates a new User
     * @param username the unique username of a new user    //TODO: handle case so that username stays unique
     * @param password the pasword of the new user
     *
     * NOTE:
     * The parameters below are in user but they are just empty when we create a new user and user factory doesn't have
     * to deal with this
     * private List<Event> rsvpEvents: A list of events the user has RSVPed to attend.
     * Private List<Event> createdEvents:
     *
     *
     */

    User create(String username, String password);
}
