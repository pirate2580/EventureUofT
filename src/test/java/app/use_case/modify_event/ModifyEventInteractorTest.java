package app.use_case.modify_event;

import java.util.List;
import java.util.Map;

import app.entity.Event.CommonEvent;
import app.entity.Event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ModifyEventInteractorTest {

    private ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject;
    private ModifyEventOutputBoundary modifyEventPresenter;
    private ModifyEventInteractor interactor;
    private Event savedEvent;
    private String deletedEventName;
    private ModifyEventOutputData outputData;
    private String failMessage;
    private boolean isSwitchToHomeViewCalled;

    @BeforeEach
    void setUp() {
        savedEvent = null;
        deletedEventName = null;
        outputData = null;
        failMessage = null;
        isSwitchToHomeViewCalled = false;

        modifyEventUserDataAccessObject = new ModifyEventUserDataAccessInterface() {
            private Event mockEvent = new CommonEvent(
                    "event123", "Original Organizer", "Old Event Title", "Original description",
                    "2024-10-10 10:00", 50, 43.0f, -79.0f, List.of("original_tag")
            );


            @Override
            public void saveEvent(Event event) {

            }

            @Override
            public void deleteEvent(String eventName) {
                deletedEventName = eventName;
            }

            @Override
            public Event getEventById(String eventId) {
                return null;
            }


            @Override
            public void modifyEvent(String eventName, Map<String, Object> updatedFields) {
                //System.out.println("Finished");
                if (mockEvent != null && mockEvent.getTitle().equals(eventName)) { // Check by event ID
                    if (updatedFields.containsKey("title")) {
                        mockEvent.setTitle((String) updatedFields.get("title"));
                    }
                    if (updatedFields.containsKey("description")) {
                        mockEvent.setDescription((String) updatedFields.get("description"));
                    }
                    if (updatedFields.containsKey("capacity")) {
                        mockEvent.setCapacity((Integer) updatedFields.get("capacity"));
                    }
                    if (updatedFields.containsKey("tags")) {
                        mockEvent.setTags((List<String>) updatedFields.get("tags"));
                    }
                    if (updatedFields.containsKey("latitude")) {
                        mockEvent.setLatitude((Float) updatedFields.get("latitude"));
                    }
                    if (updatedFields.containsKey("longitude")) {
                        mockEvent.setLongitude((Float) updatedFields.get("longitude"));
                    }
                    savedEvent = mockEvent;
                } else {
                    throw new IllegalArgumentException("Event not found with name: " + eventName);
                }
            }
        };

        modifyEventPresenter = new ModifyEventOutputBoundary() {
            @Override
            public void prepareSuccessView(ModifyEventOutputData outputData) {
                ModifyEventInteractorTest.this.outputData = outputData;
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


        interactor = new ModifyEventInteractor(modifyEventUserDataAccessObject, modifyEventPresenter);
    }

    @Test
    void testExecute_ModifyEvent_Success() {
        ModifyEventInputData inputData = new ModifyEventInputData(
                "event123",
                "Old Event Title",
                "Updated Event Title",
                "Updated description",
                "2024-10-10 10:00",
                100,
                45.0f,
                -80.0f,
                false,
                null,
                "UofTer"
        );

        interactor.execute(inputData);
        //System.out.println(savedEvent.getTitle());
        assertNotNull(savedEvent);
        assertEquals("event123", savedEvent.getEventId());
        assertEquals("Updated Event Title", savedEvent.getTitle());
        assertEquals("Updated description", savedEvent.getDescription());
        assertEquals("2024-10-10 10:00", savedEvent.getDateTime());
        assertEquals(100, savedEvent.getCapacity());
        assertEquals(45.0f, savedEvent.getLatitude());
        assertEquals(-80.0f, savedEvent.getLongitude());

        assertNull(deletedEventName, "No deletion should occur during update.");

        assertNotNull(outputData);
        assertEquals("event123", outputData.getEventId());
        assertEquals("Updated Event Title", outputData.getUpdatedTitle());
        assertEquals("Event updated successfully.", outputData.getMessage());

        assertNull(failMessage);
    }
    @Test
    void testExecute_SwitchToHomeView_Success() {
        interactor.switchToHomeView();
        assertTrue(isSwitchToHomeViewCalled);
    }
    @Test
    void testExecute_DeleteEvent_Success() {
        ModifyEventInputData inputData = new ModifyEventInputData(
                "event456",
                "Event To Delete",
                null,
                null,
                null,
                0,
                0.0f,
                0.0f,
                true,
                null,
                null
        );

        interactor.execute(inputData);

        assertNull(savedEvent);
        assertEquals("Event To Delete", deletedEventName);

        assertNotNull(outputData);
        assertEquals("Temp", outputData.getEventId());
        assertEquals("Event To Delete", outputData.getUpdatedTitle());
        assertEquals("Event deleted successfully.", outputData.getMessage());

        assertNull(failMessage);
    }
    @Test
    void testExecute_InvalidInputData() {
        ModifyEventInputData inputData = new ModifyEventInputData(
                "event789",
                null,
                null,
                null,
                null,
                0,
                0.0f,
                0.0f,
                false,
                null,
                null
        );

        interactor.execute(inputData);

        assertNull(savedEvent);
        assertNull(deletedEventName);
        assertNull(outputData);
        assertEquals("Event not found.", failMessage);
    }
    @Test
    void testExecute_EventNotFound() {
        ModifyEventInputData inputData = new ModifyEventInputData(
                "event789",
                null,
                null,
                null,
                null,
                0,
                0.0f,
                0.0f,
                false,
                null,
                null
        );

        interactor.execute(inputData);

        assertNull(savedEvent);
        assertNull(deletedEventName);
        assertNull(outputData);
        assertEquals("Event not found.", failMessage);
    }

    @Test
    void testExecute_DeleteEvent_Failure() {
        ModifyEventInputData inputData = new ModifyEventInputData(
                "event000",
                "Nonexistent Event",
                null,
                null,
                null,
                0,
                0.0f,
                0.0f,
                true,
                null,
                null
        );

        modifyEventUserDataAccessObject = new ModifyEventUserDataAccessInterface() {
            private Event mockEvent;


            @Override
            public void saveEvent(Event event) {

            }

            @Override
            public void deleteEvent(String eventName) {
                throw new RuntimeException("Database error during deletion");
            }

            @Override
            public Event getEventById(String eventId) {
                return null;
            }


            /**
             * Modifies an existing event in the Firestore database.
             *
             * @param eventName     The name of the event to modify.
             * @param updatedFields A map containing the fields to update and their new values.
             */
            @Override
            public void modifyEvent(String eventName, Map<String, Object> updatedFields) {

            }
        };

        interactor = new ModifyEventInteractor(modifyEventUserDataAccessObject, modifyEventPresenter);

        interactor.execute(inputData);

        assertNull(savedEvent);
        assertNull(deletedEventName);
        assertNull(outputData);
        assertEquals("Failed to delete event. Reason: Database error during deletion", failMessage);
    }


    @Test
    void testExecute_UpdateEvent_ExceptionDuringUpdate() {
        ModifyEventInputData inputData = new ModifyEventInputData(
                "event999",
                "Old Event Title",
                "Updated Event Title",
                "Updated description",
                "2024-10-10 10:00",
                100,
                43.6629f,
                -79.3957f,
                false,
                null,
                "UofTer"
        );

        modifyEventUserDataAccessObject = new ModifyEventUserDataAccessInterface() {

            @Override
            public void saveEvent(Event event) {

            }

            @Override
            public void deleteEvent(String eventName) {
            }

            @Override
            public Event getEventById(String eventId) {
                return null;
            }

            /**
             * Modifies an existing event in the Firestore database.
             *
             * @param eventName     The name of the event to modify.
             * @param updatedFields A map containing the fields to update and their new values.
             */
            @Override
            public void modifyEvent(String eventName, Map<String, Object> updatedFields) {
                throw new RuntimeException("Database error during saving");
            }
        };

        interactor = new ModifyEventInteractor(modifyEventUserDataAccessObject, modifyEventPresenter);

        interactor.execute(inputData);

        assertNull(savedEvent);
        assertNull(deletedEventName);
        assertNull(outputData);
        assertEquals("Failed to save updated event. Reason: Database error during saving", failMessage);
    }
}