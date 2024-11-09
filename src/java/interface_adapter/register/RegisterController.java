package java.interface_adapter.register;


import java.use_case.register.RegisterInputBoundary;
import java.use_case.register.RegisterInputData;


/**
 * Controller for the Register Use Case
 * TODO: NOTE: This class leverages InputBondary to create InputData Datastructure
 * TODO: that the Use Case Interactor can work with
 * TODO: Naoroj: I suppose it feels odd for the inputBoundary to be in a different folder
 * TODO: but this follows Clean Architecture since the Controller is lower level than UseCase files
 */
public class RegisterController {

    private final RegisterInputBoundary userRegisterUseCaseInteractor;

    public RegisterController(RegisterInputBoundary userRegisterUseCaseInteractor) {
        this.userRegisterUseCaseInteractor = userRegisterUseCaseInteractor;
    }

    /**
     * Execute the Register use Case
     * @param username
     * @param password
     * TODO: Naoroj: Note that the registerController takes in the use case interactor and once it prepares
     * the data into the data structure inputData, it executes the use case interactor
     */
    public void execute(String username, String password) {
        final RegisterInputData registerInputData = new RegisterInputData(
                username, password);

        this.userRegisterUseCaseInteractor.execute(registerInputData);
    }

//    /**
//     * Execute the switch to LoginView Use case
//     */
//    public void switchToLoginView(){
//        this.userRegisterUseCaseInteractor.switchToLoginView();
//    }
}
