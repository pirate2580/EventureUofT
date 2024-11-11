package app.data_access;

import java.util.HashMap;
import java.util.Map;

import app.entity.User.User;
import app.use_case.create_event.RegisterUserDataAccessInterface;

//import use_case.change_password.ChangePasswordUserDataAccessInterface;
//import use_case.login.LoginUserDataAccessInterface;
//import use_case.logout.LogoutUserDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 * TODO: THIS IS JUST ESSENTIALLY SAVING USERS IN A HASH MAP
 */
public class InMemoryUserDataAccessObject implements RegisterUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();

    private String currentUsername;

    @Override
    public boolean existsByUsername(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
        this.printUsers();
    }
    public void printUsers() {
        System.out.println("THIS IS FROM INMEMORY DAO");
        for (Map.Entry<String, User> entry : users.entrySet()) {
            String username = entry.getKey();
            User user = entry.getValue();
            System.out.println("Username: " + username + ", User: " + user);
        }
    }

    //    @Override
    //    public User get(String username) {
    //        return users.get(username);
    //    }
    //
    //    @Override
    //    public void changePassword(User user) {
    //        // Replace the old entry with the new password
    //        users.put(user.getName(), user);
    //    }

    //    @Override
    //    public void setCurrentUsername(String name) {
    //        this.currentUsername = name;
    //    }
    //
    //    @Override
    //    public String getCurrentUsername() {
    //        return this.currentUsername;
    //    }
}
