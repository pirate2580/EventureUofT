package app.use_case.view_rsvp;

public interface ViewRSVPInputBoundary {
    void execute(ViewRSVPInputData RSVPInputData);

    void switchToHomeView();
}
