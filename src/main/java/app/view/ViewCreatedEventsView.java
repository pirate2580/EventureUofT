package app.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import app.interface_adapter.notify_users.NotifyUserController;
import app.interface_adapter.view_created_events.ViewCreatedEventsController;
import app.interface_adapter.view_created_events.ViewCreatedEventsState;
import app.interface_adapter.view_created_events.ViewCreatedEventsViewModel;

public class ViewCreatedEventsView extends JPanel implements PropertyChangeListener {

    private static final String VIEW_NAME = "viewCreated";
    private final ViewCreatedEventsViewModel viewCreatedEventsViewModel;
    private final JPanel eventsPanel;
    private final JButton backButton;
    private final JButton viewCreatedEventsButton;
    private ViewCreatedEventsController viewCreatedEventsController;
    private NotifyUserController notifyUserController;

    public ViewCreatedEventsView(ViewCreatedEventsViewModel viewCreatedEventsViewModel) {
        this.viewCreatedEventsViewModel = viewCreatedEventsViewModel;
        this.viewCreatedEventsViewModel.addPropertyChangeListener(this);

        // Set up the panel layout
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel for events
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JScrollPane eventsScrollPane = new JScrollPane(eventsPanel);
        eventsScrollPane.setBorder(new LineBorder(Color.GRAY, 1, true));
        this.add(eventsScrollPane, BorderLayout.CENTER);

        // Buttons to navigate or trigger actions
        viewCreatedEventsButton = new JButton("View Created Events");
        viewCreatedEventsButton.addActionListener(e -> viewCreatedEvents());
        backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> goToHome());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(viewCreatedEventsButton);
        buttonPanel.add(backButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Update the view whenever the ViewCreatedEventsState changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ViewCreatedEventsState state = (ViewCreatedEventsState) evt.getNewValue();
        List<String> createdEvents = state.getCreatedEvents();

        System.out.println("view created events");
        eventsPanel.removeAll();

        // Add a title label at the top
        JLabel titleLabel = new JLabel("Your Created Events:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set a larger, bold font for the title
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding around the title
        eventsPanel.add(titleLabel);

        if (createdEvents != null && !createdEvents.isEmpty()) {
            for (String eventName : createdEvents) {
                // Create a panel for each event
                JPanel eventPanel = new JPanel();
                eventPanel.setLayout(new BorderLayout());
                eventPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Add padding to the panel

                // Create the event label
                JLabel eventLabel = new JLabel(eventName);
                eventLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Customize font size
                eventPanel.add(eventLabel, BorderLayout.WEST);

                // Create the "Send Notification Email" button
                JButton sendNotificationButton = new JButton("Send Notification Email to RSVPED users");
                sendNotificationButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Customize font size
                sendNotificationButton.addActionListener(e -> sendNotificationEmail(eventName)); // Attach action listener

                eventPanel.add(sendNotificationButton, BorderLayout.EAST);

                // Add the event panel to the main events panel
                eventsPanel.add(eventPanel);

                // Add a horizontal separator
                JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
                separator.setForeground(Color.GRAY);
                eventsPanel.add(separator);
            }
        } else {
            // Add a label indicating no events if the list is empty
            JLabel noEventsLabel = new JLabel("No created events found.");
            noEventsLabel.setFont(new Font("Arial", Font.ITALIC, 18)); // Italic font for the message
            noEventsLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding around the label
            eventsPanel.add(noEventsLabel);
        }

        // Refresh the events panel
        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    private void sendNotificationEmail(String eventName) {
        notifyUserController.execute(eventName);
    }

    private void viewCreatedEvents() {
        ViewCreatedEventsState currentState = viewCreatedEventsViewModel.getState();
        viewCreatedEventsController.execute(currentState.getUsernameState());
    }

    /**
     * Navigate back to the home view.
     */
    private void goToHome() {
        viewCreatedEventsController.switchToHomeView();
    }

    /**
     * Set the ViewCreatedEventsController for this view.
     *
     * @param controller The ViewCreatedEventsController to set.
     */
    public void setViewCreatedEventsController(ViewCreatedEventsController controller) {
        this.viewCreatedEventsController = controller;
    }

    public void setNotificationController(NotifyUserController controller) {
        this.notifyUserController = controller;
    }

    /**
     * Get the view's name.
     *
     * @return The name of the view.
     */
    public String getViewName() {
        return VIEW_NAME;
    }
}
