package app.entity.Event;

import app.entity.User.CommonUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class CommonEventTest {
    private CommonEvent event;

    @BeforeEach
    void setUp() {
        // Create sample event for testing purposes
        event = new CommonEvent("E001", "Organizer1", "Event Title", "Event Description",
                "2024-11-30T10:00", 100, 43.6529f, -79.3849f, Arrays.asList("tag1", "tag2"));
    }

    @Test
    void testNoArgConstructor() {
        CommonEvent emptyEvent = new CommonEvent();
        assertEquals("", emptyEvent.getEventId());
        assertEquals("", emptyEvent.getOrganizer());
        assertEquals("", emptyEvent.getTitle());
        assertEquals(0, emptyEvent.getCapacity());
        assertTrue(emptyEvent.getTags().isEmpty());
    }

    @Test
        // Test if the constructor works
    void testAllArgConstructor() {
        assertEquals("E001", event.getEventId());
        assertEquals("Organizer1", event.getOrganizer());
        assertEquals("Event Title", event.getTitle());
        assertEquals(100, event.getCapacity());
        assertEquals(43.6529f, event.getLatitude());
        assertEquals(-79.3849f, event.getLongitude());
        assertEquals(Arrays.asList("tag1", "tag2"), event.getTags());
    }

    @Test
    void testGetters() {
        // Test if the getters work based off the constructor
        assertEquals("E001", event.getEventId());
        assertEquals("Organizer1", event.getOrganizer());
        assertEquals("Event Title", event.getTitle());
    }

    @Test
        // Self explanatory, test the setters and see if they equal the getter methods
    void testSetters() {
        event.setTitle("New Title");
        event.setDescription("New Description");
        event.setDateTime("2024-12-01T12:00");
        event.setCapacity(200);
        event.setTags(List.of("Gaming"));
        event.setOrganizer("A");
        event.setLatitude(45.0f);
        event.setLongitude(-80.0f);

        assertEquals("New Title", event.getTitle());
        assertEquals("A", event.getOrganizer());
        assertEquals("New Description", event.getDescription());
        assertEquals("2024-12-01T12:00", event.getDateTime());
        assertEquals(200, event.getCapacity());
        assertEquals(45.0f, event.getLatitude());
        assertEquals(-80.0f, event.getLongitude());
        assertEquals(List.of("Gaming"), event.getTags());
        assertEquals(false, event.isFull());
    }

    @Test
    void testEdgeCases() {
        // Include Edge case for capacity
        event.setCapacity(-1);
        assertEquals(-1, event.getCapacity());

        // Edge case for latitude (out of range)
        event.setLatitude(90.1f);
        assertEquals(90.1f, event.getLatitude());

        // Edge case for longitude (out of range)
        event.setLongitude(-180.1f);
        assertEquals(-180.1f, event.getLongitude());
    }

    @Test
    void testAttendeesListModification() {
        assertTrue(event.getAttendeesIdList().isEmpty());

        CommonUser user = new CommonUser("username", "email", "password");
        event.getAttendeesIdList().add(user);

        assertEquals(1, event.getAttendeesIdList().size());
        assertEquals(user, event.getAttendeesIdList().get(0));
    }

    @Test
    void testAddAttendee() {
        CommonUser user = new CommonUser("username1", "email1", "password1");

        assertTrue(event.getAttendeesIdList().isEmpty());
        event.addAttendee(user);

        assertEquals(1, event.getAttendeesIdList().size());
        assertEquals(user, event.getAttendeesIdList().get(0));

        event.addAttendee(user);
        assertEquals(1, event.getAttendeesIdList().size());

        event.setCapacity(1);
        CommonUser user2 = new CommonUser("username2", "email2", "password2");
        event.addAttendee(user2); // This should not add the user as the event is full
        assertEquals(1, event.getAttendeesIdList().size());
    }

}
