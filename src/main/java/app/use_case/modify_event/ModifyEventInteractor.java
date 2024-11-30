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