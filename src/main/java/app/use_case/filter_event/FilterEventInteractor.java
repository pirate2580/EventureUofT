package app.use_case.filter_event;

import java.util.List;

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

    @Override
    public void execute(FilterEventInputData filterEventInputData) {
        if (filterEventInputData == null || filterEventInputData.getTags() == null) {
            filterEventPresenter.prepareFailView("Please provide a valid filter event");
            return;
        }
        List<Event> filteredEvents = filterEventDataAccessObject.findEvents(filterEventInputData.getTags());
        final FilterEventOutputData filterEventOutputData = new FilterEventOutputData(filteredEvents);

        filterEventPresenter.prepareSuccessView(filterEventOutputData);
    }

    @Override
    public void switchToHomeView() {
        filterEventPresenter.switchToHomeView();

    }
}
