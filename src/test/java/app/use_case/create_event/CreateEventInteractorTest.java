package app.use_case.create_event;

import app.entity.Event.CommonEventFactory;
import app.entity.Event.Event;
import app.entity.Event.EventFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateEventInteractorTest {

    private EventUserDataAccessInterface eventDataAccessObject;
    private EventOutputBoundary eventPresenter;
    private EventFactory eventFactory;
    private EventInteractor interactor;
    private Event savedEvent;
    private EventOutputData outputData;
    private String failMessage;
    private boolean isSwitchToHomeViewCalled;

    @BeforeEach
    void setUp() {
        savedEvent = null;
        outputData = null;
        failMessage = null;
        isSwitchToHomeViewCalled = false;

        eventDataAccessObject = (usernameState, event) -> savedEvent = event;

        eventPresenter = new EventOutputBoundary() {
            @Override
            public void prepareSuccessView(EventOutputData outputData) {
                CreateEventInteractorTest.this.outputData = outputData;
            }

            @Override
            public void prepareFailView(String errorMessage) {
                failMessage = errorMessage;
            }

            @Override
            public void switchToHomeView() {
                isSwitchToHomeViewCalled = true;
            }
        };

        eventFactory = new CommonEventFactory();

        interactor = new EventInteractor(eventDataAccessObject, eventPresenter, eventFactory);
    }

    @Test
    void testExecute_Success() {
        List<String> tags = Arrays.asList("test", "sample");
        EventInputData inputData = new EventInputData(
                "username123",
                "event123",
                "UofTer",
                "Sample Event",
                "This is a test event organized by UofTer",
                "2024-10-10 10:00",
                30,
                43.5f,
                -79.5f,
                tags
        );

        interactor.execute(inputData);

        assertNotNull(savedEvent);
        assertEquals("event123", savedEvent.getEventId());
        assertEquals("Sample Event", savedEvent.getTitle());
        assertEquals("UofTer", savedEvent.getOrganizer());

        assertNotNull(outputData);
        assertEquals("Sample Event", outputData.getTitle());
        assertFalse(outputData.isUseCaseFailed());

        assertNull(failMessage);

        assertFalse(isSwitchToHomeViewCalled, "switchToHomeView should not have been called automatically");
    }

    @Test
    void testSwitchToHomeView() {
        interactor.switchToHomeView();

        assertTrue(isSwitchToHomeViewCalled, "switchToHomeView should have been called on the presenter");
    }

    @Test
    void testExecute_EventFactoryReturnsNull() {
        eventFactory = (eventId, organizer, title, description, dateTime, capacity, latitude, longitude, tags) -> null;

        interactor = new EventInteractor(eventDataAccessObject, eventPresenter, eventFactory);

        List<String> tags = Arrays.asList("test", "sample");
        EventInputData inputData = new EventInputData(
                "username123",
                "event456",
                "UofTer",
                "Another Event",
                "This is another test event",
                "2024-11-11 11:00",
                50,
                43.5f,
                -79.5f,
                tags
        );

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> interactor.execute(inputData));
        assertEquals("EventFactory returned null check your input or factory implementation.", exception.getMessage());

        assertNull(outputData);
        assertNull(failMessage);

        assertFalse(isSwitchToHomeViewCalled, "switchToHomeView should not have been called");
    }

    @Test
    void testExecute_InvalidDateTimeFormat() {
        List<String> tags = Arrays.asList("test", "sample");
        EventInputData inputData = new EventInputData(
                "username123",
                "eventInvalidDateTime",
                "UofTer",
                "Invalid DateTime Event",
                "This event has an invalid dateTime format",
                "InvalidDateTime",
                30,
                43.5f,
                -79.5f,
                tags
        );

        eventFactory = (eventId, organizer, title, description, dateTime, capacity, latitude, longitude, tags1) -> {
            throw new IllegalArgumentException("Invalid dateTime format");
        };

        interactor = new EventInteractor(eventDataAccessObject, eventPresenter, eventFactory);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData));
        assertTrue(exception.getMessage().contains("Invalid dateTime format"));

        assertNull(savedEvent);
        assertNull(outputData);
        assertNull(failMessage);

        assertFalse(isSwitchToHomeViewCalled, "switchToHomeView should not have been called");
    }

    @Test
    void testExecute_NullTags() {
        EventInputData inputData = new EventInputData(
                "username123",
                "eventNullTags",
                "UofTer",
                "No Tags Event",
                "This event has null tags",
                "2024-10-10 10:00",
                30,
                43.5f,
                -79.5f,
                null
        );

        interactor.execute(inputData);

        assertNotNull(savedEvent);
        assertEquals(Collections.emptyList(), savedEvent.getTags());

        assertNotNull(outputData);
        assertEquals("No Tags Event", outputData.getTitle());
        assertFalse(outputData.isUseCaseFailed());

        assertNull(failMessage);

        assertFalse(isSwitchToHomeViewCalled, "switchToHomeView should not have been called automatically");
    }
}