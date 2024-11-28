package app.interface_adapter.filter_event;

import java.util.List;
import app.entity.Event.Event;  // TODO: i dont recall, does this break CA?

// TODO: will have to figure out what this should do
// has Event has a dependency
/**
 *
 */
public class FilterEventState {
    private List<Event> filteredEvents;

    private String usernameState;

    public String getUsernameState() {return usernameState;}

    public void setUsernameState(String usernameState) { this.usernameState = usernameState;}

    public List<Event> getFilteredEvents() {
        return this.filteredEvents;
    }
    public void setFilteredEvents(List<Event>filteredEvents) {
        this.filteredEvents = filteredEvents;
    }

    @Override
    public String toString(){
        return "Filtered events"; // TODO: change the toString method later
    }
}
