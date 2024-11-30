package app.entity.Event;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommonEventFactoryTest {
    private final CommonEventFactory factory = new CommonEventFactory();

    @Test
    void testCreateValidEvent() {
        // Valid inputs
        String eventId = "E001";
        String organizer = "Organizer1";
        String title = "Event Title";
        String description = "Event Description";
        String dateTime = "2024-11-30T10:00";
        int capacity = 100;
        float latitude = 43.65f;
        float longitude = -79.38f;
        List<String> tags = Arrays.asList("tag1", "tag2");

        // Create event for testing
        Event event = factory.create(eventId, organizer, title, description, dateTime, capacity, latitude, longitude, tags);

        // Use assert statements to make sure that the event is created properly
        assertNotNull(event, "Event should be created successfully");
        assertEquals(eventId, event.getEventId());
        assertEquals(organizer, event.getOrganizer());
        assertEquals(title, event.getTitle());
        assertEquals(description, event.getDescription());
        assertEquals(dateTime, event.getDateTime());
        assertEquals(capacity, event.getCapacity());
        assertEquals(latitude, event.getLatitude());
        assertEquals(longitude, event.getLongitude());
        assertEquals(tags, event.getTags());
    }

    @Test
    void testCreateWithNullTitleThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.create("E002", "Organizer1", null, "Description", "2024-11-30T10:00",
                    100, 43.65f, -79.38f, Arrays.asList("tag1", "tag2"));
        });
        assertEquals("Title cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateWithEmptyTitleThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.create("E002", "Organizer1", "", "Description", "2024-11-30T10:00",
                    100, 43.65f, -79.38f, Arrays.asList("tag1", "tag2"));
        });
        assertEquals("Title cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateWithNullDescriptionThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.create("E003", "Organizer1", "Title", null, "2024-11-30T10:00",
                    100, 43.65f, -79.38f, Arrays.asList("tag1", "tag2"));
        });
        assertEquals("Description cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateWithEmptyDescriptionThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.create("E003", "Organizer1", "Title", "", "2024-11-30T10:00",
                    100, 43.65f, -79.38f, Arrays.asList("tag1", "tag2"));
        });
        assertEquals("Description cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testCreateWithInvalidCapacityThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.create("E004", "Organizer1", "Title", "Description", "2024-11-30T10:00",
                    0, 43.65f, -79.38f, Arrays.asList("tag1", "tag2"));
        });
        assertEquals("Capacity must be greater than zero.", exception.getMessage());
    }

    @Test
    void testCreateWithInvalidLongitudeThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.create("E005", "Organizer1", "Title", "Description", "2024-11-30T10:00",
                    100, 43.65f, -81.0f, Arrays.asList("tag1", "tag2"));
        });
        assertEquals("Longitude must be between -79 and -80 (inclusive", exception.getMessage());
    }

    @Test
    void testCreateWithInvalidLatitudeThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            factory.create("E006", "Organizer1", "Title", "Description", "2024-11-30T10:00",
                    100, 42.0f, -79.38f, Arrays.asList("tag1", "tag2"));
        });
        assertEquals("Latitude must be between 43 and 44 (inclusive", exception.getMessage());
    }

    @Test
    void testCreateWithNullTagsDefaultsToEmpty() {
        Event event = factory.create("E007", "Organizer1", "Title", "Description", "2024-11-30T10:00",
                100, 43.65f, -79.38f, null);

        assertNotNull(event.getTags(), "Tags should not be null");
        assertTrue(event.getTags().isEmpty(), "Tags should default to an empty list");
    }
}
