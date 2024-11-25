package app.data_access;

import app.entity.Event.CommonEvent;
import app.entity.User.CommonUserFactory;
import app.use_case.filter_event.FilterEventUserDataAccessInterface;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;


import app.entity.Event.Event;
import app.entity.User.CommonUserFactory;
import app.use_case.modify_user.ModifyUserDataAccessInterface;
import app.use_case.register.RegisterUserDataAccessInterface;
import app.use_case.create_event.EventUserDataAccessInterface;
import app.use_case.login.LoginUserDataAccessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Firebase implementation of the DAO for storing user data.
 * This implementation persists data in Firestore.
 */
@Component
public class EventDAO implements EventUserDataAccessInterface, FilterEventUserDataAccessInterface {

    private final Firestore db;
    private final CollectionReference eventCollection;

    // Inject Firestore via constructor injection
    @Autowired
    public EventDAO(Firestore db, CommonUserFactory userFactory) {
        this.db = db;
        this.eventCollection = db.collection("Events");
    }

    /**
     * Function to save an event to the Firebase Database.
     *
     * @param event, the event we want to save.
     */
    public void saveEvent(Event event) {
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("organizer", event.getOrganizer());
        eventData.put("title", event.getTitle());
        eventData.put("description", event.getDescription());
        eventData.put("time", event.getDateTime());
        eventData.put("capacity", event.getCapacity());
        eventData.put("latitude", event.getLatitude());
        eventData.put("longitude", event.getLongitude());
        eventData.put("tags", event.getTags());

        try {
            DocumentReference docRef = eventCollection.document(event.getTitle());
            WriteResult result = docRef.set(eventData).get();
            System.out.println("Event saved at: " + result.getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error saving event to Firestore: " + e.getMessage());
        }
    }

    /**
     * Function to retrieve all the events
     */
    public List<Event> getEvents() {
        try {
            // Asynchronously retrieve the documents, using the reference to the users collection
            ApiFuture<QuerySnapshot> future = eventCollection.get();

            // Block on response to get the document snapshot
            QuerySnapshot document = future.get();

            // Prepare a list to hold the events
            List<Event> events = new ArrayList<>();

            // Iterate over the events in the collection
            for (QueryDocumentSnapshot doc : document.getDocuments()) {
                // Convert event into an Event object
                Event event = doc.toObject(Event.class);
                events.add(event);
            }
            // Return the list of events
            return events;

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("No events in Firestore: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Event> findEvents(List<String> tags) {
        try {
            System.out.println("I love what");
            CollectionReference eventsRef = eventCollection;
            ApiFuture<QuerySnapshot> future;
            if (!tags.isEmpty()) {
                future = eventsRef.whereArrayContainsAny("tags", tags).get();
            } else {
                // Fetch all events if no tags are provided
                future = eventsRef.get();
            }
            // Get query results
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            List<Event> events = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents) {
                Map<String, Object> data = document.getData();

                // Create Event object
                String organizer = (String) data.get("organizer");
                String title = (String) data.get("title");
                String description = (String) data.get("description");
                String time = (String) data.get("time");
                Long capacityLong = (Long) data.get("capacity"); // Retrieve as Long
                int capacity = capacityLong.intValue(); // Convert to int
                Double latitudeLong = (Double) data.get("latitude");
                float latitude = latitudeLong.floatValue();
                Double longitudeLong = (Double) data.get("longitude");
                float longitude = longitudeLong.floatValue();
//                float longitude = (float) data.get("longitude");
                List<String> eventTags = (List<String>) data.get("tags");

//                System.out.println("Naoroj is testing here,");

                Event event = new CommonEvent("", organizer, title, description, time, capacity, latitude, longitude, eventTags);
                events.add(event);
            }
//            for (Event event: events){
//                System.out.println(event.getTitle());
//            }
            return events;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error finding events in Firestore: " + e.getMessage());
            return new ArrayList<>(); // Return empty list on error

        }
    }
}
