package app.interface_adapter.display_event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.entity.Event.CommonEvent;
import app.entity.Event.Event;

public class DisplayEventState {

    private ArrayList<ArrayList<Object>> events;
    private String usernameState;

    private String getUsernameState() {return usernameState;}

    private void setUsernameState(String usernameState) { this.usernameState = usernameState;}

    public void setEvents(ArrayList<ArrayList<Object>> events) {
        this.events = events;
    }

    /**
     * Retrieves all events.
     * @return List of events.
     */
    public ArrayList<ArrayList<Object>> getEvents() {
        return this.events;
    }

}
