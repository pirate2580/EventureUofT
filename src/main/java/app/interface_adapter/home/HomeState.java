package app.interface_adapter.home;

/**
 * Represents the state of the Home view.
 * This class is responsible for storing user-related state information,
 * such as the current username, which can be accessed and modified as needed.
 */
public class HomeState {

    private String usernameState;

    /**
     * Retrieves the username associated with the current state.
     * @return the username of the user, or {@code null} if not set.
     */
    public String getUsernameState() {
        return usernameState;
    }

    /**
     * Sets the username for the current state.
     * @param usernameState the username to set. Can be {@code null}.
     */
    public void setUsernameState(String usernameState) {
        this.usernameState = usernameState;
    }
}
