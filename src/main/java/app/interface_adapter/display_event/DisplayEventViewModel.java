package app.interface_adapter.display_event;

import app.interface_adapter.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayEventViewModel extends ViewModel<DisplayEventState> {
    // List to store events with their details

    // Constructor
    public DisplayEventViewModel() {
        super("displayEvent");
        setState(new DisplayEventState());
    }

}
