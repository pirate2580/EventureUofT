package app.use_case.modify_user;

/**
 * Output data for modifying user account details.
 */
public class ModifyUserOutputData {

    private final String username;
    private final String message;

    /**
     * Constructor to initialize the output data for the Modify User use case.
     * @param username The username of the user whose account details have been modified.
     *                 This will typically be the updated username.
     * @param message A message that summarizes the result of the modification,
     *                such as success or failure explanation.
     */
    public ModifyUserOutputData(String username, String message) {
        this.username = username;
        this.message = message;
    }

    /**
     * Retrieves the username of the user whose account details have been modified.
     * @return the username, which may be the updated username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the message summarizing the modification result.
     * @return the result message
     */
    public String getMessage() {
        return message;
    }
}

