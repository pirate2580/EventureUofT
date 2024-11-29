package app.use_case.view_created_events;


public interface ViewCreatedInputBoundary {
    void execute (ViewCreatedInputData CreatedInputData);

    void switchToHomeView();
}
