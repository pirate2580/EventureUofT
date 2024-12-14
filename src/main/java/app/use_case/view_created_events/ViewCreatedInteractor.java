package app.use_case.view_created_events;

import java.util.List;

/**
 * Interactor for the View Created Events Use Case.
 * This class is responsible for handling the business logic of viewing the events
 * created by a specific user. It retrieves the list of created events for a user
 * and passes this information to the presenter to prepare the success view.
 */
public class ViewCreatedInteractor implements ViewCreatedInputBoundary {

    private final ViewCreatedDataAccessInterface viewCreatedDataAccessObject;
    private final ViewCreatedOutputBoundary viewCreatedPresenter;

    /**
     * Constructor for the ViewCreatedInteractor class.
     * Initializes the interactor with the data access object and presenter
     * needed for executing the use case and preparing the output view.
     * @param viewCreatedDataAccessObject The data access interface used to retrieve created events.
     * @param viewCreatedPresenter The presenter used to prepare the success or failure views.
     */
    public ViewCreatedInteractor(ViewCreatedDataAccessInterface viewCreatedDataAccessObject,
                                 ViewCreatedOutputBoundary viewCreatedPresenter) {
        this.viewCreatedDataAccessObject = viewCreatedDataAccessObject;
        this.viewCreatedPresenter = viewCreatedPresenter;
    }

    /**
     * Executes the "view created events" use case.
     * This method retrieves the list of created events for the user and
     * prepares the success view using the output data.
     * @param CreatedInputData The input data containing the username of the user whose created events are to be viewed.
     */
    @Override
    public void execute(ViewCreatedInputData CreatedInputData) {
        List<String> viewCreatedEvents = viewCreatedDataAccessObject.getCreatedEvents(CreatedInputData.getUsername());
        final ViewCreatedOutputData viewCreatedOutputData = new ViewCreatedOutputData(viewCreatedEvents);

        viewCreatedPresenter.prepareSuccessView(viewCreatedOutputData);
    }

    /**
     * Switches to the home view.
     * This method tells the presenter to navigate back to the home view.
     */
    @Override
    public void switchToHomeView() {
        viewCreatedPresenter.switchToHomeView();
    }
}
