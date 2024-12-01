package app.use_case.view_created_events;

/**
 * Input Data class for the View Created Events Use Case.
 * This class holds the data necessary for the "view created events" use case.
 * It contains the user's information (e.g., username) to retrieve the list of
 * events they have created.
 */
public class ViewCreatedInputData {

    private final String username;

    /**
     * Constructor for the ViewCreatedInputData class.
     * Initializes the data object with the username of the user whose created events
     * are to be viewed.
     * @param username The username of the user.
     */
    public ViewCreatedInputData(String username) {
        this.username = username;
    }

    /**
     * Gets the username of the user.
     * This method returns the username that will be used to fetch the list of
     * created events.
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }
}
