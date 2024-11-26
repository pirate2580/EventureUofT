package app.use_case.display_event;

public interface DisplayEventOutputBoundary {
    void prepareSuccessView(DisplayEventOutputData eventOutputData);

    void prepareFailView(String errorMessage);
}
