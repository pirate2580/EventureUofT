//package app.use_case.modify_event;
//
//import app.entity.Event.Event;
//import app.entity.Event.EventFactory;
//
//import app.use_case.modify_event.ModifyEventUserDataAccessInterface;
//
///**
// * The ModifyEventInteractor handles the logic of modifying events (edit or delete).
// * Uses the Event Data Access Interface to update the event details.
// */
//public class ModifyEventInteractor implements ModifyEventInputBoundary {
//    private final ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject;
//    private final ModifyEventOutputBoundary modifyEventPresenter;
//    EventFactory eventFactory;
//
//    public ModifyEventInteractor(ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject,
//                                 ModifyEventOutputBoundary modifyEventPresenter,
//                                 EventFactory eventFactory   ) {
//        this.modifyEventUserDataAccessObject = modifyEventUserDataAccessObject;
//        this.modifyEventPresenter = modifyEventPresenter;
//        this.eventFactory = eventFactory;
//    }
//
//    @Override
//    public void execute(ModifyEventInputData modifyEventInputData) {
//        String eventName = modifyEventInputData.getOldTitle();
//        System.out.println("Rizzler was here");
//        System.out.println(modifyEventInputData.getDeleteEvent());
//        if (eventName == null) {
//            modifyEventPresenter.prepareFailView("Event not found.");
//        } else if (modifyEventInputData.getDeleteEvent()) {
//            try {
//                System.out.println("Delete event");
//                modifyEventUserDataAccessObject.deleteEvent(eventName);
//                ModifyEventOutputData outputData = new ModifyEventOutputData("Temp", eventName, "Event deleted successfully.");
//                modifyEventPresenter.prepareSuccessView(outputData);
//            } catch (Exception e) {
//                modifyEventPresenter.prepareFailView("Failed to delete event. Reason: " + e.getMessage());
//            }
//        } else {
//            try {
//                Event event = eventFactory.create(
//                        modifyEventInputData.getEventId(),
//                        modifyEventInputData.getUpdatedOrganizer(),
//                        modifyEventInputData.getUpdatedTitle(),
//                        modifyEventInputData.getUpdatedDescription(),
//                        modifyEventInputData.getUpdatedDateTime(),
//                        modifyEventInputData.getUpdatedCapacity(),
//                        modifyEventInputData.getUpdatedLatitude(),
//                        modifyEventInputData.getUpdatedLongitude(),
//                        modifyEventInputData.getUpdatedTags()
//                );
//                System.out.println("Big Justicesd    was here");
//                modifyEventUserDataAccessObject.deleteEvent(eventName);
//                modifyEventUserDataAccessObject.saveEvent(event);
//                ModifyEventOutputData outputData = new ModifyEventOutputData("Temp", eventName, "Event deleted successfully.");
//                modifyEventPresenter.prepareSuccessView(outputData);
//            } catch (Exception e) {
//                    modifyEventPresenter.prepareFailView("Failed to delete event. Reason: " + e.getMessage());
//            }
//        }
//    }
//}


package app.use_case.modify_event;

import app.entity.Event.Event;
import app.entity.Event.EventFactory;

public class ModifyEventInteractor implements ModifyEventInputBoundary {
    private final ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject;
    private final ModifyEventOutputBoundary modifyEventPresenter;
    private final EventFactory eventFactory;

    public ModifyEventInteractor(ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject,
                                 ModifyEventOutputBoundary modifyEventPresenter,
                                 EventFactory eventFactory) {
        this.modifyEventUserDataAccessObject = modifyEventUserDataAccessObject;
        this.modifyEventPresenter = modifyEventPresenter;
        this.eventFactory = eventFactory;
    }

    @Override
    public void execute(ModifyEventInputData modifyEventInputData) {
        String eventName = modifyEventInputData.getOldTitle();

        if (eventName == null) {
            modifyEventPresenter.prepareFailView("Event not found.");
            return;
        }

        if (modifyEventInputData.getDeleteEvent()) {
            handleDeleteEvent(eventName);
        } else {
            handleUpdateEvent(modifyEventInputData);
        }
    }

    private void handleDeleteEvent(String eventName) {
        try {
            modifyEventUserDataAccessObject.deleteEvent(eventName);
            ModifyEventOutputData outputData = new ModifyEventOutputData("Temp", eventName, "Event deleted successfully.");
            modifyEventPresenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            modifyEventPresenter.prepareFailView("Failed to delete event. Reason: " + e.getMessage());
        }
    }

    private void handleUpdateEvent(ModifyEventInputData modifyEventInputData) {
        try {
            Event existingEvent = modifyEventUserDataAccessObject.getEventById(modifyEventInputData.getEventId());

            if (existingEvent == null) {
                modifyEventPresenter.prepareFailView("Event not found for updating.");
                return;
            }

            // Update the event's fields with the new values
            existingEvent.setTitle(modifyEventInputData.getUpdatedTitle());
            existingEvent.setDescription(modifyEventInputData.getUpdatedDescription());
            existingEvent.setDateTime(modifyEventInputData.getUpdatedDateTime());
            existingEvent.setCapacity(modifyEventInputData.getUpdatedCapacity());
            existingEvent.setLatitude(modifyEventInputData.getUpdatedLatitude());
            existingEvent.setLongitude(modifyEventInputData.getUpdatedLongitude());
            existingEvent.setTags(modifyEventInputData.getUpdatedTags());
            existingEvent.setOrganizer(modifyEventInputData.getUpdatedOrganizer());

            try {
                modifyEventUserDataAccessObject.saveEvent(existingEvent);

                ModifyEventOutputData outputData = new ModifyEventOutputData(
                        existingEvent.getEventId(),
                        existingEvent.getTitle(),
                        "Event updated successfully."
                );
                modifyEventPresenter.prepareSuccessView(outputData);
            } catch (Exception saveException) {
                modifyEventPresenter.prepareFailView("Failed to save updated event. Reason: " + saveException.getMessage());
            }

        } catch (Exception e) {
            modifyEventPresenter.prepareFailView("Failed to update event. Reason: " + e.getMessage());
        }
    }
}