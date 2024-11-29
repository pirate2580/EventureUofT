package app.use_case.notify_users;

public interface NotifyUserOutputBoundary {
    void prepareSuccessView(NotifyUserOutputData outputData);
    void prepareFailView(String errorMessage);
}
