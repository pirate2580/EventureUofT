package app.data_access;

import okhttp3.*;
import org.json.JSONObject;

import java.io.File;

public class EmailSender {
    private static final String API_BASE_URL = "https://api.mailgun.net/v3/sandbox9c50024e32714e008fc2b9c8a247a85a.mailgun.org/messages";

    private static final String API_KEY = "166c053debe0fa7a91d668be6cf4b4b1-c02fd0ba-b0e3a531";
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
                .add("from", "Your App Name <noreply@sandbox9c50024e32714e008fc2b9c8a247a85a.mailgun.org>")
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
