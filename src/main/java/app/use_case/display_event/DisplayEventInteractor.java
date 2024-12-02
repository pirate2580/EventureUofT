package app.use_case.display_event;

import java.util.ArrayList;

import app.data_access.EventDAO;
import app.entity.Event.EventFactory;

/**
 * The Event Interactor
 * The Event Interactor handles the input
 * If a user with the inputted username exists, then get the userPresenter to prepare a fail view on frontend
 * Do a similar thing for other errors
 * Otherwise, interact with the database through hte userDAO to save the user and prepare a success view to the
 * front end.
 * NOTE: userPresenter is just the outputBoundary class
 * Note: EventInteractor is an implementation of the inputBoundary interface
 */
public class DisplayEventInteractor implements DisplayEventInputBoundary {
    private final DisplayEventDataAccessInterface displayEventDataAccessObject;
    private final DisplayEventOutputBoundary displayEventPresenter;

    /**
     * Constructs a new {@link DisplayEventInteractor}.
     *
     * @param eventDataAccessObject The DAO for accessing event data. Must not be {@code null}.
     * @param eventPresenter        The output boundary for preparing the view. Must not be {@code null}.
     */
    public DisplayEventInteractor(DisplayEventDataAccessInterface eventDataAccessObject,
                                  DisplayEventOutputBoundary eventPresenter) {
        this.displayEventDataAccessObject = eventDataAccessObject;
        this.displayEventPresenter = eventPresenter;
    }

    /**
     * Executes the Display Event Use Case.
     * Retrieves event details using the DAO, prepares the output data, and sends it to the
     * presenter for displaying on the front end.
     * @return A nested {@link ArrayList} containing event details.
     *         Each inner {@link ArrayList} represents a single event's properties.
     */
    @Override
    public ArrayList<ArrayList<Object>> execute() {
        ArrayList<ArrayList<Object>> events = displayEventDataAccessObject.eventDetails();

        DisplayEventOutputData outputData = new DisplayEventOutputData(events);
        displayEventPresenter.prepareSuccessView(outputData);

        return events;
    }
}
