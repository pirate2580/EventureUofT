package app.use_case.filter_event;
import java.util.List;
import java.util.ArrayList;

public class FilterEventInputData {
    private final List<String> tags;
//    private final String location;

    public FilterEventInputData(List<String> category) {
        this.tags = category;   //The category of the event, e.g. "clubs", "parties", "sports"...etc.
//        this.location = location;   //The location where the event is taking place
    }

    public List<String> getTags() {
        return tags;
    }

//    public String getLocation() {
//        return location;
//    }
}