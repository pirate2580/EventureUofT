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
        // Initialize a CommonUser and sample events
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
        user.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmail());
    }

    @Test
    void testSetPassword() {
        user.setPassword("newpassword123");
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
        user.removeRsvpedEvent(event1);
        assertEquals(1, user.getRsvpedEvents().size());
        assertFalse(user.getRsvpedEvents().contains(event1));
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
}
