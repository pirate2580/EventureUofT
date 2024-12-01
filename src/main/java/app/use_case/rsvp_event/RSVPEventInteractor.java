package app.use_case.rsvp_event;

/**
 * The Interactor for the RSVP Event Use Case.
 * This class is responsible for handling the RSVP functionality. It processes the input data,
 * validates the provided information (username and event ID), and then interacts with the
 * data access object to add the user to the event's RSVP list.
 */
public class RSVPEventInteractor implements RSVPEventInputBoundary {
    private final RSVPEventUserDataAccessInterface rsvpEventDataAccessObject;
    private final RSVPEventOutputBoundary rsvpEventPresenter;

    /**
     * Constructs the RSVPEventInteractor with the necessary data access and presenter dependencies.
     * @param rsvpEventDataAccessObject The data access interface responsible for interacting with the data store.
     * @param rsvpEventPresenter        The output boundary responsible for presenting the results of the RSVP operation
     */
    public RSVPEventInteractor(RSVPEventUserDataAccessInterface rsvpEventDataAccessObject,
                               RSVPEventOutputBoundary rsvpEventPresenter) {
        this.rsvpEventDataAccessObject = rsvpEventDataAccessObject;
        this.rsvpEventPresenter = rsvpEventPresenter;
    }

    /**
     * Executes the RSVP event use case.
     * This method validates the input data and calls the appropriate methods to add the user to the RSVP list
     * for the specified event. If there are any validation issues, it prepares a failure view with an error message.
     * @param rsvpEventInputData The input data containing the username and event ID to process the RSVP.
     */
    @Override
    public void execute(RSVPEventInputData rsvpEventInputData) {
        if (rsvpEventInputData.getUsername() == null) {
            rsvpEventPresenter.prepareFailView("Username cannot be null.");
            return;
        }

        if (rsvpEventInputData.getEventId() == null) {
            rsvpEventPresenter.prepareFailView("Event ID cannot be null.");
            return;
        }

        rsvpEventDataAccessObject.addUserToRSVPList(rsvpEventInputData.getUsername(), rsvpEventInputData.getEventId());
        rsvpEventPresenter.prepareSuccessView(new RSVPEventOutputData(rsvpEventInputData.getUsername(),
                rsvpEventInputData.getEventId()));
    }
}
