package app.use_case.login;

/**
 * Output Data for the Login Use Case.
 * This class encapsulates the results of the login process, including the username
 * of the user and whether the login was successful. It is used to transfer data
 * from the interactor to the presenter.
 */
public class LoginOutputData {
    private final String username;
    private final boolean success;

    /**
     * Constructs a new {@link LoginOutputData}.
     * @param username The username of the user who attempted to log in. Must not be {@code null}.
     * @param success  A boolean indicating whether the login attempt was successful.
     */
    public LoginOutputData(String username, boolean success) {
        this.username = username;
        this.success = success;
    }

    /**
     * Retrieves the username of the user who attempted to log in.
     * @return The username as a {@link String}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the login attempt was successful.
     * @return {@code true} if the login attempt was successful, {@code false} otherwise.
     */
    public boolean isSuccess() {
        return success;
    }
}
