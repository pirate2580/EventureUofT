package app.data_access;

import okhttp3.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class provides functionality for sending emails using the Mailgun API.
 * It reads the API key and base URL from a `.env` file for secure configuration.
 */
public class EmailSender {
    // Declare all static variables
    private static final String ENV_FILE_PATH = "private/.env";
    private static final String API_KEY;
    private static final String API_BASE_URL;

    // Static block to load environment variables from the .env file
    static {
        // Initialize string and API key to null
        String apiKey = null;
        String apiBaseUrl = null;

        // Attempt to read environment variables from the .env file
        try (BufferedReader reader = new BufferedReader(new FileReader(ENV_FILE_PATH))) {
            String line;
            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the API_KEY and extract its value
                if (line.startsWith("API_KEY=")) {
                    apiKey = line.substring("API_KEY=".length()).trim();
                }
                // Check if the line contains the API_BASE_URL and extract its value
                else if (line.startsWith("API_BASE_URL=")) {
                    apiBaseUrl = line.substring("API_BASE_URL=".length()).trim();
                }
            }
        } catch (IOException e) {
            // Handle any errors that occur while reading the .env file
            System.err.println("Error reading .env file: " + e.getMessage());
        }

        // Assign the extracted values to the static final variables
        API_KEY = apiKey;
        API_BASE_URL = apiBaseUrl;

    }

    /**
     * Sends an email using the Mailgun API.
     * @param recipientEmail, recipient's email address.
     * @param subject, the subject of the email.
     * @param body, the body of the email.
     * @throws IllegalStateException if the API key or base URL is not initialized.
     */
    public void sendEmail(String recipientEmail, String subject, String body) {
        // Ensure API_KEY and API_BASE_URL are initialized
        if (API_KEY == null || API_BASE_URL == null) {
            throw new IllegalStateException("API_KEY or API_BASE_URL is not initialized. Check the .env file.");
        }

        // Create an OkHttpClient instance for making HTTP requests
        OkHttpClient client = new OkHttpClient();

        // Build the request body with the required email details
        RequestBody formBody = new FormBody.Builder()
                .add("from", "<noreply@sandbox" + API_BASE_URL.substring(36) + ">")
                .add("to", recipientEmail)
                .add("subject", subject)
                .add("text", body)
                .build();

        // Build the HTTP POST request with authorization header
        Request request = new Request.Builder()
                .url(API_BASE_URL)
                .header("Authorization", Credentials.basic("api", API_KEY))
                .post(formBody)
                .build();

        // Execute the request and handle the response
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Email sent successfully to " + recipientEmail);
            } else {
                System.err.println("Failed to send email to " + recipientEmail + ". Response: " + response.body().string());
            }
        } catch (Exception e) {
            // Handle exceptions during email sending
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}