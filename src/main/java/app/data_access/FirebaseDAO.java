package app.data_access;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FirebaseFirestore;
import com.google.cloud.firestore.WriteResult;

import app.entity.User.User;
import app.use_case.register.RegisterUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Firebase implementation of the DAO for storing user data.
 * This implementation persists data in Firestore.
 */
public class FirebaseDAO implements RegisterUserDataAccessInterface {

    private final FirebaseFirestore db;
    private final CollectionReference usersCollection;

    public FirebaseDAO() {
        // Initialize Firebase Firestore
        this.db = FirebaseFirestore.getInstance();
        this.usersCollection = db.collection("Users"); // Replace "Users" with your Firestore collection name
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
        userData.put("password", user.getPassword()); // Ideally, store hashed passwords

        try {
            DocumentReference docRef = usersCollection.document(user.getUsername());
            WriteResult result = docRef.set(userData).get(); // Wait for Firestore operation to complete
            System.out.println("User saved at: " + result.getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error saving user to Firestore: " + e.getMessage());
        }
    }

    public void printUsers() {
        try {
            usersCollection.get().get().getDocuments().forEach(doc -> {
                System.out.println("User: " + doc.getId() + ", Data: " + doc.getData());
            });
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving users from Firestore: " + e.getMessage());
        }
    }
}
