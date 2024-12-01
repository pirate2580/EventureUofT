package app.use_case.modify_event;

import java.util.HashMap;
import java.util.Map;

public class ModifyEventInteractor implements ModifyEventInputBoundary {
    private final ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject;
    private final ModifyEventOutputBoundary modifyEventPresenter;

    public ModifyEventInteractor(ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject,
                                 ModifyEventOutputBoundary modifyEventPresenter) {
        this.modifyEventUserDataAccessObject = modifyEventUserDataAccessObject;
        this.modifyEventPresenter = modifyEventPresenter;
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

    @Override
    public void switchToHomeView() {
        modifyEventPresenter.switchToHomeView();
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

        Map<String, Object> updatedFields = new HashMap<>();
        // Update the event's fields with the new values
        updatedFields.put("title", modifyEventInputData.getUpdatedTitle());
        updatedFields.put("description", modifyEventInputData.getUpdatedDescription());
        updatedFields.put("time", modifyEventInputData.getUpdatedDateTime());
        updatedFields.put("capacity", modifyEventInputData.getUpdatedCapacity());
        updatedFields.put("latitude", modifyEventInputData.getUpdatedLatitude());
        updatedFields.put("longitude", modifyEventInputData.getUpdatedLongitude());
        updatedFields.put("tags", modifyEventInputData.getUpdatedTags());
        updatedFields.put("organizer", modifyEventInputData.getUpdatedOrganizer());

        try {
            modifyEventUserDataAccessObject.modifyEvent(modifyEventInputData.getOldTitle(), updatedFields);

            ModifyEventOutputData outputData = new ModifyEventOutputData(
                    modifyEventInputData.getEventId(),
                    modifyEventInputData.getUpdatedTitle(),
                    "Event updated successfully."
            );
            modifyEventPresenter.prepareSuccessView(outputData);
        } catch (Exception saveException) {
            modifyEventPresenter.prepareFailView("Failed to save updated event. Reason: " + saveException.getMessage());
        }

    }
}