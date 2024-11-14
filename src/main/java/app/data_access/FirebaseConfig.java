package app.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        String serviceAccountPath = "private/serviceAccount.json";

        FileInputStream serviceAccount = new FileInputStream(serviceAccountPath);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://eventureuoft-11db1.firebaseio.com")
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp app = FirebaseApp.initializeApp(options);
            System.out.println("FirebaseApp initialized successfully.");
            return app;
        } else {
            System.out.println("FirebaseApp already initialized.");
            return FirebaseApp.getInstance();
        }
    }

    @Bean
    public Firestore firestore(FirebaseApp firebaseApp) {
        // Ensures FirebaseApp is initialized before Firestore
        return FirestoreClient.getFirestore(firebaseApp);
    }
}
