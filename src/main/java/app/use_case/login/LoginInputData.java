package app.use_case.login;

public class LoginInputData {
    private final String username;
    private final String password;

    /**
     * Constructor for LoginInputData, the user logs in by entering in
     * their username, password, and nothing else.
     * @param username
     * @param password
     */
    public LoginInputData(String username, String password){
        this.username = username;
        this.password = password;
    }

    String getUsername() {return username; }
    String getPassword() { return password; }
}
