package app.interface_adapter.register;

/**
 * Represents the state for the Register feature.
 * This class holds the registration details, including username, email, and password,
 * as well as any associated error messages for validation feedback.
 */
public class RegisterState {
    private String username = "";
    private String usernameError;
    private String password = "";
    private String passwordError;
    private String email = "";
    private String emailError = "";

    /**
     * Retrieves the username entered by the user.
     * @return the username, or an empty string if not set.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the registration process.
     * @param username the username to set. Cannot be {@code null}.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the error message associated with the username.
     * @return the username error message, or {@code null} if no error exists.
     */
    public String getUsernameError() {
        return usernameError;
    }

    /**
     * Sets the error message for the username.
     * @param usernameError the error message to set. Can be {@code null}.
     */
    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    /**
     * Retrieves the password entered by the user.
     * @return the password, or an empty string if not set.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the registration process.
     * @param password the password to set. Cannot be {@code null}.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the error message associated with the password.
     * @return the password error message, or {@code null} if no error exists.
     */
    public String getPasswordError() {
        return passwordError;
    }

    /**
     * Sets the error message for the password.
     * @param passwordError the error message to set. Can be {@code null}.
     */
    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    /**
     * Retrieves the email entered by the user.
     * @return the email, or an empty string if not set.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email for the registration process.
     * @param email the email to set. Cannot be {@code null}.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the error message associated with the email.
     * @return the email error message, or an empty string if no error exists.
     */
    public String getEmailError() {
        return emailError;
    }

    /**
     * Sets the error message for the email.
     * @param emailError the error message to set. Can be {@code null}.
     */
    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    /**
     * Returns a string representation of the register state.
     * For security reasons, the password is not fully displayed.
     * @return a string containing the username, email, and a protected password representation.
     */
    @Override
    public String toString() {
        return "RegisterState{"
                + "username='" + username + '\''
                + ", email=" + email + '\''
                + ", password='" + password + '\''
                + '}';
    }
}
