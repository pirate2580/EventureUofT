package app.use_case.modify_user;

import app.entity.User.User;
import org.springframework.stereotype.Component;


@Component
public class ModifyUserInteractor implements ModifyUserInputBoundary {
    private final ModifyUserDataAccessInterface userDataAccess;
    private final ModifyUserOutputBoundary outputBoundary;


    public ModifyUserInteractor(ModifyUserDataAccessInterface userDataAccess,
                                ModifyUserOutputBoundary outputBoundary) {
        this.userDataAccess = userDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(ModifyUserInputData modifyUserInputData) {
        User user = userDataAccess.getUserByUsername(modifyUserInputData.getUsername());

        if (user == null) {
            outputBoundary.prepareFailView("User not found.");
            return;
        }

        // Verify the provided old password before making changes
        if (!user.verifyPassword(modifyUserInputData.getOldPassword())) {
            outputBoundary.prepareFailView("Invalid password provided.");
            return;
        }

        // Check if the new username or email is already taken
        if (!user.getUsername().equals(modifyUserInputData.getNewUsername()) &&
                userDataAccess.doesUsernameExist(modifyUserInputData.getNewUsername())) {
            outputBoundary.prepareFailView("Username is already taken.");
            return;
        }

        if (!user.getEmail().equals(modifyUserInputData.getNewEmail()) &&
                userDataAccess.doesEmailExist(modifyUserInputData.getNewEmail())) {
            outputBoundary.prepareFailView("Email is already taken.");
            return;
        }

        // Update user details
        user.setUsername(modifyUserInputData.getNewUsername());
        user.setEmail(modifyUserInputData.getNewEmail());

        if (!modifyUserInputData.getNewPassword().isEmpty()) {
            user.setPassword(modifyUserInputData.getNewPassword());
        }

        boolean updateSuccessful = userDataAccess.updateUser(user);

        if (updateSuccessful) {
            ModifyUserOutputData outputData = new ModifyUserOutputData(user.getUsername(), "User account successfully updated.");
            outputBoundary.prepareSuccessView(outputData);
        } else {
            outputBoundary.prepareFailView("Failed to update user account.");
        }
    }
}




