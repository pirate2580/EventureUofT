package app.use_case.register;

/**
 * Output data for the Register User use case.
 * This class contains the necessary information to communicate the result of a registration process.
 * It holds the username of the newly registered user and a flag indicating whether the use case failed.
 */
public class RegisterOutputData {

    private final String username;
    private final boolean useCaseFailed;

    /**
     * Constructs a new instance of RegisterOutputData.
     * @param username The username of the registered user.
     * @param useCaseFailed A flag indicating whether the use case (registration) was successful or failed.
     */
    public RegisterOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Retrieves the username of the registered user.
     * @return The username of the registered user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Checks if the use case (registration) failed.
     * @return {@code true} if the registration failed, {@code false} otherwise.
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
