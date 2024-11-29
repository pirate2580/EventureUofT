package app.interface_adapter.view_created_events;

import app.interface_adapter.ViewModel;

public class ViewCreatedEventsViewModel extends ViewModel<ViewCreatedEventsState>{
    public ViewCreatedEventsViewModel() {
        super("viewCreated");
        setState(new ViewCreatedEventsState());
    }
}
