package app.use_case.view_rsvp;

//import app.entity.Event.Event;
import java.util.List;

public class ViewRSVPOutputData {
    private final List<String> rsvpEvents;

    public ViewRSVPOutputData(List<String> rsvpEvents) {
        this.rsvpEvents = rsvpEvents;
    }

    public List<String> getRSVPEvents () {
        return rsvpEvents;
    }
}
