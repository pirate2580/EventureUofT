package java;

import java.util.List;

abstract class Event {
    private String eventId;
    private List<User> organizers;
    private List<User> contact_information;
    private String title;
    private String signUp;
    private String description;
    private String dateTime;
    private List<User> attendeesIdList;
    private int price;
    private int capacity;
    private List<String> tags;

}
