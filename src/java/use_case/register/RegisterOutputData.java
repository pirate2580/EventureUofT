package java.use_case.register;


/**
 * Output data for the register user use case
 */
public class RegisterOutputData {

    private final String username;

    private final boolean useCaseFailed;

    public RegisterOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
