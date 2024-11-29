package app.interface_adapter.view_rsvp;

import app.use_case.view_rsvp.ViewRSVPInputBoundary;
import app.use_case.view_event.ViewEventInputData;
import app.use_case.view_rsvp.ViewRSVPInputData;

public class ViewRSVPController {
    private final ViewRSVPInputBoundary viewRSVPUseCaseInteractor;

    public ViewRSVPController(ViewRSVPInputBoundary viewRSVPUseCaseInteractor) {
        this.viewRSVPUseCaseInteractor =viewRSVPUseCaseInteractor;
    }

    public void execute(String username) {
        final ViewRSVPInputData viewRSVPInputData = new ViewRSVPInputData(username);

        this.viewRSVPUseCaseInteractor.execute(viewRSVPInputData);
    }

    public void switchToHomeView() {this.viewRSVPUseCaseInteractor.switchToHomeView();}

}
