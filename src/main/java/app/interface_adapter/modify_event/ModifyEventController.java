package app.interface_adapter.modify_event;

import java.util.List;

import app.use_case.modify_event.ModifyEventInputBoundary;
import app.use_case.modify_event.ModifyEventInputData;

/**
 * Controller for the Modify Event Use Case.
 * This class handles user input for modifying events and delegates the execution
 * to the corresponding use case interactor. It creates a {@link ModifyEventInputData}
 * object to encapsulate the updated event details.
 */
public class ModifyEventController {

    private final ModifyEventInputBoundary userEventUseCaseInteractor;

    /**
     * Constructs a new {@link ModifyEventController}.
     * @param userEventUseCaseInteractor the interactor responsible for executing the modify event use case.
     *                                   Must not be {@code null}.
     */
    public ModifyEventController(ModifyEventInputBoundary userEventUseCaseInteractor) {
        this.userEventUseCaseInteractor = userEventUseCaseInteractor;
    }

    /**
     * Executes the Modify Event Use Case.
     * Updates an existing event with the provided details. This method modifies various attributes
     * of the event, such as its title, description, date and time, capacity, location, tags, and organizer.
     * Additionally, it allows for the deletion of the event if specified.
     * @param oldTitle            the original title of the event. Used to identify the event to be modified.
     * @param updatedTitle        the new title of the event.
     * @param updatedDescription  the updated description of the event.
     * @param updatedDateTime     the updated date and time of the event in ISO format.
     * @param updatedCapacity     the updated capacity of the event.
     * @param updatedLatitude     the updated latitude of the event location.
     * @param updatedLongitude    the updated longitude of the event location.
     * @param deleteEvent         a flag indicating whether the event should be deleted. If {@code true},
     *                             the event will be removed; otherwise, it will be updated.
     * @param updatedTags         a list of updated tags associated with the event.
     * @param eventId             the unique identifier of the event to be modified.
     * @param updatedOrganizer    the updated name of the event organizer.
     */
    public void execute(String oldTitle, String updatedTitle, String updatedDescription,
                        String updatedDateTime, int updatedCapacity, float updatedLatitude,
                        float updatedLongitude, boolean deleteEvent, List<String> updatedTags,
                        String eventId, String updatedOrganizer) {
        final ModifyEventInputData modifyEventInputData = new ModifyEventInputData(eventId, oldTitle, updatedTitle,
                updatedDescription, updatedDateTime, updatedCapacity, updatedLatitude, updatedLongitude, deleteEvent,
                updatedTags, updatedOrganizer);
        this.userEventUseCaseInteractor.execute(modifyEventInputData);
    }

    public void switchToHomeView() {this.userEventUseCaseInteractor.switchToHomeView();}
}
