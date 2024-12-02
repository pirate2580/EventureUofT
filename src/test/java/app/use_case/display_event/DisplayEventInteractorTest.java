package app.use_case.display_event;

import app.entity.Event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DisplayEventInteractorTest {
    private DisplayEventDataAccessInterface displayEventDataAccessInterface;
    private DisplayEventOutputBoundary displayPresenter;
    private DisplayEventInteractor interactor;
    private ArrayList<ArrayList<Object>> events;
    private String failMessage;

    @BeforeEach
    void setUp() {
        failMessage = null;

        displayEventDataAccessInterface = new DisplayEventDataAccessInterface() {
            @Override
            public ArrayList<ArrayList<Object>> eventDetails() {
                ArrayList<ArrayList<Object>> events = new ArrayList<>();
                ArrayList<Object> eventOne = new ArrayList<>();
                eventOne.add("1");
                ArrayList<Object> eventTwo = new ArrayList<>();
                eventTwo.add("2");

                events.add(eventOne);
                events.add(eventTwo);
                return events;
            }
        };

        displayPresenter = new DisplayEventOutputBoundary() {
            @Override
            public void prepareSuccessView(DisplayEventOutputData displayEventOutputData) {
                events = displayEventOutputData.getEvents();
            }

            @Override
            public void prepareFailView(String errorMessage) {
                failMessage = errorMessage;
            }
        };

        interactor = new DisplayEventInteractor(displayEventDataAccessInterface, displayPresenter);
    }


    @Test
    void testExecute_Success() {
        ArrayList<ArrayList<Object>> input = interactor.execute();
        assertNotNull(input);
        assertEquals(input.get(0).get(0), "1");
        assertEquals(input.get(1).get(0), "2");
    }
}
