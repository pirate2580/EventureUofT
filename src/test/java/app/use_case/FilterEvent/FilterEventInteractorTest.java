package app.use_case.FilterEvent;

import app.entity.Event.CommonEvent;
import app.entity.Event.Event;
import app.use_case.filter_event.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterEventInteractorTest {

    private FilterEventUserDataAccessInterface filterEventDataAccessObject;
    private FilterEventOutputBoundary filterEventPresenter;
    private FilterEventInteractor interactor;
    private List<Event> filteredEvents;
    private String failMessage;

    @BeforeEach
    void setUp() {
        filteredEvents = null;
        failMessage = null;

        filterEventDataAccessObject = tags -> {
            if (tags.contains("sports")) {
                return List.of(
                        new CommonEvent("event1", "Organizer1", "Sports Event", "Description1",
                                "2024-10-10 10:00", 50, 43.0f, -79.0f, List.of("sports", "fitness")),
                        new CommonEvent("event2", "Organizer2", "Soccer Event", "Description2",
                                "2024-11-11 11:00", 30, 43.1f, -79.1f, List.of("sports", "soccer"))
                );
            } else if (tags.contains("music")) {
                return List.of(
                        new CommonEvent("event3", "Organizer3", "Music Festival", "Description3",
                                "2024-12-12 12:00", 100, 43.2f, -79.2f, List.of("music", "festival"))
                );
            }
            return new ArrayList<>();
        };

        filterEventPresenter = new FilterEventOutputBoundary() {
            @Override
            public void prepareSuccessView(FilterEventOutputData outputData) {
                filteredEvents = outputData.getFilteredEvents();
            }

            @Override
            public void prepareFailView(String errorMessage) {
                failMessage = errorMessage;
            }

            @Override
            public void switchToHomeView() {
            }
        };

        interactor = new FilterEventInteractor(filterEventDataAccessObject, filterEventPresenter);
    }

    @Test
    void testExecute_FilterBySports_Success() {
        FilterEventInputData inputData = new FilterEventInputData(List.of("sports"));

        interactor.execute(inputData);

        assertNotNull(filteredEvents);
        assertEquals(2, filteredEvents.size());
        assertEquals("Sports Event", filteredEvents.get(0).getTitle());
        assertEquals("Soccer Event", filteredEvents.get(1).getTitle());
        assertNull(failMessage);
    }

    @Test
    void testExecute_FilterByMusic_Success() {
        FilterEventInputData inputData = new FilterEventInputData(List.of("music"));

        interactor.execute(inputData);

        assertNotNull(filteredEvents);
        assertEquals(1, filteredEvents.size());
        assertEquals("Music Festival", filteredEvents.get(0).getTitle());
        assertNull(failMessage);
    }

    @Test
    void testExecute_NoMatchingTags() {
        FilterEventInputData inputData = new FilterEventInputData(List.of("unknownTag"));

        interactor.execute(inputData);

        assertNotNull(filteredEvents);
        assertTrue(filteredEvents.isEmpty());
        assertNull(failMessage);
    }

    @Test
    void testExecute_NullTags() {
        FilterEventInputData inputData = new FilterEventInputData(null);

        interactor.execute(inputData);

        assertEquals("Please provide a valid filter event", failMessage);
    }

    @Test
    void testExecute_EmptyTags() {
        FilterEventInputData inputData = new FilterEventInputData(new ArrayList<>());

        interactor.execute(inputData);

        assertNotNull(filteredEvents);
        assertTrue(filteredEvents.isEmpty());
        assertNull(failMessage);
    }

    @Test
    void testExecute_DataAccessThrowsException() {
        filterEventDataAccessObject = tags -> {
            throw new RuntimeException("Data access error");
        };

        interactor = new FilterEventInteractor(filterEventDataAccessObject, filterEventPresenter);

        FilterEventInputData inputData = new FilterEventInputData(List.of("sports"));

        Exception exception = assertThrows(RuntimeException.class, () -> interactor.execute(inputData));

        assertEquals("Data access error", exception.getMessage());
        assertNull(filteredEvents);
    }

    @Test
    void testSwitchToHomeView() {
        interactor.switchToHomeView();

        assertDoesNotThrow(() -> interactor.switchToHomeView());
    }

    @Test
    void testExecute_NullFilterEventInputData() {
        interactor.execute(null);

        assertEquals("Please provide a valid filter event", failMessage,
                "Fail message should indicate invalid input when input data is null.");
        assertNull(filteredEvents,
                "Filtered events should remain null when input data is null.");
    }
}