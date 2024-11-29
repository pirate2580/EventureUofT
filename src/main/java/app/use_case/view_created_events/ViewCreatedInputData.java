package app.use_case.view_created_events;

public class ViewCreatedInputData {
    private final String username;

    public ViewCreatedInputData (String username) {
        this.username = username;
    }

    public String getUsername() {return username;}
}
