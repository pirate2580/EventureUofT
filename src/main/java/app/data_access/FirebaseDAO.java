package app.data_access;

import app.entity.Event.CommonEvent;
import app.entity.User.CommonUserFactory;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import app.entity.User.User;
import app.entity.User.UserFactory;
import app.entity.Event.Event;
import app.use_case.register.RegisterUserDataAccessInterface;
import app.use_case.create_event.EventUserDataAccessInterface;
import app.use_case.login.LoginUserDataAccessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Firebase implementation of the DAO for storing user data.
 * This implementation persists data in Firestore.
 */
@Component
public class FirebaseDAO implements RegisterUserDataAccessInterface, EventUserDataAccessInterface, LoginUserDataAccessInterface {

    private final Firestore db;
    private final CollectionReference usersCollection;
    private final CollectionReference eventCollection;

    // Inject Firestore via constructor injection
    @Autowired
    public FirebaseDAO(Firestore db) {
        this.db = db;
        this.usersCollection = db.collection("Users");
        this.eventCollection = db.collection("Events");
    }

    @Override
    public boolean existsByUsername(String identifier) {
        try {
            return usersCollection.document(identifier).get().get().exists();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void save(User user) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", user.getUsername());
        userData.put("email", user.getEmail());
        userData.put("password", user.getPassword());

        try {
            DocumentReference docRef = usersCollection.document(user.getUsername());
            WriteResult result = docRef.set(userData).get();
            System.out.println("User saved at: " + result.getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error saving user to Firestore: " + e.getMessage());
        }
    }

    public void printUsers() {
        try {
            ApiFuture<QuerySnapshot> future = usersCollection.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                System.out.println("User: " + document.getId() + ", Data: " + document.getData());
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving users from Firestore: " + e.getMessage());
        }
    }

    /**
     * Function to save an event to the Firebase Database.
     * @param event, the event we want to save.
     * */
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

    @Override
    public User findUserByUsername(String username) {
        try {
            // Get a reference to the user document with the given username
            DocumentReference docRef = usersCollection.document(username);

            // Asynchronously retrieve the document
            ApiFuture<DocumentSnapshot> future = docRef.get();

            // Block on response to get the document snapshot
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                // Convert document data to a User object
                Map<String, Object> data = document.getData();

                // Extract user fields from the data map
                String email = (String) data.get("email");
                String password = (String) data.get("password");

                // Create and return a User object
                UserFactory userFactory = new CommonUserFactory();
                User user = userFactory.create(username, email, password);
                System.out.println("Hello this is Naoroj yo, I'm skyler white yo");
                return user;
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error finding user in Firestore: " + e.getMessage());
            return null;
        }
    }
}
