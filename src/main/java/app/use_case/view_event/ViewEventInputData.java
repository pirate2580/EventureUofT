package app.use_case.view_event;

/**
 * Input Data for the View Event use case.
 * This class encapsulates the data needed to view a specific event, such as the event's title.
 */
public class ViewEventInputData {

    private final String title;

    /**
     * Constructor for ViewEventInputData.
     * This constructor initializes the data required for viewing an event.
     * @param title The title of the event to be viewed.
     */
    public ViewEventInputData(String title) {
        this.title = title;
    }

    /**
     * Gets the title of the event.
     * @return The title of the event.
     */
    public String getTitle() {
        return title;
    }
}
