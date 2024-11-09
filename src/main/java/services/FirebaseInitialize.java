package services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseInitialize {

    @PostConstruct
    public void initialize() {
        try {
            // retrieve path to service account file from the environment variable (need to set up on your own device)
            String serviceAccountPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

            if (serviceAccountPath == null || serviceAccountPath.isEmpty()) {
                throw new IllegalStateException("GOOGLE_APPLICATION_CREDENTIALS environment variable is not set.");
            }

            // load the service account key file
            FileInputStream serviceAccount = new FileInputStream(serviceAccountPath);

            // build Firebase options with the service account credentials
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://eventureuoft-11db1.firebaseio.com")
                    .build();

            // initialize Firebase app
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase has been initialized successfully.");
            } else {
                System.out.println("Firebase app already initialized.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to initialize Firebase: " + e.getMessage());
        }
    }
}
