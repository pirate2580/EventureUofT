package app.use_case.view_rsvp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewRSVPInteractorTest {

    private ViewRSVPDataAccessInterface rsvpDataAccessObject;
    private ViewRSVPOutputBoundary viewRsvpEventPresenter;
    private ViewRSVPInteractor interactor;

    private List<String> fetchedRSVPEvents;
    private String usernamePassedToDAO;
    private boolean switchToHomeViewCalled;

    @BeforeEach
    void setUp() {
        fetchedRSVPEvents = null;
        usernamePassedToDAO = null;
        switchToHomeViewCalled = false;

        // Mock the Data Access Object
        rsvpDataAccessObject = username -> {
            usernamePassedToDAO = username;
            if ("validUser".equals(username)) {
                return Arrays.asList("Event1", "Event2", "Event3");
            } else if ("emptyUser".equals(username)) {
                return Collections.emptyList();
            }
            throw new IllegalArgumentException("Invalid username");
        };

        viewRsvpEventPresenter = new ViewRSVPOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewRSVPOutputData outputData) {
                fetchedRSVPEvents = outputData.getRSVPEvents();
            }

            @Override
            public void switchToHomeView() {
                switchToHomeViewCalled = true;
            }
        };

        interactor = new ViewRSVPInteractor(rsvpDataAccessObject, viewRsvpEventPresenter);
    }

    @Test
    void testExecute_Success() {
        ViewRSVPInputData inputData = new ViewRSVPInputData("validUser");

        interactor.execute(inputData);

        assertNotNull(fetchedRSVPEvents, "Fetched RSVP events should not be null.");
        assertEquals(Arrays.asList("Event1", "Event2", "Event3"), fetchedRSVPEvents, "Fetched events do not match expected list.");
        assertEquals("validUser", usernamePassedToDAO, "Username passed to DAO is incorrect.");
        assertFalse(switchToHomeViewCalled, "Switch to Home View should not have been called.");
    }

    @Test
    void testExecute_EmptyEvents() {
        ViewRSVPInputData inputData = new ViewRSVPInputData("emptyUser");

        interactor.execute(inputData);

        assertNotNull(fetchedRSVPEvents, "Fetched RSVP events should not be null.");
        assertTrue(fetchedRSVPEvents.isEmpty(), "Fetched events should be empty for user with no RSVPs.");
        assertEquals("emptyUser", usernamePassedToDAO, "Username passed to DAO is incorrect.");
        assertFalse(switchToHomeViewCalled, "Switch to Home View should not have been called.");
    }

    @Test
    void testExecute_InvalidUsername() {
        ViewRSVPInputData inputData = new ViewRSVPInputData("invalidUser");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData));
        assertEquals("Invalid username", exception.getMessage(), "Exception message is incorrect.");
        assertNull(fetchedRSVPEvents, "Fetched RSVP events should be null when an exception occurs.");
        assertEquals("invalidUser", usernamePassedToDAO, "Username passed to DAO is incorrect.");
        assertFalse(switchToHomeViewCalled, "Switch to Home View should not have been called.");
    }

    @Test
    void testSwitchToHomeView() {
        interactor.switchToHomeView();

        assertTrue(switchToHomeViewCalled, "Switch to Home View should have been called.");
    }
}