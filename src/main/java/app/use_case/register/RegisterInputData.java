package app.use_case.register;

/**
 * Input data for the Register use case.
 * This class holds the necessary data required for a user to register. It encapsulates the
 * username, email, and password provided by the user. This data is passed to the controller
 * for further processing.
 */
public class RegisterInputData {
    private final String username;
    private final String password;
    private final String email;

    /**
     * Constructs an instance of RegisterInputData with the provided username, email, and password.
     * @param username The username chosen by the user.
     * @param email The email address of the user.
     * @param password The password chosen by the user.
     */
    public RegisterInputData(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Retrieves the username provided by the user.
     * @return The username chosen by the user.
     */
    String getUsername() {
        return username;
    }

    /**
     * Retrieves the password provided by the user.
     * @return The password chosen by the user.
     */
    String getPassword() {
        return password;
    }

    /**
     * Retrieves the email address provided by the user.
     * @return The email address of the user.
     */
    String getEmail() {
        return email;
    }
}
