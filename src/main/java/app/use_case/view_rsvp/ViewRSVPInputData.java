package app.use_case.view_rsvp;

import java.util.List;
import app.entity.Event.Event;

public class ViewRSVPInputData {
    // no input data, triggered upon button click and view change
    private final String username;

    public ViewRSVPInputData(String username) {
        this.username = username;
    }
    public String getUsername() {return username;}
}
