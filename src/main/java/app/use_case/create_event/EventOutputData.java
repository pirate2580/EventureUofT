package app.use_case.create_event;


/**
 * Output data for the event user use case
 */
public class EventOutputData {

    private String title;

    private final boolean useCaseFailed;

    public EventOutputData(String title, boolean useCaseFailed) {
        this.title = title;
        this.useCaseFailed = useCaseFailed;
    }

    public String getTitle() {
        return title;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}