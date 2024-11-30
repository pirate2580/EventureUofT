package app.use_case.view_event;

import app.entity.Event.Event;
import app.use_case.view_event.ViewEventInputData;
import java.util.List;


public interface ViewEventUserDataAccessInterface {

    Event viewEvent(String title);
}
