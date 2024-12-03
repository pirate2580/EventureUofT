package app.use_case.home;

import app.entity.Event.CommonEventFactory;
import app.entity.Event.EventFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeInteractorTest {
    private HomeOutputBoundary homePresenter;
    private HomeInteractor interactor;
    private boolean switchToLoginViewCalled;
    private boolean switchToCreateEventViewCalled;
    private boolean switchToFilterEventViewCalled;
    private boolean switchToViewRSVPViewCalled;
    private boolean switchToViewCreatedEventsViewCalled;
    private boolean switchToModifyEventViewCalled;
    private boolean executed;

    private EventFactory eventFactory;

    @BeforeEach
    void setUp() {
        switchToLoginViewCalled = false;
        switchToCreateEventViewCalled = false;
        switchToFilterEventViewCalled = false;
        switchToViewRSVPViewCalled = false;
        switchToViewCreatedEventsViewCalled = false;
        switchToModifyEventViewCalled = false;

        eventFactory = new CommonEventFactory();

        homePresenter = new HomeOutputBoundary() {
            @Override
            public void switchToCreateEventView() {
                switchToCreateEventViewCalled = true;
            }

            @Override
            public void switchToLoginView() {
                switchToLoginViewCalled = true;
            }

            @Override
            public void switchToFilterEventView() {
                switchToFilterEventViewCalled = true;
            }

            @Override
            public void switchToViewRSVPView() {
                switchToViewRSVPViewCalled = true;
            }

            @Override
            public void switchToViewCreatedEventsView() {
                switchToViewCreatedEventsViewCalled = true;
            }

            @Override
            public void switchToModifyEventView() {
                switchToModifyEventViewCalled = true;
            }

        };
        interactor = new HomeInteractor(homePresenter);
    }
    @Test
    void testSwitchToLoginView() {
        interactor.switchToLoginView();
        assertTrue(switchToLoginViewCalled, "switchToLoginView should have been called");
    }
    @Test
    void testSwitchToCreateEventView() {
        interactor.switchtoCreateEventView();
        assertTrue(switchToCreateEventViewCalled, "switchToCreateEventView should have been called");
    }
    @Test
    void testSwitchToFilterEventView() {
        interactor.switchToFilterEventView();
        assertTrue(switchToFilterEventViewCalled, "switchToFilterEventView should have been called");
    }

    @Test
    void testSwitchToViewRSVPView() {
        interactor.switchToViewRSVPView();
        assertTrue(switchToViewRSVPViewCalled, "switchToViewRSVPView should have been called");
    }

    @Test
    void testExecute() {
        assertDoesNotThrow(() -> interactor.execute(), "execute should not throw any exceptions");
    }

    @Test
    void testSwitchToViewCreatedEventsView() {
        interactor.switchToViewCreatedEventsView();
        assertTrue(switchToViewCreatedEventsViewCalled, "switchToViewCreatedEventsView should have been called");
    }

    @Test
    void testSwitchToModifyEventView() {
        interactor.switchToModifyEventView();
        assertTrue(switchToModifyEventViewCalled, "switchToModifyEventView should have been called");
    }


}
