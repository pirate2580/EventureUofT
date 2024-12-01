package app.use_case.create_event;

/**
 * Input Boundary for the actions relating to creating an event.
 */
public interface EventInputBoundary {

    /**
     * Execute the event creation use case.
     * @param eventInputData the input data
     */
    void execute(EventInputData eventInputData);

    /**
     * Executes the switch to the login view use case once
     * event created.
     * use case outcome
     */
    void switchToHomeView();
}
