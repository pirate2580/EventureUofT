package app.use_case.view_rsvp;
//import app.entity.Event.Event;

import java.util.List;

public interface ViewRSVPDataAccessInterface {
    List<String> getRSVPEvents(String username);
}
