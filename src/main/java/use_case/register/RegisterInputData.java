package use_case.register;


/**
 * Input Data for the Register Use Case
 * The View sends data to the controller which should have an actionListener, and then
 * will prepare data one triggered
 */
public class RegisterInputData {
    private final String username;
    private final String password;

    /**
     * Constructor for RegisterInputData, the user registers by entering in
     * their username, password, and nothing else. The two other attributes createdEvents
     * and rsvpEvents are added to later
     * @param username
     * @param password
     */
    public RegisterInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }
}
