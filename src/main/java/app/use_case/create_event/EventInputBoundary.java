package app.use_case.create_event;

/**
 * Input Boundary for the actions relating to registration.
 */
public interface EventInputBoundary {

    /**
     * Execute the registration use case.
     * @param registerInputData the input data
     */
    void execute(EventInputData registerInputData);

    /**
     * Executes the switch to the login view use case once
     * registered.
     * TODO note this idea of switching to different usecases after a certain
     * use case outcome
     */
    void switchToLoginView();
}