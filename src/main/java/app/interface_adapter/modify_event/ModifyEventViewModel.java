package app.interface_adapter.modify_event;

import app.interface_adapter.ViewModel;

/**
 * ViewModel for the Modify Event functionality.
 * This class manages the state and UI labels associated with modifying events.
 * It extends the {@link ViewModel} class and utilizes {@link ModifyEventState}
 * to store and manage the state of the modify event feature.
 */
public class ModifyEventViewModel extends ViewModel<ModifyEventState> {
    public static final String TITLE_LABEL = "Event View";
    public static final String EVENT_TITLE_LABEL = "Choose event title";
    public static final String EVENT_DESCRIPTION_LABEL = "Set description";
    public static final String EVENT_DATETIME_LABEL = "Set date and time";
    public static final String EVENT_CAPACITY_LABEL = "Set capacity";
    public static final String EVENT_LATITUDE_LABEL = "Set event Latitude";
    public static final String EVENT_LONGITUDE_LABEL = "Set event Longitude";
    public static final String EVENT_TAGS_LABEL = "Set event tags";

    public static final String CREATE_EVENT_BUTTON_LABEL = "Create Event";

    public ModifyEventViewModel() {
        super("modifyEvent");
        setState(new ModifyEventState());
    }
}
