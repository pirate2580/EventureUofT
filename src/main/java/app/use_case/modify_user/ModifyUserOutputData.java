package app.use_case.modify_user;

/**
 * Output data for modifying user account details.
 */
public class ModifyUserOutputData {
    private final String userId;
    private final String message;

    public ModifyUserOutputData(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}

