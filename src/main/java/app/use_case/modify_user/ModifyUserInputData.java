package app.use_case.modify_user;

public class ModifyUserInputData {
    private final String username;
    private final String newUsername;
    private final String newEmail;
    private final String oldPassword;
    private final String newPassword;

    public ModifyUserInputData(String username, String newUsername, String newEmail, String oldPassword, String newPassword) {
        this.username = username;
        this.newUsername = newUsername;
        this.newEmail = newEmail;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}

