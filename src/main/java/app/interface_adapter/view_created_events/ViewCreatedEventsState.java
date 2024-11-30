package app.interface_adapter.view_created_events;

import java.util.List;

public class ViewCreatedEventsState {

    private List<String> createdEvents;

    private String usernameState;

    public String getUsernameState() {return usernameState;}

    public void setUsernameState(String usernameState) { this.usernameState = usernameState;}

    public List<String> getCreatedEvents() {
        return  this.createdEvents;
    }

    public void setCreatedEvents(List<String> createdEvents) {
        this.createdEvents = createdEvents;
    }
}
