//package services;
//import org.springframework.stereotype.Service;
//
//import java.entity.User.User;
//
//@Service
//public class FirebaseService {
//    public String saveUserDetails(User user) {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("users").document(user.getName()).set()
//        return collectionsApiFuture.get().getUpdateTime().toString(); // print when user added
//    }
//
//    public User getUserDetails(String name){
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        DocumentReference documentReference = dbFirestore.collection("users").document(name);
//        ApiFuture<DocumentSnapshot> snap = documentReference.get();
//
//        DocSnapshot document = snap.get();
//
//        User user = null;
//        if(document.exists()){
//            user = document.toObject(User.class);
//            return user;
//        } else {
//            return null;
//        }
//    }
//}
