package app.use_case.view_rsvp;

public interface ViewRSVPOutputBoundary {
    void prepareSuccessView(ViewRSVPOutputData outputData);
    void switchToHomeView();
}
