package app.use_case.NotifyUsers;

import app.use_case.notify_users.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotifyUserInteractorTest {

    private NotifyUserDataAccessInterface notifyUserDataAccessInterface;
    private NotifyUserOutputBoundary notifyUserPresenter;
    private NotifyUserInteractor interactor;

    private String notificationMessageSent;
    private String eventTitleReceivedByPresenter;
    private String errorMessageReceivedByPresenter;

    @BeforeEach
    void setUp() {
        notificationMessageSent = null;
        eventTitleReceivedByPresenter = null;
        errorMessageReceivedByPresenter = null;

        notifyUserDataAccessInterface = eventTitle -> notificationMessageSent = eventTitle;

        notifyUserPresenter = new NotifyUserOutputBoundary() {
            @Override
            public void prepareSuccessView(NotifyUserOutputData outputData) {
                eventTitleReceivedByPresenter = outputData.getEventTitle();
            }

            @Override
            public void prepareFailView(String errorMessage) {
                errorMessageReceivedByPresenter = errorMessage;
            }
        };

        interactor = new NotifyUserInteractor(notifyUserDataAccessInterface, notifyUserPresenter);
    }

    @Test
    void testExecute_Success() {
        NotifyUserInputData inputData = new NotifyUserInputData("Sample Event");

        interactor.execute(inputData);

        assertEquals("Sample Event", notificationMessageSent, "The notification message sent does not match the event name.");
        assertEquals("notification sent", eventTitleReceivedByPresenter, "The event title received by the presenter does not match.");
        assertNull(errorMessageReceivedByPresenter, "No error message should have been received.");
    }

    @Test
    void testExecute_Failure() {
        notifyUserDataAccessInterface = eventTitle -> {
            throw new IllegalStateException("Notification system failed.");
        };

        interactor = new NotifyUserInteractor(notifyUserDataAccessInterface, notifyUserPresenter);

        NotifyUserInputData inputData = new NotifyUserInputData("Fail Event");

        Exception exception = assertThrows(IllegalStateException.class, () -> interactor.execute(inputData));
        assertEquals("Notification system failed.", exception.getMessage(), "The exception message is incorrect.");

        assertNull(eventTitleReceivedByPresenter, "No event title should have been received by the presenter on failure.");
        assertNull(errorMessageReceivedByPresenter, "The failure view should not have been prepared since the error is directly thrown.");
    }

    @Test
    void testExecute_PresenterFailure() {
        notifyUserPresenter = new NotifyUserOutputBoundary() {
            @Override
            public void prepareSuccessView(NotifyUserOutputData outputData) {
                throw new RuntimeException("Presenter failure.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                errorMessageReceivedByPresenter = errorMessage;
            }
        };

        interactor = new NotifyUserInteractor(notifyUserDataAccessInterface, notifyUserPresenter);

        NotifyUserInputData inputData = new NotifyUserInputData("Presenter Fail Event");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> interactor.execute(inputData));
        assertEquals("Presenter failure.", exception.getMessage(), "The exception message is incorrect.");

        assertEquals("Presenter Fail Event", notificationMessageSent, "The notification message should still have been sent.");
        assertNull(eventTitleReceivedByPresenter, "No event title should have been received by the presenter on failure.");
    }
}