package app.use_case.view_created_events;

public interface ViewCreatedOutputBoundary {
    void prepareSuccessView(ViewCreatedOutputData outputData);
    void switchToHomeView();
}
