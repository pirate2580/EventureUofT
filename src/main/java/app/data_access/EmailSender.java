package app.data_access;

import okhttp3.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EmailSender {
    private static final String ENV_FILE_PATH = "private/.env";
    private static final String API_KEY;
    private static final String API_BASE_URL;

    // Static block to load environment variables from the .env file
    static {
        String apiKey = null;
        String apiBaseUrl = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(ENV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("API_KEY=")) {
                    apiKey = line.substring("API_KEY=".length()).trim();
                } else if (line.startsWith("API_BASE_URL=")) {
                    apiBaseUrl = line.substring("API_BASE_URL=".length()).trim();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading .env file: " + e.getMessage());
        }

        API_KEY = apiKey;
        API_BASE_URL = apiBaseUrl;
    }

    /**
     * Sends an email using Mailgun.
     *
     * @param recipientEmail The recipient's email address.
     * @param subject        The subject of the email.
     * @param body           The body of the email.
     */
    public void sendEmail(String recipientEmail, String subject, String body) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("from", "<noreply@sandbox" + API_BASE_URL.substring(36) + ">")
                .add("to", recipientEmail)
                .add("subject", subject)
                .add("text", body)
                .build();

        Request request = new Request.Builder()
                .url(API_BASE_URL)
                .header("Authorization", Credentials.basic("api", API_KEY))
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Email sent successfully to " + recipientEmail);
            } else {
                System.err.println("Failed to send email to " + recipientEmail + ". Response: " + response.body().string());
            }
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
