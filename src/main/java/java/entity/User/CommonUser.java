package main.java.java.entity.User;

import main.java.java.entity.Event.CommonEvent;
import java.util.List;
// TODO: is this clean architecture for entities to rely on another?

/**
 * Implementation of the User interface
 */
public class CommonUser implements User{
    private final String username;
    private final String password;
    private List<CommonEvent> rsvpedEvents;
    private List<CommonEvent> createdEvents;

    // Note that the two list attributes are not put in the constructor

    public CommonUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    pu

}
