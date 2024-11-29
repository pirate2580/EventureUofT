package use_case;

import app.entity.User.User;
import app.entity.User.UserFactory;
import app.entity.User.CommonUserFactory;
import app.use_case.login.LoginInputData;
import app.use_case.login.LoginInteractor;
import app.use_case.login.LoginOutputBoundary;
import app.use_case.login.LoginOutputData;
import app.use_case.login.LoginUserDataAccessInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    private LoginUserDataAccessInterface userDataAccessObject;
    private LoginOutputBoundary loginPresenter;
    private LoginInteractor interactor;
    private User foundUser;
    private LoginOutputData outputData;
    private String failMessage;
    private boolean switchToRegisterViewCalled;
    private UserFactory userFactory;

    @BeforeEach
    void setUp() {
        foundUser = null;
        outputData = null;
        failMessage = null;
        switchToRegisterViewCalled = false;

        userFactory = new CommonUserFactory();

        userDataAccessObject = new LoginUserDataAccessInterface() {
            @Override
            public User findUserByUsername(String username) {
                return foundUser != null && foundUser.getUsername().equals(username) ? foundUser : null;
            }
        };

        loginPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData outputData, String s) {
                LoginInteractorTest.this.outputData = outputData;
            }

            @Override
            public void prepareFailView(String errorMessage) {
                failMessage = errorMessage;
            }

            @Override
            public void switchToRegisterView() {
                switchToRegisterViewCalled = true;
            }
        };

        interactor = new LoginInteractor(userDataAccessObject, loginPresenter);
    }

    @Test
    void testExecute_SuccessfulLogin() {
        foundUser = userFactory.create("testuser", "testuser@example.com", "password123");

        LoginInputData inputData = new LoginInputData("testuser", "password123");

        interactor.execute(inputData);

        assertNotNull(outputData);
        assertEquals("testuser", outputData.getUsername());
        assertTrue(outputData.isSuccess());

        assertNull(failMessage);
    }

    @Test
    void testExecute_UsernameDoesNotExist() {
        foundUser = null;

        LoginInputData inputData = new LoginInputData("nonexistentuser", "password123");

        interactor.execute(inputData);

        assertNull(outputData);
        assertEquals("Username does not exist.", failMessage);
    }

    @Test
    void testExecute_IncorrectPassword() {
        foundUser = userFactory.create("testuser", "testuser@example.com", "password123");

        LoginInputData inputData = new LoginInputData("testuser", "wrongpassword");


        interactor.execute(inputData);

        assertNull(outputData);
        assertEquals("Incorrect password.", failMessage);
    }

    @Test
    void testSwitchToRegisterView() {
        interactor.switchToRegisterView();

        assertTrue(switchToRegisterViewCalled, "switchToRegisterView should have been called");
    }

    @Test
    void testExecute_NullInputData() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> interactor.execute(null));
        assertEquals("LoginInputData cannot be null", exception.getMessage());
    }

    @Test
    void testExecute_NullUsername() {
        LoginInputData inputData = new LoginInputData(null, "password123");

        interactor.execute(inputData);

        assertNull(outputData);
        assertEquals("Username or password cannot be empty.", failMessage);
    }

    @Test
    void testExecute_NullPassword() {
        LoginInputData inputData = new LoginInputData("testuser", null);

        interactor.execute(inputData);

        assertNull(outputData);
        assertEquals("Username or password cannot be empty.", failMessage);
    }

    @Test
    void testExecute_UserPasswordIsNull() {
        // User with null password
        foundUser = userFactory.create("testuser", "testuser@example.com", null);

        LoginInputData inputData = new LoginInputData("testuser", "password123");

        interactor.execute(inputData);

        assertNull(outputData);
        assertEquals("Incorrect password.", failMessage);
    }
}

