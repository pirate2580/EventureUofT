package app.interface_adapter.create_event;

import java.util.List;

import app.use_case.create_event.EventInputBoundary;
import app.use_case.create_event.EventInputData;

/**
 * Controller for the Create Event Use Case.
 * This controller handles the input from the user to create a new event and delegates the
 * execution to the EventInputBoundary interactor. It is responsible for collecting and packaging
 * all required event data and switching views when necessary.
 */
public class CreateEventController {

    private final EventInputBoundary userEventUseCaseInteractor;

    /**
     * Constructs a new CreateEventController.
     *
     * @param userEventUseCaseInteractor the interactor responsible for executing the create event use case.
     */
    public CreateEventController(EventInputBoundary userEventUseCaseInteractor) {
        this.userEventUseCaseInteractor = userEventUseCaseInteractor;
    }

    /**
     * Executes the create event use case by providing all required event details.
     *
     * @param username   the username of the event organizer.
     * @param title      the title of the event.
     * @param description a detailed description of the event.
     * @param dateTime   the date and time of the event in ISO format.
     * @param capacity   the maximum number of participants allowed.
     * @param latitude   the latitude of the event location.
     * @param longitude  the longitude of the event location.
     * @param tags       a list of tags associated with the event.
     * @param eventId    the unique identifier for the event.
     * @param organizer  the name of the event organizer.
     */
    public void execute(String username, String title, String description, String dateTime,
                        int capacity, float latitude, float longitude, List<String> tags,
                        String eventId, String organizer) {
        final EventInputData eventInputData = new EventInputData(username, eventId, organizer,
                title, description, dateTime, capacity, latitude, longitude, tags);

        this.userEventUseCaseInteractor.execute(eventInputData);
    }

    /**
     * Switches the view to the home screen.
     */
    public void switchToHomeView() {
        this.userEventUseCaseInteractor.switchToHomeView();
    }
}
