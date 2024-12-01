package app.interface_adapter.modify_event;

import java.util.List;

import app.use_case.modify_event.ModifyEventInputBoundary;
import app.use_case.modify_event.ModifyEventInputData;

/**
 * Controller for the Modify Event Use Case.
 * This class handles the input from the user interface and converts it into
 * data that can be processed by the use case interactor. It facilitates
 * operations such as updating or deleting events.
 */
public class ModifyEventController {
    private final ModifyEventInputBoundary userEventUseCaseInteractor;

    /**
     * Constructs a new {@link ModifyEventController}.
     * @param userEventUseCaseInteractor The interactor that handles the business logic
     *                                   for modifying events. Must not be {@code null}.
     */
    public ModifyEventController(ModifyEventInputBoundary userEventUseCaseInteractor) {
        this.userEventUseCaseInteractor = userEventUseCaseInteractor;
    }

    /**
     * Executes the modify event use case.
     * This method takes the event details provided by the user and sends them
     * to the use case interactor for processing.
     * @param oldTitle           The original title of the event.
     * @param updatedTitle       The updated title of the event.
     * @param updatedDescription The updated description of the event.
     * @param updatedDateTime    The updated date and time of the event.
     * @param updatedCapacity    The updated capacity of the event.
     * @param updatedLatitude    The updated latitude of the event.
     * @param updatedLongitude   The updated longitude of the event.
     * @param deleteEvent        {@code true} if the event is to be deleted; {@code false} otherwise.
     * @param updatedTags        A list of updated tags for the event.
     * @param eventId            The unique ID of the event.
     * @param updatedOrganizer   The updated organizer of the event.
     */
    public void execute(String oldTitle,
                        String updatedTitle,
                        String updatedDescription,
                        String updatedDateTime,
                        int updatedCapacity,
                        float updatedLatitude,
                        float updatedLongitude,
                        boolean deleteEvent,
                        List<String> updatedTags,
                        String eventId,
                        String updatedOrganizer) {
        final ModifyEventInputData modifyEventInputData = new ModifyEventInputData(eventId,
                oldTitle,
                updatedTitle,
                updatedDescription,
                updatedDateTime,
                updatedCapacity,
                updatedLatitude,
                updatedLongitude,
                deleteEvent,
                updatedTags,
                updatedOrganizer);
        this.userEventUseCaseInteractor.execute(modifyEventInputData);
    }

    /**
     * Switches to the home view.
     * This method communicates with the use case interactor to navigate
     * back to the home view.
     */
    public void switchToHomeView() {
        this.userEventUseCaseInteractor.switchToHomeView();
    }
}
