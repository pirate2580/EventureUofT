package main.java.java.use_case.register;


/**
 * Input Data for the Register Use Case
 */
public class RegisterInputData {
    private final String username;
    private final String password;

    public RegisterInputData(String username, String password, String repeatPassword) {
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
