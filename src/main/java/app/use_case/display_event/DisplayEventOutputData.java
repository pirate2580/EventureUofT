package app.use_case.display_event;
import app.entity.Event.CommonEvent;
import app.entity.Event.Event;

import java.util.ArrayList;
import java.util.List;

public class DisplayEventOutputData {
    private final ArrayList<CommonEvent> events;

    public DisplayEventOutputData(ArrayList<CommonEvent> events) {
        this.events = events;
    }

    public ArrayList<CommonEvent> getEvents() {return events;}
}
