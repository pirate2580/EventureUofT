package app.use_case.Register;

import app.entity.User.User;
import app.entity.User.CommonUserFactory;
import app.use_case.register.RegisterInputData;
import app.use_case.register.RegisterInteractor;
import app.use_case.register.RegisterUserDataAccessInterface;
import app.use_case.register.RegisterOutputData;
import app.use_case.register.RegisterInputBoundary;
import app.use_case.register.RegisterOutputBoundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterInteractorTest {


    private RegisterUserDataAccessInterface userDataAccessObject;
    private RegisterOutputBoundary userPresenter;
    private CommonUserFactory userFactory;
    private RegisterInteractor interactor;
    private User savedUser;
    private String failMessage;
    private String successMessage;

    @BeforeEach
    void setUp() {
        savedUser = null;
        failMessage = null;
        successMessage = null;

        userDataAccessObject = new RegisterUserDataAccessInterface() {
            private final String existingUsername = "existingUser";

            @Override
            public boolean existsByUsername(String username) {
                return existingUsername.equals(username);
            }

            @Override
            public void save(User user) {
                savedUser = user;
            }
        };

        userPresenter = new RegisterOutputBoundary() {
            @Override
            public void prepareSuccessView(RegisterOutputData outputData, String message) {
                successMessage = message;
            }

            @Override
            public void prepareFailView(String errorMessage) {
                failMessage = errorMessage;
            }

            @Override
            public void switchToLoginView() {
                successMessage = "Switched to Login View";
            }
        };

        userFactory = new CommonUserFactory();

        interactor = new RegisterInteractor(userDataAccessObject, userPresenter, userFactory);
    }

    @Test
    void testExecute_SuccessfulRegistration() {
        RegisterInputData inputData = new RegisterInputData("newUser", "newUser@example.com", "password123");

        interactor.execute(inputData);

        assertNotNull(savedUser, "User should be saved.");
        assertEquals("newUser", savedUser.getUsername(), "Username should match input.");
        assertEquals("newUser@example.com", savedUser.getEmail(), "Email should match input.");
        assertEquals("password123", savedUser.getPassword(), "Password should match input.");
        assertEquals("User created successfully! Please log in.", successMessage, "Success message should match.");
        assertNull(failMessage, "Fail message should be null for successful registration.");
    }

    @Test
    void testExecute_UsernameAlreadyExists() {
        RegisterInputData inputData = new RegisterInputData("existingUser", "user@example.com", "password123");

        interactor.execute(inputData);

        assertNull(savedUser, "No user should be saved if username already exists.");
        assertEquals("Username already exists.", failMessage, "Failure message should match.");
        assertNull(successMessage, "Success message should be null for failed registration.");
    }

    @Test
    void testSwitchToLoginView() {
        interactor.switchToLoginView();

        assertEquals("Switched to Login View", successMessage, "Switch to login view message should match.");
    }

    @Test
    void testExecute_NullInputData() {
        interactor.execute(null);

        assertNull(savedUser, "No user should be saved for null input data.");
        assertEquals("Register input data cannot be null.", failMessage, "Failure message should match expected for null input.");
        assertNull(successMessage, "Success message should be null when input data is null.");
    }

    @Test
    void testExecute_ValidInputData() {
        RegisterInputData validInputData = new RegisterInputData("validUser", "valid@example.com", "securePassword");

        interactor.execute(validInputData);

        assertNotNull(savedUser, "User should be saved for valid input.");
        assertEquals("validUser", savedUser.getUsername(), "Username should match the input data.");
        assertEquals("valid@example.com", savedUser.getEmail(), "Email should match the input data.");
        assertEquals("securePassword", savedUser.getPassword(), "Password should match the input data.");
        assertEquals("User created successfully! Please log in.", successMessage, "Success message should match expected for valid input.");
        assertNull(failMessage, "Fail message should be null for successful registration.");
    }
}