package app.use_case.login;

/**
 * Input Data for the Login Use Case.
 * This class serves as a data transfer object (DTO) for encapsulating
 * the login credentials entered by the user. It contains the username
 * and password required for authentication.
 */
public class LoginInputData {
    private final String username;
    private final String password;

    /**
     * Constructs a new {@link LoginInputData}.
     * @param username The username entered by the user. Must not be {@code null}.
     * @param password The password entered by the user. Must not be {@code null}.
     */
    public LoginInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the username entered by the user.
     * @return The username as a {@link String}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the password entered by the user.
     * @return The password as a {@link String}.
     */
    public String getPassword() {
        return password;
    }
}
