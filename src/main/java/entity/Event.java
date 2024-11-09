package entity;

import java.util.List;

abstract class Event {
    private String eventId;
    private String organizer;
    private String title;
    private String description;
    private String dateTime;
    private List<User> attendeesIdList;
    private int capacity;
    private List<String> tags;
    private int latitude;
    private int longitude;
}
