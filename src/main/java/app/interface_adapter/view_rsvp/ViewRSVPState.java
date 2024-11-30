package app.interface_adapter.view_rsvp;

import java.util.List;
//import app.entity.Event.Event;


public class ViewRSVPState {

    private List<String> viewRSVP;

    private String usernameState;

    public String getUsernameState() {return usernameState;}

    public void setUsernameState(String usernameState) { this.usernameState = usernameState;}

    public List<String> getViewRSVP() {
        return this.viewRSVP;
    }

    public void setViewRSVP(List<String> viewRSVP) {
        this.viewRSVP = viewRSVP;
    }



}
