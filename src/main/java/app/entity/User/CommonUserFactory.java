package app.entity.User;

import org.springframework.stereotype.Component;
/**
 * Factory for creating CommonUser objects
 *
 * NOTE: why do we want this:
 * By using a factory (CommonUserFactory) to create instances of User,
 * you decouple the creation logic from the main business logic. Your main code only needs to know
 * about the User interface and UserFactory, not the specific implementation (CommonUser).
 */
@Component
public class CommonUserFactory implements UserFactory{

    @Override
    public User create(String username, String email, String password){ return new CommonUser(username, email, password);}
}
