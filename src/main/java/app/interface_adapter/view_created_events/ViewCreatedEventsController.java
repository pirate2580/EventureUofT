package app.interface_adapter.view_created_events;

import app.use_case.view_created_events.ViewCreatedInputBoundary;
import app.use_case.view_created_events.ViewCreatedInputData;

public class ViewCreatedEventsController {
    private final ViewCreatedInputBoundary viewCreatedUseCaseInteractor;

    public ViewCreatedEventsController(ViewCreatedInputBoundary viewCreatedUseCaseInteractor) {
        this.viewCreatedUseCaseInteractor = viewCreatedUseCaseInteractor;
    }

    public void execute(String username) {
        final ViewCreatedInputData viewCreatedInputData = new ViewCreatedInputData(username);
        this.viewCreatedUseCaseInteractor.execute(viewCreatedInputData);
    }

    public void switchToHomeView() {this.viewCreatedUseCaseInteractor.switchToHomeView();}
}
