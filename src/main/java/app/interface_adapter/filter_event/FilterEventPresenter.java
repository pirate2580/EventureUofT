package app.interface_adapter.filter_event;

import app.interface_adapter.ViewManagerModel;
import app.use_case.filter_event.FilterEventOutputBoundary;
import app.use_case.filter_event.FilterEventOutputData;
import app .view.FilterEventView;

/**
 * The presenter for the Filter Event Use Case
 */
public class FilterEventPresenter implements FilterEventOutputBoundary{
    private final FilterEventViewModel filterEventViewModel;
//    private final FilterEventView filterEventManagerModel;
    private final ViewManagerModel viewManagerModel;

    public FilterEventPresenter(ViewManagerModel viewManagerModel,
                                FilterEventViewModel filterEventViewModel) {
        this.filterEventViewModel = filterEventViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares the succeess view for the Filter Event Use case which would be a list of the
     * Events matching the filter
     * @param outputData The output data including the list of filtered events.
     */
    @Override
    public void prepareSuccessView(FilterEventOutputData outputData) {

    }

    /**
     * Prepares the fail view for the Filter Event use case, probably just hte same as the
     * success view except it says its empty
     * @param errorMessage The explanation of the failure.
     */
    @Override
    public void prepareFailView(String errorMessage) {

    }
}
