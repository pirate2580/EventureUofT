package app.use_case.display_event;

import app.data_access.EventDAO;
import app.entity.Event.CommonEvent;

import java.util.ArrayList;
import java.util.List;

public interface DisplayEventDataAccessInterface {
    ArrayList<CommonEvent> loadEvents();
    //extract database details
    // need to call event DAO
    // then pass it to the output boundary
    // which then passes it to presenter
    // input boundary can be used if the user clicks on a marker, disregard for now
}
