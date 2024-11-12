package app.data_access;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseInitialize {

    @PostConstruct
    public void initialize() {
        try {
            // path to the service account JSON file
            String serviceAccountPath = "private/serviceAccount.json";

            // check if the file exists and is readable
            File file = new File(serviceAccountPath);
            if (!file.exists() || !file.canRead()) {
                throw new IllegalStateException("Service account key file not found or unreadable at: " + serviceAccountPath);
            }

            // debugging
            System.out.println("Using service account credentials at: " + serviceAccountPath);

            // Load the service account key file
            FileInputStream serviceAccount = new FileInputStream(serviceAccountPath);

            // build Firebase options with the service account credentials
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://eventureuoft-11db1.firebaseio.com") // Replace with your Firebase database URL
                    .build();

            // initialize Firebase app if not already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase has been initialized successfully.");
            } else {
                System.out.println("Firebase app already initialized.");
            }
        // error messages
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to initialize Firebase: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unexpected error during Firebase initialization: " + e.getMessage());
        }
    }
}
