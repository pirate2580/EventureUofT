package controllers;

import entity.User.CommonUser;
import entity.User.User;
import entity.User.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import services.FirebaseService;

@RestController
public class FirebaseController {

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private UserFactory userFactory; // inject the factory

    @PostMapping("/createUser")
    public String postUser(@RequestBody CommonUser user) throws Exception {
        CommonUser createdUser = (CommonUser) userFactory.create(user.getUsername(), user.getPassword());
        return firebaseService.saveUserDetails(createdUser);
    }
}
