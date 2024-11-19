package app.data_access;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import app.entity.User.User;
import app.entity.User.CommonUserFactory;
import app.use_case.modify_user.ModifyUserDataAccessInterface;
import app.use_case.register.RegisterUserDataAccessInterface;
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
public class FirebaseDAO implements RegisterUserDataAccessInterface, ModifyUserDataAccessInterface {

    private final Firestore db;
    private final CollectionReference usersCollection;
    private final CommonUserFactory userFactory;

    // Inject Firestore via constructor injection
    @Autowired
    public FirebaseDAO(Firestore db, CommonUserFactory userFactory) {
        this.db = db;
        this.usersCollection = db.collection("Users");
        this.userFactory = userFactory;
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

    //retrieve username
    public User getUserByUsername(String username) {
        try {
            DocumentReference docRef = usersCollection.document(username);
            var snapshot = docRef.get().get();
            if (snapshot.exists()) {
                String retrievedUsername = snapshot.getString("username");
                String email = snapshot.getString("email");
                String password = snapshot.getString("password");

                // Using UserFactory to create a User object
                return userFactory.create(retrievedUsername, email, password);
            } else {
                System.out.println("User not found with username: " + username);
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error getting user from Firestore: " + e.getMessage());
            return null;
        }
    }

    //check existence of userId
    public boolean doesUsernameExist(String userId) {
        try {
            return usersCollection.document(userId).get().get().exists();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error checking if user ID exists in Firestore: " + e.getMessage());
            return false;
        }
    }

    //check if new email duplicated
    public boolean doesEmailExist(String email) {
        try {
            ApiFuture<QuerySnapshot> future = usersCollection.whereEqualTo("email", email).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            return !documents.isEmpty();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error checking if email exists in Firestore: " + e.getMessage());
            return false;
        }
    }

    //check if successfully updated the user
    public boolean updateUser(User user) {
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("username", user.getUsername());
        updatedData.put("email", user.getEmail());
        updatedData.put("password", user.getPassword());

        try {
            DocumentReference docRef = usersCollection.document(user.getUsername());
            WriteResult result = docRef.set(updatedData).get();
            System.out.println("User updated at: " + result.getUpdateTime());
            return true;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error updating user in Firestore: " + e.getMessage());
            return false;
        }
    }
}
