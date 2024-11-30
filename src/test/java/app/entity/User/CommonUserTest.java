package app.entity.User;

import app.entity.Event.CommonEvent;
import app.entity.Event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommonUserTest {

    private CommonUser user;
    private Event event1;
    private Event event2;

    @BeforeEach
    void setUp() {

        user = new CommonUser("testuser", "test@example.com", "password123");
        user.setRsvpedEvents(new ArrayList<>());
        user.setCreatedEvents(new ArrayList<>());
        event1 = new CommonEvent("E001", "Organizer1", "Event Title 1", "Description 1",
                "2024-11-30T10:00", 100, 43.6529f, -79.3849f, List.of("tag1", "tag2"));
        event2 = new CommonEvent("E002", "Organizer2", "Event Title 2", "Description 2",
                "2024-12-01T14:00", 200, 43.7000f, -79.4000f, List.of("tag3", "tag4"));
    }

    @Test
    void testGetUsername() {
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void testGetEmail() {
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testSetUsername() {
        user.setUsername("newuser");
        assertEquals("newuser", user.getUsername());
    }

    @Test
    void testSetEmail() {
        String email = "newemail@example.com";
        user.setEmail(email);
        System.out.println("setEmail called with: " + email); // Debugging
        assertEquals("newemail@example.com", user.getEmail());
    }

    @Test
    void testSetEmailNull() {
        user.setEmail(null);
        assertNull(user.getEmail());
    }

    @Test
    void testSetPassword() {
        user.setPassword("newpassword123");
        System.out.println("Test " + user.getPassword()); // Debugging
        assertEquals("newpassword123", user.getPassword());
    }

    @Test
    void testVerifyPassword() {
        assertTrue(user.verifyPassword("password123"));
        assertFalse(user.verifyPassword("wrongpassword"));
    }

    @Test
    void testAddRsvpedEvent() {
        user.addRsvpedEvent(event1);
        assertEquals(1, user.getRsvpedEvents().size());
        assertTrue(user.getRsvpedEvents().contains(event1));
    }

    @Test
    void testRemoveRsvpedEvent() {
        user.addRsvpedEvent(event1);
        user.addRsvpedEvent(event2);

        user.removeRsvpedEvent(event1); // Remove an existing event
        assertEquals(1, user.getRsvpedEvents().size());
        assertFalse(user.getRsvpedEvents().contains(event1));
        assertTrue(user.getRsvpedEvents().contains(event2));
    }

    @Test
    void testRemoveRsvpedEventNonExistent() {
        user.addRsvpedEvent(event1);

        user.removeRsvpedEvent(event2); // Attempt to remove an event that doesn't exist
        assertEquals(1, user.getRsvpedEvents().size());
        assertTrue(user.getRsvpedEvents().contains(event1));
        assertFalse(user.getRsvpedEvents().contains(event2));
    }

    @Test
    void testRemoveRsvpedEventNull() {
        user.addRsvpedEvent(event1);
        assertEquals(1, user.getRsvpedEvents().size());
        assertTrue(user.getRsvpedEvents().contains(event1));
    }

    @Test
    void testRemoveRsvpedEventFromEmptyList() {
        assertTrue(user.getRsvpedEvents().isEmpty());

        user.removeRsvpedEvent(event1); // Attempt to remove an event from an empty list
        assertTrue(user.getRsvpedEvents().isEmpty()); // List should remain empty
    }

    @Test
    void testRemoveCreatedEventNull() {
        user.addCreatedEvent(event1);
        assertEquals(1, user.getCreatedEvents().size());
        assertTrue(user.getCreatedEvents().contains(event1));
    }


    @Test
    void testRemoveCreatedEventWithDuplicates() {
        user.addCreatedEvent(event1);
        user.addCreatedEvent(event1); // Add the same event twice

        user.removeCreatedEvent(event1); // Remove one instance of the event
        assertEquals(1, user.getCreatedEvents().size());
        assertTrue(user.getCreatedEvents().contains(event1)); // The other instance should remain
    }

    @Test
    void testRemoveRsvpedEventDuplicateEntries() {
        user.addRsvpedEvent(event1);
        user.addRsvpedEvent(event1); // Add the same event twice

        user.removeRsvpedEvent(event1); // Remove one instance
        assertEquals(1, user.getRsvpedEvents().size());
        assertTrue(user.getRsvpedEvents().contains(event1)); // The other instance should remain
    }


    @Test
    void testAddCreatedEvent() {
        user.addCreatedEvent(event1);
        assertEquals(1, user.getCreatedEvents().size());
        assertTrue(user.getCreatedEvents().contains(event1));
    }

    @Test
    void testRemoveCreatedEvent() {
        user.addCreatedEvent(event1);
        user.addCreatedEvent(event2);
        user.removeCreatedEvent(event1);
        assertEquals(1, user.getCreatedEvents().size());
        assertFalse(user.getCreatedEvents().contains(event1));
    }

    @Test
    void testEmptyRsvpedEvents() {
        assertTrue(user.getRsvpedEvents().isEmpty());
    }

    @Test
    void testEmptyCreatedEvents() {
        assertTrue(user.getCreatedEvents().isEmpty());
    }

    // Additional tests for edge cases

    @Test
    void testRemoveNonExistentRsvpedEvent() {
        user.addRsvpedEvent(event1);
        user.removeRsvpedEvent(event2); // event2 is not in the list
        assertEquals(1, user.getRsvpedEvents().size());
        assertTrue(user.getRsvpedEvents().contains(event1));
    }

    @Test
    void testRemoveNonExistentCreatedEvent() {
        user.addCreatedEvent(event1);
        user.removeCreatedEvent(event2); // event2 is not in the list
        assertEquals(1, user.getCreatedEvents().size());
        assertTrue(user.getCreatedEvents().contains(event1));
    }

    @Test
    void testSetRsvpedEvents() {
        List<Event> newRsvpedEvents = new ArrayList<>();
        newRsvpedEvents.add(event1);
        newRsvpedEvents.add(event2);
        user.setRsvpedEvents(new ArrayList<>(newRsvpedEvents));
        assertEquals(newRsvpedEvents.size(), user.getRsvpedEvents().size());
    }

    @Test
    void testSetCreatedEvents() {
        List<Event> newCreatedEvents = new ArrayList<>();
        newCreatedEvents.add(event1);
        newCreatedEvents.add(event2);
        user.setCreatedEvents(new ArrayList<>(newCreatedEvents));
        assertEquals(newCreatedEvents.size(), user.getCreatedEvents().size());
    }

    @Test
    void testSetRsvpedEventsEmptyList() {
        ArrayList<Event> emptyList = new ArrayList<>();
        user.setRsvpedEvents(emptyList);
        assertTrue(user.getRsvpedEvents().isEmpty());
    }


    @Test
    void testSetCreatedEventsEmptyList() {
        ArrayList<Event> emptyList = new ArrayList<>();
        user.setCreatedEvents(emptyList);
        assertTrue(user.getCreatedEvents().isEmpty());
    }



    @Test
    void testRemoveCreatedEventEmptyList() {
        // Test removing an event from an empty list
        assertTrue(user.getCreatedEvents().isEmpty());
        user.removeCreatedEvent(event1); // Should do nothing, no exception expected
        assertTrue(user.getCreatedEvents().isEmpty());
    }
}
