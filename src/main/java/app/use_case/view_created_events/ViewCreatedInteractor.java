package app.use_case.view_created_events;

import java.util.List;

public class ViewCreatedInteractor implements ViewCreatedInputBoundary {

    private final ViewCreatedDataAccessInterface viewCreatedDataAccessObject;
    private final ViewCreatedOutputBoundary viewCreatedPresenter;

    public ViewCreatedInteractor(ViewCreatedDataAccessInterface viewCreatedDataAccessObject,
                                 ViewCreatedOutputBoundary viewCreatedPresenter) {
        this.viewCreatedDataAccessObject = viewCreatedDataAccessObject;
        this.viewCreatedPresenter = viewCreatedPresenter;
    }

    @Override
    public void execute(ViewCreatedInputData CreatedInputData) {
        List<String> viewCreatedEvents = viewCreatedDataAccessObject.getCreatedEvents(CreatedInputData.getUsername());
        final ViewCreatedOutputData viewCreatedOutputData = new ViewCreatedOutputData(viewCreatedEvents);

        viewCreatedPresenter.prepareSuccessView(viewCreatedOutputData);
    }

    @Override
    public void switchToHomeView() {
        viewCreatedPresenter.switchToHomeView();
    }
}
