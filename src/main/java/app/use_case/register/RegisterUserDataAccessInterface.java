package app.use_case.register;

import app.entity.User.User;

/**
 * Data Access Object for the Register Use Case folder.
 */
public interface RegisterUserDataAccessInterface {

    /**
     * Checks if the username exists. This is used downstream so that the db has no
     * duplicate usernames
     * @param username the username to look for
     * @return true if the user with the given username exists; false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Saves the user to the database.
     * @param user the user to save
     */
    void save(User user);
}
