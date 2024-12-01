package app.interface_adapter.display_event;

import app.interface_adapter.ViewModel;

/**
 * ViewModel for displaying events.
 * This class manages the state for displaying events and interacts with the UI.
 */
public class DisplayEventViewModel extends ViewModel<DisplayEventState> {

    public DisplayEventViewModel() {
        super("displayEvent");
        setState(new DisplayEventState());
    }

}
