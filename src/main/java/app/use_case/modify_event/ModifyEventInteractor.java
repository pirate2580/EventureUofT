package app.use_case.modify_event;

import app.entity.Event.Event;
import app.entity.Event.EventFactory;

/**
 * Interactor for the Modify Event Use Case.
 * This class implements the business logic for modifying events, including
 * updating event details or deleting an event. It interacts with the data access
 * object (DAO) to persist changes and communicates the results to the presenter.
 */
public class ModifyEventInteractor implements ModifyEventInputBoundary {

    private final ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject;
    private final ModifyEventOutputBoundary modifyEventPresenter;
    private final EventFactory eventFactory;

    /**
     * Constructs a new {@link ModifyEventInteractor}.
     * @param modifyEventUserDataAccessObject The DAO for accessing and modifying event data. Must not be {@code null}.
     * @param modifyEventPresenter            The output boundary for preparing views. Must not be {@code null}.
     * @param eventFactory                    The factory for creating event objects. Must not be {@code null}.
     */
    public ModifyEventInteractor(ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject,
                                 ModifyEventOutputBoundary modifyEventPresenter,
                                 EventFactory eventFactory) {
        this.modifyEventUserDataAccessObject = modifyEventUserDataAccessObject;
        this.modifyEventPresenter = modifyEventPresenter;
        this.eventFactory = eventFactory;
    }

    /**
     * Executes the event modification use case.
     * Depending on the input data, this method handles either updating event details
     * or deleting an event. It validates input and communicates success or failure
     * to the presenter.
     * @param modifyEventInputData The input data containing details for modifying the event. Must not be {@code null}.
     */
    @Override
    public void execute(ModifyEventInputData modifyEventInputData) {
        String eventName = modifyEventInputData.getOldTitle();

        if (eventName == null) {
            modifyEventPresenter.prepareFailView("Event not found.");
            return;
        }

        if (modifyEventInputData.getDeleteEvent()) {
            handleDeleteEvent(eventName);
        }
        else {
            handleUpdateEvent(modifyEventInputData);
        }
    }

    /**
     * Handles the deletion of an event.
     * @param eventName The name of the event to delete. Must not be {@code null}.
     */
    private void handleDeleteEvent(String eventName) {
        try {
            modifyEventUserDataAccessObject.deleteEvent(eventName);
            ModifyEventOutputData outputData =
                    new ModifyEventOutputData("Temp", eventName, "Event deleted successfully.");
            modifyEventPresenter.prepareSuccessView(outputData);
        }
        catch (Exception e) {
            modifyEventPresenter.prepareFailView("Failed to delete event. Reason: " + e.getMessage());
        }
    }

    /**
     * Handles the updating of event details.
     * @param modifyEventInputData The input data containing updated event details. Must not be {@code null}.
     */
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
            }
            catch (Exception saveException) {
                modifyEventPresenter.prepareFailView("Failed to save updated event. "
                        + "Reason: " + saveException.getMessage());
            }
        }
        catch (Exception e) {
            modifyEventPresenter.prepareFailView("Failed to update event. Reason: " + e.getMessage());
        }
    }
}
