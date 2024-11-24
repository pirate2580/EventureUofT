package app.entity.Event;

import java.util.List;

/**
 * Factory for creating CommonEvents
 */
public class CommonEventFactory implements EventFactory {
    public Event create(String eventId, String organizer, String title, String description, String dateTime,
                        int capacity, float latitude, float longitude, List<String> tags) {
        // Validate inputs
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        if (dateTime == null || dateTime.isEmpty()) {
            throw new IllegalArgumentException("DateTime cannot be null or empty.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        if (tags == null) {
            tags = List.of();
        }
        return new CommonEvent (eventId, organizer, title, description, dateTime,
                capacity, latitude, longitude, tags);
    }
}
