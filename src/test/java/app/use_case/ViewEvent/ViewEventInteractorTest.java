package app.use_case.ViewEvent;

import app.entity.Event.CommonEvent;
import app.entity.Event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import app.use_case.view_event.ViewEventInputData;
import app.use_case.view_event.ViewEventInteractor;
import app.use_case.view_event.ViewEventOutputData;
import app.use_case.view_event.ViewEventOutputBoundary;
import app.use_case.view_event.ViewEventInputBoundary;
import app.use_case.view_event.ViewEventUserDataAccessInterface;

import static org.junit.jupiter.api.Assertions.*;

class ViewEventInteractorTest {

    private ViewEventUserDataAccessInterface eventDataAccessObject;
    private ViewEventOutputBoundary viewEventPresenter;
    private ViewEventInteractor interactor;
    private Event returnedEvent;
    private String failMessage;

    @BeforeEach
    void setUp() {
        returnedEvent = null;
        failMessage = null;

        eventDataAccessObject = title -> {
            if ("Existing Event".equals(title)) {
                return new CommonEvent("event1", "Organizer1", "Existing Event", "Description1",
                        "2024-10-10 10:00", 50, 43.0f, -79.0f, List.of("sports", "fitness"));
            }
            return null;
        };

        viewEventPresenter = new ViewEventOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewEventOutputData outputData) {
                returnedEvent = outputData.getEvent();
            }

            @Override
            public void prepareFailView(String errorMessage) {
                failMessage = errorMessage;
            }

            @Override
            public void switchToHomeView() {
                failMessage = "Switched to Home View";
            }
        };

        interactor = new ViewEventInteractor(eventDataAccessObject, viewEventPresenter);
    }

    @Test
    void testExecute_SuccessfulEventView() {
        ViewEventInputData inputData = new ViewEventInputData("Existing Event");

        interactor.execute(inputData);

        assertNotNull(returnedEvent, "Returned event should not be null.");
        assertEquals("Existing Event", returnedEvent.getTitle(), "Event title should match.");
        assertEquals("Organizer1", returnedEvent.getOrganizer(), "Organizer should match.");
        assertNull(failMessage, "Fail message should be null for successful event view.");
    }

    @Test
    void testExecute_EventNotFound() {
        ViewEventInputData inputData = new ViewEventInputData("Nonexistent Event");

        interactor.execute(inputData);

        assertNull(returnedEvent, "Returned event should be null for nonexistent event.");
        assertNull(failMessage, "Fail message should be null as event not found is a valid case.");
    }

    @Test
    void testSwitchToHomeView() {
        interactor.switchToHomeView();

        assertEquals("Switched to Home View", failMessage, "Message should indicate switch to home view.");
    }


    @Test
    void testExecute_NullInputData() {
        interactor.execute(null);

        assertNull(returnedEvent, "Returned event should be null when input data is null.");
        assertEquals("Event title cannot be null.", failMessage, "Fail message should indicate null input data.");
    }

    @Test
    void testExecute_NullTitle() {
        ViewEventInputData inputData = new ViewEventInputData(null);

        interactor.execute(inputData);

        assertNull(returnedEvent, "Returned event should be null for null title.");
        assertEquals("Event title cannot be null.", failMessage, "Fail message should indicate null title.");
    }
}
