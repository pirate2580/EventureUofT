package app.use_case.view_created_events;
import java.util.List;

public class ViewCreatedOutputData {
    private final List<String> createdEvents;

    public ViewCreatedOutputData(List<String> createdEvents) {this.createdEvents = createdEvents;}

    public List<String> getCreatedEvents() {
        return createdEvents;
    }
}
