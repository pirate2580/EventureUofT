package app.interface_adapter.login;

/**
 * Represents the state of the Login view.
 * This class stores the username, password, and their respective error messages,
 * allowing the login process to manage and validate user input effectively.
 */
public class LoginState {
    private String username = "";
    private String usernameError;
    private String password = "";
    private String passwordError;

    /**
     * Retrieves the username entered by the user.
     *
     * @return the username, or an empty string if not set.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the login state.
     *
     * @param username the username to set. Cannot be {@code null}.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the error message associated with the username.
     *
     * @return the username error message, or {@code null} if no error exists.
     */
    public String getUsernameError() {
        return usernameError;
    }

    /**
     * Sets the error message associated with the username.
     *
     * @param usernameError the error message to set. Can be {@code null}.
     */
    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    /**
     * Retrieves the password entered by the user.
     *
     * @return the password, or an empty string if not set.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the login state.
     *
     * @param password the password to set. Cannot be {@code null}.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the error message associated with the password.
     *
     * @return the password error message, or {@code null} if no error exists.
     */
    public String getPasswordError() {
        return passwordError;
    }

    /**
     * Sets the error message associated with the password.
     *
     * @param passwordError the error message to set. Can be {@code null}.
     */
    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    /**
     * Returns a string representation of the login state.
     * For security reasons, the password is not fully displayed in the output.
     *
     * @return a string representation of this state, showing the username and obfuscated password.
     */
    @Override
    public String toString() {
        return "LoginState{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + '}';
    }

}
