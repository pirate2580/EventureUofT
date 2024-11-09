package app.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import app.entity.User.CommonUser;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    // save user details in database
    public String saveUserDetails(CommonUser user) throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore
                .collection("users")
                .document(user.getUsername()) // Use the username as the document ID
                .set(user); // Save the user object in Firestore

        // return the time the write operation completed
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    // get user details from database
    public CommonUser getUserDetails(String name) throws Exception {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("users").document(name);
        ApiFuture<DocumentSnapshot> snap = documentReference.get();

        // get document snapshot
        DocumentSnapshot document = snap.get();

        // check if document exists
        if (document.exists()) {
            // convert the document to a CommonUser object
            return document.toObject(CommonUser.class);
        } else {
            // return null if the document does not exist
            return null;
        }
    }
}
