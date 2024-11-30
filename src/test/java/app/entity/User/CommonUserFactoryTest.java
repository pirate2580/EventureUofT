package app.entity.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonUserFactoryTest {

    private final CommonUserFactory factory = new CommonUserFactory();

    @Test
    void testCreateValidUser() {
        // Make sample inputs
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        // Create sample user
        User user = factory.create(username, email, password);

        // Make sure that the correct messages appear
        assertNotNull(user, "Factory should create a non-null User object");
        assertTrue(user instanceof CommonUser, "Created user should be an instance of CommonUser");
        assertEquals(username, user.getUsername(), "Username should match the provided value");
        assertEquals(email, user.getEmail(), "Email should match the provided value");
        assertEquals(password, user.getPassword(), "Password should match the provided value");
    }

    @Test
    void testCreateWithNullValues() {
        // Make all attributes null
        String username = null;
        String email = null;
        String password = null;

        // Create sample user with these inputs
        User user = factory.create(username, email, password);

        // Make sure that the correct null messages show up
        assertNotNull(user, "Factory should create a non-null User object even with null values");
        assertNull(user.getUsername(), "Username should be null if provided as null");
        assertNull(user.getEmail(), "Email should be null if provided as null");
        assertNull(user.getPassword(), "Password should be null if provided as null");
    }

    @Test
    void testCreateWithEmptyStrings() {
        // Case with empty strings, here are the inputs
        String username = "";
        String email = "";
        String password = "";

        // Same logic as before, just create a sample user
        User user = factory.create(username, email, password);

        // Make sure the correct messages appear for empty strings
        assertNotNull(user, "Factory should create a non-null User object even with empty strings");
        assertEquals("", user.getUsername(), "Username should match the provided empty string");
        assertEquals("", user.getEmail(), "Email should match the provided empty string");
        assertEquals("", user.getPassword(), "Password should match the provided empty string");
    }
}
