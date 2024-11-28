package app.interface_adapter.view_event;

import app.entity.Event.Event;

public class ViewEventState {
    public Event event;
    private String usernameState;

    public String getUsernameState() {return usernameState;}

    public void setUsernameState(String usernameState) { this.usernameState = usernameState;}

    public Event getViewEvent() {
        return this.event;
    }

    public void setViewEvent(Event event) {
        this.event = event;
    }
}

