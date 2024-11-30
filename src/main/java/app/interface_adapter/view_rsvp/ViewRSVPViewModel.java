package app.interface_adapter.view_rsvp;

import app.interface_adapter.ViewModel;

public class ViewRSVPViewModel extends ViewModel<ViewRSVPState> {

    public ViewRSVPViewModel() {
        super("viewRSVP");
        setState(new ViewRSVPState());
    }
}
