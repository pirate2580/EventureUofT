package app.entity.User;

/**
 * Factory for creating users
 */
public interface UserFactory {
    /**
     * Creates a new User
     * @param username the unique username of a new user
     * @param email the email of the user
     * @param password the password of the new user
     *
     * NOTE:
     * The parameters below are in user, but they are just empty when we create a new user and user factory doesn't have
     * to deal with this
     * private List<Event> rsvpEvents: A list of events the user has RSVPed to attend.
     * Private List<Event> createdEvents:
     *
     *
     */

    User create(String username, String email, String password);
}
