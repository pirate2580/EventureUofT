package java;

import java.util.List;

abstract class User {
    private String userID;
    private String displayName;
    private String contactInfo;
    private String bio;
    private boolean isVerified;
    private List<Event> rsvpEvents;
    private List<Event> pastEvents;
}
