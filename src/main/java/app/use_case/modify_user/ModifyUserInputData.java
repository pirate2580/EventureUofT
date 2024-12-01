package app.use_case.modify_user;

/**
 * Input Data for the Modify User Use Case.
 * This class encapsulates the information needed to modify a user's account details, including
 * the current username, new username, new email, current password (for verification), and new password.
 */
public class ModifyUserInputData {
    private final String username;
    private final String newUsername;
    private final String newEmail;
    private final String oldPassword;
    private final String newPassword;

    /**
     * Constructor for ModifyUserInputData.
     * @param username    The current username of the user.
     * @param newUsername The new username to be updated.
     * @param newEmail    The new email address to be updated.
     * @param oldPassword The current password for verification.
     * @param newPassword The new password to be set.
     */
    public ModifyUserInputData(String username,
                               String newUsername,
                               String newEmail,
                               String oldPassword,
                               String newPassword) {
        this.username = username;
        this.newUsername = newUsername;
        this.newEmail = newEmail;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    /**
     * Gets the current username.
     *
     * @return the current username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the new username to be set.
     *
     * @return the new username.
     */
    public String getNewUsername() {
        return newUsername;
    }

    /**
     * Gets the new email to be set.
     *
     * @return the new email.
     */
    public String getNewEmail() {
        return newEmail;
    }

    /**
     * Gets the current password of the user, used for verification.
     *
     * @return the current password.
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Gets the new password to be set.
     *
     * @return the new password.
     */
    public String getNewPassword() {
        return newPassword;
    }
}

