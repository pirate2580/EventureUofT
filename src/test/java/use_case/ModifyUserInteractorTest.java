package use_case;

import app.entity.User.User;
import app.entity.User.UserFactory;
import app.entity.User.CommonUserFactory;
import app.use_case.modify_user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class ModifyUserInteractorTest {

    private ModifyUserDataAccessInterface userDataAccess;
    private ModifyUserOutputBoundary outputBoundary;
    private ModifyUserInteractor interactor;
    private User existingUser;
    private String failMessage;
    private ModifyUserOutputData outputData;
    private UserFactory userFactory;

    @BeforeEach
    void setUp() {
        existingUser = null;
        failMessage = null;
        outputData = null;

        userFactory = new CommonUserFactory();

        userDataAccess = new ModifyUserDataAccessInterface() {
            @Override
            public User getUserByUsername(String username) {
                if (existingUser != null && existingUser.getUsername().equals(username)) {
                    return existingUser;
                }
                return null;
            }

            @Override
            public boolean doesUsernameExist(String username) {
                return existingUser != null && existingUser.getUsername().equals(username);
            }

            @Override
            public boolean doesEmailExist(String email) {
                return existingUser != null && existingUser.getEmail().equals(email);
            }

            @Override
            public boolean updateUser(User user) {
                existingUser = user;
                return true;
            }
        };

        outputBoundary = new ModifyUserOutputBoundary() {
            @Override
            public void prepareSuccessView(ModifyUserOutputData outputData) {
                ModifyUserInteractorTest.this.outputData = outputData;
            }

            @Override
            public void prepareFailView(String errorMessage) {
                failMessage = errorMessage;
            }
        };

        interactor = new ModifyUserInteractor(userDataAccess, outputBoundary);
    }

    @Test
    void testExecute_SuccessfulModification() {
        existingUser = userFactory.create("currentUser", "current@example.com", "currentPass");

        ModifyUserInputData inputData = new ModifyUserInputData(
                "currentUser",
                "newUser",
                "new@example.com",
                "currentPass",
                "newPass"
        );

        interactor.execute(inputData);

        assertNull(failMessage);
        assertNotNull(outputData);
        assertEquals("newUser", outputData.getUsername());
        assertEquals("User account successfully updated.", outputData.getMessage());

        assertEquals("newUser", existingUser.getUsername());
        assertEquals("new@example.com", existingUser.getEmail());
        assertTrue(existingUser.verifyPassword("newPass"));
    }

    @Test
    void testExecute_UserNotFound() {
        existingUser = null;

        ModifyUserInputData inputData = new ModifyUserInputData(
                "nonExistentUser",
                "newUser",
                "new@example.com",
                "anyPass",
                "newPass"
        );

        interactor.execute(inputData);

        assertNull(outputData);
        assertEquals("User not found.", failMessage);
    }

    @Test
    void testExecute_InvalidPassword() {
        existingUser = userFactory.create("currentUser", "current@example.com", "currentPass");

        // Incorrect old password
        ModifyUserInputData inputData = new ModifyUserInputData(
                "currentUser",
                "newUser",
                "new@example.com",
                "wrongPass",
                "newPass"
        );

        interactor.execute(inputData);

        assertNull(outputData);
        assertEquals("Invalid password provided.", failMessage);
    }

    @Test
    void testExecute_UsernameAlreadyTaken() {
        existingUser = userFactory.create("currentUser", "current@example.com", "currentPass");

        User anotherUser = userFactory.create("newUser", "other@example.com", "otherPass");

        userDataAccess = new ModifyUserDataAccessInterface() {
            @Override
            public User getUserByUsername(String username) {
                if (existingUser.getUsername().equals(username)) {
                    return existingUser;
                } else if (anotherUser.getUsername().equals(username)) {
                    return anotherUser;
                }
                return null;
            }

            @Override
            public boolean doesUsernameExist(String username) {
                return existingUser.getUsername().equals(username) || anotherUser.getUsername().equals(username);
            }

            @Override
            public boolean doesEmailExist(String email) {
                return existingUser.getEmail().equals(email) || anotherUser.getEmail().equals(email);
            }

            @Override
            public boolean updateUser(User user) {
                existingUser = user;
                return true;
            }
        };

        interactor = new ModifyUserInteractor(userDataAccess, outputBoundary);

        // Username already taken
        ModifyUserInputData inputData = new ModifyUserInputData(
                "currentUser",
                "newUser",
                "new@example.com",
                "currentPass",
                "newPass"
        );

        interactor.execute(inputData);

        assertNull(outputData);
        assertEquals("Username is already taken.", failMessage);
    }

    @Test
    void testExecute_EmailAlreadyTaken() {
        existingUser = userFactory.create("currentUser", "current@example.com", "currentPass");

        User anotherUser = userFactory.create("anotherUser", "new@example.com", "otherPass");

        userDataAccess = new ModifyUserDataAccessInterface() {
            @Override
            public User getUserByUsername(String username) {
                if (existingUser.getUsername().equals(username)) {
                    return existingUser;
                } else if (anotherUser.getUsername().equals(username)) {
                    return anotherUser;
                }
                return null;
            }

            @Override
            public boolean doesUsernameExist(String username) {
                return existingUser.getUsername().equals(username) || anotherUser.getUsername().equals(username);
            }

            @Override
            public boolean doesEmailExist(String email) {
                return existingUser.getEmail().equals(email) || anotherUser.getEmail().equals(email);
            }

            @Override
            public boolean updateUser(User user) {
                existingUser = user;
                return true;
            }
        };

        interactor = new ModifyUserInteractor(userDataAccess, outputBoundary);

        // Email already taken
        ModifyUserInputData inputData = new ModifyUserInputData(
                "currentUser",
                "newUser",
                "new@example.com",
                "currentPass",
                "newPass"
        );

        interactor.execute(inputData);

        assertNull(outputData);
        assertEquals("Email is already taken.", failMessage);
    }

    @Test
    void testExecute_NoUsernameChange() {
        existingUser = userFactory.create("currentUser", "current@example.com", "currentPass");

        // The username remains the same as the current username
        ModifyUserInputData inputData = new ModifyUserInputData(
                "currentUser",
                "currentUser",
                "new@example.com",
                "currentPass",
                "newPass"
        );

        interactor.execute(inputData);

        assertNull(failMessage);
        assertNotNull(outputData);
        assertEquals("currentUser", outputData.getUsername());
        assertEquals("User account successfully updated.", outputData.getMessage());

        assertEquals("currentUser", existingUser.getUsername());
        assertEquals("new@example.com", existingUser.getEmail());
        assertTrue(existingUser.verifyPassword("newPass"));
    }

    @Test
    void testExecute_NoEmailChange() {
        existingUser = userFactory.create("currentUser", "current@example.com", "currentPass");

        // The email remains the same as the current email
        ModifyUserInputData inputData = new ModifyUserInputData(
                "currentUser",
                "newUser",
                "current@example.com",
                "currentPass",
                "newPass"
        );

        interactor.execute(inputData);

        assertNull(failMessage);
        assertNotNull(outputData);
        assertEquals("newUser", outputData.getUsername());
        assertEquals("User account successfully updated.", outputData.getMessage());

        assertEquals("newUser", existingUser.getUsername());
        assertEquals("current@example.com", existingUser.getEmail());
        assertTrue(existingUser.verifyPassword("newPass"));
    }

    @Test
    void testExecute_UpdateFailure() {
        existingUser = userFactory.create("currentUser", "current@example.com", "currentPass");

        userDataAccess = new ModifyUserDataAccessInterface() {
            @Override
            public User getUserByUsername(String username) {
                return existingUser;
            }

            @Override
            public boolean doesUsernameExist(String username) {
                return false;
            }

            @Override
            public boolean doesEmailExist(String email) {
                return false;
            }

            @Override
            public boolean updateUser(User user) {
                return false;
            }
        };

        interactor = new ModifyUserInteractor(userDataAccess, outputBoundary);

        ModifyUserInputData inputData = new ModifyUserInputData(
                "currentUser",
                "newUser",
                "new@example.com",
                "currentPass",
                "newPass"
        );

        interactor.execute(inputData);

        assertNull(outputData);
        assertEquals("Failed to update user account.", failMessage);
    }

    @Test
    void testExecute_NewPasswordIsEmpty() {
        existingUser = userFactory.create("currentUser", "current@example.com", "currentPass");

        // Empty new password
        ModifyUserInputData inputData = new ModifyUserInputData(
                "currentUser",
                "newUser",
                "new@example.com",
                "currentPass",
                ""

        );

        interactor.execute(inputData);

        assertNotNull(failMessage);
        assertEquals("New password cannot be empty.", failMessage);

        assertNull(outputData);

        assertEquals("currentUser", existingUser.getUsername());

        assertEquals("current@example.com", existingUser.getEmail());

        assertTrue(existingUser.verifyPassword("currentPass"));
    }
}
