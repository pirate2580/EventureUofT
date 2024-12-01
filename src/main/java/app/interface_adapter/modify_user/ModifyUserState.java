package app.interface_adapter.modify_user;

/**
 * Represents the state for the Modify User feature.
 * This class holds the state related to the user modification process,
 * including the username currently being modified.
 */
public class ModifyUserState {
    private String usernameState;

    /**
     * Retrieves the current username associated with the modify user process.
     * @return the username, or {@code null} if not set.
     */
    private String getUsernameState() {
        return usernameState;
    }

    /**
     * Sets the username associated with the modify user process.
     * @param usernameState the username to set. Can be {@code null}.
     */
    private void setUsernameState(String usernameState) {
        this.usernameState = usernameState;
    }
}
