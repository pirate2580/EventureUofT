package app.use_case.RSVPEvent;

import static org.junit.jupiter.api.Assertions.*;
import app.entity.Event.CommonEvent;
import app.entity.User.CommonUser;
import app.use_case.rsvp_event.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class RSVPEventInteractorTest {

    private RSVPEventUserDataAccessInterface rsvpEventDataAccessObject;
    private RSVPEventOutputBoundary rsvpEventPresenter;
    private RSVPEventInteractor interactor;
    private List<String> rsvpList;
    private String failMessage;
    private RSVPEventOutputData successOutputData;

    @BeforeEach
    void setUp() {
        rsvpList = new ArrayList<>();
        failMessage = null;
        successOutputData = null;

        rsvpEventDataAccessObject = new RSVPEventUserDataAccessInterface() {
            @Override
            public void addUserToRSVPList(String username, String eventId) {
                if ("validEvent".equals(eventId)) {
                    rsvpList.add(username);
                } else {
                    throw new IllegalArgumentException("Event not found");
                }
            }
        };

        rsvpEventPresenter = new RSVPEventOutputBoundary() {
            @Override
            public void prepareSuccessView(RSVPEventOutputData outputData) {
                successOutputData = outputData;
            }

            @Override
            public void prepareFailView(String errorMessage) {
                failMessage = errorMessage;
            }
        };

        interactor = new RSVPEventInteractor(rsvpEventDataAccessObject, rsvpEventPresenter);
    }

    @Test
    void testExecute_SuccessfulRSVP() {
        RSVPEventInputData inputData = new RSVPEventInputData("user1", "validEvent");

        interactor.execute(inputData);

        assertTrue(rsvpList.contains("user1"), "The user should be added to the RSVP list.");
        assertNotNull(successOutputData, "Success output data should not be null.");
        assertEquals("user1", successOutputData.getUsername(), "The username should match.");
        assertEquals("validEvent", successOutputData.getEventTitle(), "The event ID should match.");
        assertNull(failMessage, "There should be no failure message for a successful RSVP.");
    }

    @Test
    void testExecute_InvalidEventID() {
        RSVPEventInputData inputData = new RSVPEventInputData("user2", "invalidEvent");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData));
        assertEquals("Event not found", exception.getMessage(), "The exception message should indicate the event is not found.");
        assertFalse(rsvpList.contains("user2"), "The user should not be added to the RSVP list for an invalid event.");
        assertNull(successOutputData, "Success output data should be null for a failed RSVP.");
    }

    @Test
    void testExecute_NullUsername() {
        RSVPEventInputData inputData = new RSVPEventInputData(null, "validEventId");

        interactor.execute(inputData);

        assertEquals("Username cannot be null.", failMessage, "Failure message should indicate null username.");
        assertNull(successOutputData, "Filtered events should remain null for null username.");
    }

    @Test
    void testExecute_NullEventId() {
        RSVPEventInputData inputData = new RSVPEventInputData("validUsername", null);

        interactor.execute(inputData);

        assertEquals("Event ID cannot be null.", failMessage, "Failure message should indicate null eventId.");
        assertNull(successOutputData, "Filtered events should remain null for null eventId.");
    }
}
