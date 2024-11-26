package app.interface_adapter.display_event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.entity.Event.CommonEvent;
import app.entity.Event.Event;

public class DisplayEventState {

    private ArrayList<CommonEvent> events;

    public void setEvents(ArrayList<CommonEvent> events) {
        this.events = events;
    }

    /**
     * Retrieves all events.
     * @return List of events.
     */
    public ArrayList<CommonEvent> getEvents() {
        return this.events;
    }

}
