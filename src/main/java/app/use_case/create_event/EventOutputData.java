package app.use_case.create_event;

/**
 * Output data for the event user use case.
 */
public class EventOutputData {

    private String title;
    private final boolean useCaseFailed;

    /**
     * Constructs a new {@link EventOutputData}.
     * @param title         The title of the event. Can be {@code null} if the use case failed.
     * @param useCaseFailed A boolean indicating whether the use case execution failed.
     */
    public EventOutputData(String title, boolean useCaseFailed) {
        this.title = title;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Retrieves the title of the event.
     * @return the event title, or {@code null} if the use case failed.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Checks whether the use case execution failed.
     * @return {@code true} if the use case execution failed, otherwise {@code false}.
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
