package app.use_case.modify_user;

import app.entity.User.User;

/**
 * Data Access Interface for modifying user information.
 * This interface defines methods for interacting with the data source to retrieve, check,
 * and update user information. It is used by the Modify User use case to manage user data.
 */
public interface ModifyUserDataAccessInterface {

    /**
     * Finds a user by their username.
     * This method retrieves the user object associated with the given username.
     * @param username The username of the user to retrieve. Must not be {@code null}.
     * @return The user object associated with the provided username, or {@code null} if not found.
     */
    User getUserByUsername(String username);

    /**
     * Checks if the given username is already taken.
     * This method is used to verify whether a username is available for a new user or if it
     * is already associated with an existing user.
     * @param username The username to check. Must not be {@code null}.
     * @return {@code true} if the username exists, {@code false} otherwise.
     */
    boolean doesUsernameExist(String username);

    /**
     * Checks if the given email is already associated with an existing user.
     * This method is used to verify whether an email address is already in use for a user account.
     * @param email The email address to check. Must not be {@code null}.
     * @return {@code true} if the email exists, {@code false} otherwise.
     */
    boolean doesEmailExist(String email);

    /**
     * Updates the user's details in the data source.
     * This method updates the user information in the data source with the new details provided.
     * @param user The user object containing updated details. Must not be {@code null}.
     * @return {@code true} if the user details were successfully updated, {@code false} otherwise.
     */
    boolean updateUser(User user);
}
