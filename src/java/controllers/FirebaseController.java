package java.controllers;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class FirebaseController {
    @Autowired
    FirebaseService firebaseService;

    @PostMapping("/createUser")
    public User postUser(@RequestBody User user){
        return firebaseService.saveUserDetails(user);
    }
}
