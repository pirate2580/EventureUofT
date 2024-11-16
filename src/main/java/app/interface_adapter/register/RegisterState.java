package app.interface_adapter.register;

public class RegisterState {
    private String username = "";
    private String usernameError;
    private String password = "";
    private String passwordError;
    private String email = "";
    private String emailError = "";

    public String getUsername() {
        return username;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getEmail() { return email; }

    public String getEmailError() { return emailError; }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setEmail(String email) {this.email = email; }

    public void setEmailError(String emailError) {this.emailError = emailError; }

    @Override
    public String toString() {
        return "SignupState{"
                + "username='" + username + '\''
                + ", email=" + email + '\''
                + ", password='" + password + '\''
                + '}';
    }

}
