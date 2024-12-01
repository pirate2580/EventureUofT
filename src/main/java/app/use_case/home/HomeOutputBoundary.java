package app.use_case.home;

public interface HomeOutputBoundary {
    void switchToCreateEventView();
    void switchToLoginView();
    void switchToFilterEventView();
    void switchToViewRSVPView();
    void switchToViewCreatedEventsView();
    void switchToModifyEventView();
}
