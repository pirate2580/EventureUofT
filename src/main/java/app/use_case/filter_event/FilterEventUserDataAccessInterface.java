package app.use_case.filter_event;

import app.entity.Event.Event;
import java.util.List;

public interface FilterEventUserDataAccessInterface {
    /**
     *
     * @param tags
     * @return
     */
    List<Event> findEvents(List<String> tags);
    //TODO: Verify method implementation after integration with Google Maps API.
    // Also make sure the method interacts with new Google Maps geolocation data and provides accurate event filtering
}