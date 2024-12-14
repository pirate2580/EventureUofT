package app.use_case.filter_event;

import java.util.List;

// The FilterEventInteractor filtering events in the application, it uses the Event Data Access to
// retrieve relevant events and sends the results through the presenter.
import app.entity.Event.Event;

/**
 * Interactor for the Filter Event Use Case.
 * This class handles the business logic for filtering events based on user-provided criteria.
 * It interacts with the data access object (DAO) to retrieve filtered events and passes
 * the results to the presenter for preparing the view.
 */
public class FilterEventInteractor implements FilterEventInputBoundary {

    private final FilterEventUserDataAccessInterface filterEventDataAccessObject;
    private final FilterEventOutputBoundary filterEventPresenter;

    /**
     * Constructs a new {@link FilterEventInteractor}.
     * @param eventDataAccessObject The DAO for accessing filtered event data. Must not be {@code null}.
     * @param filterEventPresenter  The output boundary for preparing the view. Must not be {@code null}.
     */
    public FilterEventInteractor(FilterEventUserDataAccessInterface eventDataAccessObject,
                                 FilterEventOutputBoundary filterEventPresenter) {
        this.filterEventDataAccessObject = eventDataAccessObject;
        this.filterEventPresenter = filterEventPresenter;
    }

    /**
     * Executes the filter event use case through validating input data, retrieving filtered events,
     * and preparing success or failure views.
     *
     * @param filterEventInputData, input data containing tags to filter events, can't be null or have
     *                              null tags
     */
    @Override
    public void execute(FilterEventInputData filterEventInputData) {
        if (filterEventInputData.getTags().isEmpty()) {
            filterEventPresenter.prepareFailView("Please provide a valid filter event");
            return;
        }
        // Retrieve events based on the provided criteria.
        List<Event> filteredEvents = filterEventDataAccessObject.findEvents(filterEventInputData.getTags());
        final FilterEventOutputData filterEventOutputData = new FilterEventOutputData(filteredEvents);

        filterEventPresenter.prepareSuccessView(filterEventOutputData);
    }


    /**
     * Switches the current view to HomeView by calling the presenter's function.
     */
    @Override
    public void switchToHomeView() {
        filterEventPresenter.switchToHomeView();

    }
}
