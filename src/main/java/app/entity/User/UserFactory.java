package app.entity.User;

/**
 * NOTE:
 * The parameters below are placeholders for user data but are empty when we create a new user.
 * These do not need to be managed directly by the UserFactory.
 * - {@code private List<Event> rsvpEvents}: A list of events the user has RSVPed to attend.
 * - {@code private List<Event> createdEvents}: A list of events the user has created.
 */

public interface UserFactory {

    /**
     * Creates a new User.
     *
     * @param username the unique username of a new user.
     * @param email the email of the user.
     * @param password the password of the new user.
     * @return a new {@code User} object with the specified username, email, and password.
     */
    User create(String username, String email, String password);
}
