package app.use_case.display_event;
import app.entity.Event.CommonEvent;
import app.entity.Event.Event;

import java.util.ArrayList;
import java.util.List;

public class DisplayEventOutputData {
    private final ArrayList<ArrayList<Object>> events;

    public DisplayEventOutputData(ArrayList<ArrayList<Object>> events) {
        this.events = events;
    }

    public ArrayList<ArrayList<Object>> getEvents() {return events;}
}
