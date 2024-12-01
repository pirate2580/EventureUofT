package app.use_case.view_event;

import app.entity.Event.Event;

/**
 * Interactor for the View Event use case.
 * The ViewEventInteractor class handles the logic for fetching and displaying event details.
 * It interacts with the data layer (ViewEventUserDataAccessInterface) to retrieve event information
 * and communicates the results to the presenter (ViewEventOutputBoundary).
 */
public class ViewEventInteractor implements ViewEventInputBoundary {
    private final ViewEventUserDataAccessInterface eventDataAccessObject;
    private final ViewEventOutputBoundary viewEventPresenter;

    /**
     * Constructor for ViewEventInteractor.
     * Initializes the interactor with the necessary data access object and output boundary
     * for event viewing functionality.
     * @param eventDataAccessObject the data access object to retrieve event details
     * @param viewEventPresenter the presenter to prepare success/failure views
     */
    public ViewEventInteractor(ViewEventUserDataAccessInterface eventDataAccessObject,
                               ViewEventOutputBoundary viewEventPresenter) {
        this.eventDataAccessObject = eventDataAccessObject;
        this.viewEventPresenter = viewEventPresenter;
    }

    /**
     * Executes the View Event use case.
     * This method handles retrieving an event by its title and prepares a success view if the event exists.
     * If any of the required input data (such as the event title) is missing, it prepares a failure view.
     * @param viewEventInputData the input data containing the event title to be viewed
     */
    @Override
    public void execute(ViewEventInputData viewEventInputData) {
        if (viewEventInputData == null || viewEventInputData.getTitle() == null) {
            viewEventPresenter.prepareFailView("Event title cannot be null.");
            return;
        }

        Event event = eventDataAccessObject.viewEvent(viewEventInputData.getTitle());
        ViewEventOutputData outputData = new ViewEventOutputData(event);
        viewEventPresenter.prepareSuccessView(outputData);
    }

    /**
     * Switches to the home view.
     * This method delegates the action to the presenter to transition to the home view.
     */
    @Override
    public void switchToHomeView() {
        viewEventPresenter.switchToHomeView();
    }
}
