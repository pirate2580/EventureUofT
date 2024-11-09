package main.java.java.entity.User;
import main.java.java.entity.Event.Event;

import java.util.List;
// TODO: is this clean architecture for entities to rely on another?
// TODO: 12am stupid question, is it clean architecture to have an implementation of adding events to a user
// This doesn't rely on the database but still idk?
/**
 * Implementation of the User interface
 */
public class CommonUser implements User{
    private final String username;
    private final String password;

    // notice this way of setting up for a general Event interface is better than
    // using a specific implementation
    private List<Event> rsvpedEvents;
    private List<Event> createdEvents;

    // Note that the two list attributes are not put in the constructor

    public CommonUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void addRsvpedEvent(Event event) {
        // notice we use dependency injection design pattern here
        this.rsvpedEvents.add(event);
    }

    @Override
    public void removeRsvpedEvent(Event event) {
        // TODO: might need a special event.equals method
        // TODO: check edge cases on implementation
        for (int i = 0; i < this.rsvpedEvents.size(); i++){
            if (this.rsvpedEvents.get(i).equals(event)){
                this.rsvpedEvents.remove(i);
                break;
            }
        }

    }

    @Override
    public void addCreatedEvent(Event event) {
        this.createdEvents.add(event);
    }

    @Override
    public void removeCreatedEvent(Event event) {
        // TODO: might need a special event.equals method
        // TODO: check edge cases on implementation
        for (int i = 0; i < this.createdEvents.size(); i++){
            if (this.createdEvents.get(i).equals(event)){
                this.createdEvents.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Event> getRsvpedEvents() {
        return this.rsvpedEvents;
    }

    @Override
    public List<Event> getCreatedEvents() {
        return this.createdEvents;
    }
}
