package app.entity.Event;

import java.util.List;

public class CommonEventFactory implements EventFactory {

    @Override
    public Event create(String eventId, String organizer, String title, String description, String dateTime,
                        int capacity, float latitude, float longitude, List<String> tags) {
        // Log inputs for debugging
//        System.out.println("Creating event with inputs:");
//        System.out.printf("ID: %s, Organizer: %s, Title: %s, Description: %s, DateTime: %s%n",
//                eventId, organizer, title, description, dateTime);
//        System.out.printf("Capacity: %d, Latitude: %d, Longitude: %d, Tags: %s%n",
//                capacity, latitude, longitude, tags);

        System.out.println(eventId);
        System.out.println(organizer);
        System.out.println(title);
        System.out.println(description);
        System.out.println(dateTime);
        System.out.println(capacity);
        System.out.println(latitude);
        System.out.println(longitude);
        System.out.println(tags);


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
            tags = List.of(); // Default to empty list if null
        }

        // Create and return the CommonEvent instance
        Event event = new CommonEvent(eventId, organizer, title, description, dateTime,
                capacity, latitude, longitude, tags);

        System.out.println("Event created successfully: " + event);
        return event;
    }

}
