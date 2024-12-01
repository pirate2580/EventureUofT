package app.interface_adapter.view_rsvp;

import java.util.List;

/**
 * Represents the state for the View RSVP feature.
 * This class holds the state information for viewing RSVP details, including
 * a list of events the user has RSVP'd to and the username of the current user.
 */
public class ViewRSVPState {

    private List<String> viewRSVP;
    private String usernameState;

    /**
     * Retrieves the username associated with the state.
     * @return the username of the user, or {@code null} if not set.
     */
    public String getUsernameState() {
        return usernameState;
    }

    /**
     * Sets the username for the RSVP view state.
     * @param usernameState the username of the user. Can be {@code null}.
     */
    public void setUsernameState(String usernameState) {
        this.usernameState = usernameState;
    }

    /**
     * Retrieves the list of RSVP'd events.
     * @return a list of event names that the user has RSVP'd to, or {@code null} if not set.
     */
    public List<String> getViewRSVP() {
        return this.viewRSVP;
    }

    /**
     * Sets the list of RSVP'd events for the state.
     * @param viewRSVP a list of event names that the user has RSVP'd to. Can be {@code null}.
     */
    public void setViewRSVP(List<String> viewRSVP) {
        this.viewRSVP = viewRSVP;
    }
}
