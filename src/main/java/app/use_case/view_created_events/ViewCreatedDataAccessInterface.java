package app.use_case.view_created_events;

import java.util.List;


public interface ViewCreatedDataAccessInterface {
    List<String> getCreatedEvents(String username);
}
