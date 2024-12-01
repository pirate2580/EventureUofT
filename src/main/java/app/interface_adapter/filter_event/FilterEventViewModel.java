package app.interface_adapter.filter_event;

import app.interface_adapter.ViewModel;

/**
 * ViewModel for the Filter Event Use Case.
 * This class manages the state and UI labels for the Filter Event feature.
 * It extends the {@link ViewModel} class with {@link FilterEventState} as its state.
 */
public class FilterEventViewModel extends ViewModel<FilterEventState> {
    public static final String TITLE_LABEL = "Filter Event View";
    public static final String FILTER_EVENT_TITLE_LABEL = "Below are the events based on your filter";
    public static final String FILTER_EVENT_SEARCH_BAR = "Choose a tag you want to filter by";
    public static final String FILTER_EVENT_BUTTON_LABEL = "FILTER EVENTS";


    public FilterEventViewModel() {
        super("filterEvent");
        setState(new FilterEventState());
    }
}
