package app.use_case.rsvp_event;

/**
 * Input Boundary for the RSVP Event Use Case.
 * This interface defines the contract for handling the RSVP event functionality.
 * The implementing class will execute the RSVP operation when provided with the necessary input data.
 */
public interface RSVPEventInputBoundary {
    /**
     * Executes the RSVP event use case.
     * This method takes the input data containing the event details and the user's RSVP information,
     * and processes the RSVP for the specified event.
     * @param rsvpEventInputData The input data containing the event and user details required for the RSVP.
     *                           It includes event information and the user who is RSVPing.
     */
    void execute(RSVPEventInputData rsvpEventInputData);
}
