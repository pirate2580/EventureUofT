package app.use_case.modify_event;

/**
 * Output Data for the Modify Event Use Case.
 * This class encapsulates the result of a modification to an event, including
 * the event ID, updated title, and a success or failure message.
 */
public class ModifyEventOutputData {
    private final String eventId;
    private final String updatedTitle;
    private final String message;

    /**
     * Contains details for a modification.
     * @param eventId      The ID of the event modified.
     * @param updatedTitle The updated title of the event.
     * @param message      The success message.
     */
    public ModifyEventOutputData(String eventId, String updatedTitle, String message) {
        this.eventId = eventId;
        this.updatedTitle = updatedTitle;
        this.message = message;
    }

    /**
     * Retrieves the unique ID of the modified event.
     * @return The event ID as a {@link String}.
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Retrieves the updated title of the modified event.
     * @return The updated title as a {@link String}, or {@code null} if the title was not modified.
     */
    public String getUpdatedTitle() {
        return updatedTitle;
    }

    /**
     * Retrieves the message describing the result of the modification.
     * @return The message as a {@link String}.
     */
    public String getMessage() {
        return message;
    }
}
