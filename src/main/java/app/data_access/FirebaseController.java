package app.data_access;

import app.entity.User.CommonUser;
import app.entity.User.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirebaseController {

    private final UserFactory userFactory;

    @Autowired
    public FirebaseController(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Autowired
    private FirebaseService firebaseService;


    @PostMapping("/createUser")
    public String postUser(@RequestBody CommonUser user) throws Exception {
        CommonUser createdUser = (CommonUser) userFactory.create(user.getUsername(), user.getPassword());
        return firebaseService.saveUserDetails(createdUser);
    }
}
