package app.data_access;

import app.entity.Event.CommonEvent;
import app.entity.User.CommonUserFactory;
import app.interface_adapter.view_created_events.ViewCreatedEventsViewModel;
import app.use_case.filter_event.FilterEventUserDataAccessInterface;
import app.use_case.modify_event.ModifyEventUserDataAccessInterface;
import app.use_case.display_event.DisplayEventDataAccessInterface;
import app.use_case.notify_users.NotifyUserDataAccessInterface;
import app.use_case.rsvp_event.RSVPEventUserDataAccessInterface;
import app.use_case.view_created_events.ViewCreatedDataAccessInterface;
import app.use_case.view_event.ViewEventInputData;
import app.use_case.view_event.ViewEventUserDataAccessInterface;
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
public class EventDAO implements EventUserDataAccessInterface, DisplayEventDataAccessInterface, FilterEventUserDataAccessInterface, ModifyEventUserDataAccessInterface, ViewEventUserDataAccessInterface, RSVPEventUserDataAccessInterface, ViewCreatedDataAccessInterface, NotifyUserDataAccessInterface {

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
    @Override
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
     * */
    public ArrayList<CommonEvent> loadEvents() {
        try {
            // Asynchronously retrieve the documents
            ApiFuture<QuerySnapshot> future = eventCollection.get();

            // Block on response to get the document snapshot
            QuerySnapshot document = future.get();

            // Prepare a list to hold the events
            ArrayList<CommonEvent> events = new ArrayList<>();

            // Iterate over the events in the collection
            for (QueryDocumentSnapshot doc : document.getDocuments()) {
                // Convert Firestore document into a CommonEvent object
                CommonEvent event = doc.toObject(CommonEvent.class);
                events.add(event); // Add it to the list
            }

            // Return the list of events
            return events;

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error loading events from Firestore: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }

    public ArrayList<ArrayList<Object>> eventDetails() {
        try {
            // Asynchronously retrieve the documents
            ApiFuture<QuerySnapshot> future = eventCollection.get();

            // Block on response to get the document snapshot
            QuerySnapshot document = future.get();

            // Prepare a list to hold the events
            ArrayList<ArrayList<Object>> events = new ArrayList<>();

            // Iterate over the events in the collection
            for (QueryDocumentSnapshot doc : document.getDocuments()) {
                ArrayList<Object> details = new ArrayList<>();
                // Convert Firestore document into a CommonEvent object
                CommonEvent event = doc.toObject(CommonEvent.class);
                details.add(event.getEventId()); // Add it to the list
                details.add(event.getTitle());
                details.add(event.getLatitude());
                details.add(event.getLongitude());
                details.add(event.getTags());
                events.add(details);

            }
            return events;

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error loading events from Firestore: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }

    @Override
    public List<Event> findEvents(List<String> tags) {
        try {
//            System.out.println("I love what");
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

    /**
     * Function to delete an event from the Firebase Database.
     *
     * @param eventName the name of the event to delete.
     */
    @Override
    public void deleteEvent(String eventName) {
        try {
            // Reference to the document with the given name
            DocumentReference docRef = eventCollection.document(eventName);

            // Attempt to delete the document
            ApiFuture<WriteResult> writeResult = docRef.delete();

            // Wait for the operation to complete
            WriteResult result = writeResult.get();

//            System.out.println("Event deleted at");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error deleting event from Firestore: " + e.getMessage());
        }
    }

    @Override
    public Event viewEvent(String title) {
        try {
            // Reference to the document with the given title
            DocumentReference docRef = eventCollection.document(title);

            // Retrieve the document asynchronously
            ApiFuture<DocumentSnapshot> future = docRef.get();

            // Get the document snapshot
            DocumentSnapshot document = future.get();

            // Check if the document exists
            if (document.exists()) {
                // Convert the document to a CommonEvent object
                Map<String, Object> data = document.getData();

                if (data != null) {
                    String organizer = (String) data.get("organizer");
                    String description = (String) data.get("description");
                    String time = (String) data.get("time");
                    Long capacityLong = (Long) data.get("capacity");
                    int capacity = capacityLong.intValue();
                    Double latitudeLong = (Double) data.get("latitude");
                    float latitude = latitudeLong.floatValue();
                    Double longitudeLong = (Double) data.get("longitude");
                    float longitude = longitudeLong.floatValue();
                    List<String> tags = (List<String>) data.get("tags");

                    // Create and return the event object
                    return new CommonEvent(
                            title, // Title is used as the ID
                            organizer,
                            title,
                            description,
                            time,
                            capacity,
                            latitude,
                            longitude,
                            tags
                    );
                }
            } else {
//                System.out.println("No event found with title: " + title);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error viewing event from Firestore: " + e.getMessage());
        }

        // Return null if no event is found or an error occurs
        return null;
    }

    @Override
    public void addUserToRSVPList(String username, String eventId) {
        try {
            // Reference to the event document
            DocumentReference eventDocRef = eventCollection.document(eventId);

            // Add the username to the RSVP list in the event document
            ApiFuture<WriteResult> eventWriteResult = eventDocRef.update("rsvpList", com.google.cloud.firestore.FieldValue.arrayUnion(username));
            eventWriteResult.get(); // Wait for operation to complete

            System.out.println("User " + username + " added to RSVP list for event: " + eventId);

            // Reference to the user document in the "Users" collection
            CollectionReference userCollection = db.collection("Users");
            DocumentReference userDocRef = userCollection.document(username);

            // Add the event ID to the RSVPEvents array in the user document
            ApiFuture<WriteResult> userWriteResult = userDocRef.update("RSVPEvents", com.google.cloud.firestore.FieldValue.arrayUnion(eventId));
            userWriteResult.get(); // Wait for operation to complete

            System.out.println("Event " + eventId + " added to RSVPEvents list for user: " + username);

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error adding user to RSVP list and updating RSVPEvents: " + e.getMessage());
        }
    }

    /**
     * Create event DAO
     * @param usernameState
     * @param event to save onto the database
     */
    @Override
    public void saveEvent(String usernameState, Event event) {
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("creator_username", usernameState);
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

    @Override
    public List<String> getCreatedEvents(String username) {
        try {
            // Query Firestore for events where "creator_username" matches the given username
            ApiFuture<QuerySnapshot> future = eventCollection.whereEqualTo("creator_username", username).get();

            // Get query results
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            // Extract the titles of the matching events
            List<String> createdEventTitles = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents) {
                String title = document.getString("title");
                createdEventTitles.add(title);
            }

            return createdEventTitles; // Return the list of titles
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving created events for user " + username + ": " + e.getMessage());
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }

    @Override
    public void notifyUsers(String eventTitle) {
        try {
            // Get the event document by title
            DocumentReference eventDocRef = eventCollection.document(eventTitle);
            DocumentSnapshot eventDocSnapshot = eventDocRef.get().get();

            if (eventDocSnapshot.exists()) {
                // Extract the RSVP list
                List<String> rsvpList = (List<String>) eventDocSnapshot.get("rsvpList");

                if (rsvpList != null && !rsvpList.isEmpty()) {
                    // Fetch emails of users in RSVP list
                    List<String> emails = new ArrayList<>();
                    CollectionReference usersCollection = db.collection("Users");

                    for (String username : rsvpList) {
                        DocumentReference userDocRef = usersCollection.document(username);
                        DocumentSnapshot userDocSnapshot = userDocRef.get().get();

                        if (userDocSnapshot.exists()) {
                            String email = userDocSnapshot.getString("email");
                            if (email != null) {
                                emails.add(email);
                            }
                        }
                    }

                    // Send email notifications
                    EmailSender emailSender = new EmailSender(); // Use the Mailgun email sender class
                    for (String email : emails) {
                        emailSender.sendEmail(email, "Event Notification: " + eventTitle,
                                "You have RSVP'd for the event: " + eventTitle + ". Here are the details:\n\n" +
                                        "Description: " + eventDocSnapshot.getString("description") + "\n" +
                                        "Time: " + eventDocSnapshot.getString("time") + "\n" +
                                        "Organizer: " + eventDocSnapshot.getString("organizer") + "\n\n" +
                                        "We look forward to seeing you there!");
                    }
                } else {
                    System.out.println("No users found in RSVP list for event: " + eventTitle);
                }
            } else {
                System.out.println("Event with title " + eventTitle + " not found.");
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error notifying users for event " + eventTitle + ": " + e.getMessage());
        }
    }


    private List<String> getTags(Object rawTags) {
        if (rawTags instanceof List<?>) {
            List<?> rawList = (List<?>) rawTags;
            List<String> tags = new ArrayList<>();
            for (Object tag : rawList) {
                if (tag instanceof String) {
                    tags.add((String) tag);
                }
            }
            return tags;
        }
        return new ArrayList<>();
    }


    @Override
    public Event getEventById(String eventId) {
        try {
            DocumentReference docRef = eventCollection.document(eventId);

            ApiFuture<DocumentSnapshot> future = docRef.get();

            DocumentSnapshot document = future.get();

            if (document.exists()) {
                Map<String, Object> data = document.getData();

                if (data != null) {
                    String organizer = (String) data.get("organizer");
                    String title = (String) data.get("title");
                    String description = (String) data.get("description");
                    String time = (String) data.get("time");
                    Long capacityLong = (Long) data.get("capacity");
                    int capacity = capacityLong != null ? capacityLong.intValue() : 0;
                    Double latitudeLong = (Double) data.get("latitude");
                    float latitude = latitudeLong != null ? latitudeLong.floatValue() : 0.0f;
                    Double longitudeLong = (Double) data.get("longitude");
                    float longitude = longitudeLong != null ? longitudeLong.floatValue() : 0.0f;
                    List<String> tags = getTags(data.get("tags"));

                    return new CommonEvent(
                            eventId,
                            organizer,
                            title,
                            description,
                            time,
                            capacity,
                            latitude,
                            longitude,
                            tags
                    );
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving event from Firestore: " + e.getMessage());
        }

        return null;
    }
}
