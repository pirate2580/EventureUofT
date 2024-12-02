package app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import app.interface_adapter.notify_users.NotifyUserController;
import app.interface_adapter.view_created_events.ViewCreatedEventsController;
import app.interface_adapter.view_created_events.ViewCreatedEventsState;
import app.interface_adapter.view_created_events.ViewCreatedEventsViewModel;

public class ViewCreatedEventsView extends JPanel implements PropertyChangeListener {
    // Padding and Margins
    private static final int PANEL_PADDING = 10;
    private static final int EVENT_PADDING = 5;

    // Font Sizes
    private static final int TITLE_FONT_SIZE = 24;
    private static final int EVENT_LABEL_FONT_SIZE = 18;
    private static final int BUTTON_FONT_SIZE = 14;

    // Border Properties
    private static final int BORDER_THICKNESS = 1;
    private static final Color BORDER_COLOR = Color.GRAY;

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
        this.setBorder(new EmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

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
        titleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setBorder(new EmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));
        eventsPanel.add(titleLabel);

        if (createdEvents != null && !createdEvents.isEmpty()) {
            for (String eventName : createdEvents) {
                // Create a panel for each event
                JPanel eventPanel = new JPanel();
                eventPanel.setLayout(new BorderLayout());
                eventPanel.setBorder(new EmptyBorder(EVENT_PADDING, EVENT_PADDING, EVENT_PADDING, EVENT_PADDING));

                // Create the event label
                JLabel eventLabel = new JLabel(eventName);
                eventLabel.setFont(new Font("Arial", Font.PLAIN, EVENT_LABEL_FONT_SIZE));
                eventPanel.add(eventLabel, BorderLayout.WEST);

                // Create the "Send Notification Email" button
                JButton sendNotificationButton = new JButton("Send Notification Email to RSVPED users");
                sendNotificationButton.setFont(new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE));
                // Customize font size
                sendNotificationButton.addActionListener(e -> sendNotificationEmail(eventName));
                // Attach action listener

                eventPanel.add(sendNotificationButton, BorderLayout.EAST);

                // Add the event panel to the main events panel
                eventsPanel.add(eventPanel);

                // Add a horizontal separator
                JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
                separator.setForeground(Color.GRAY);
                eventsPanel.add(separator);
            }
        }
        else {
            // Add a label indicating no events if the list is empty
            JLabel noEventsLabel = new JLabel("No created events found.");
            noEventsLabel.setFont(new Font("Arial", Font.ITALIC, EVENT_LABEL_FONT_SIZE));
            // Italic font for the message
            noEventsLabel.setBorder(new EmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));
            // Add some padding around the label
            eventsPanel.add(noEventsLabel);
        }

        // Refresh the events panel
        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    private void sendNotificationEmail(String eventName) {
        notifyUserController.execute(eventName);
        JOptionPane.showMessageDialog(
                null,
                "You have notified all the users who have rsvped to " + eventName,
                "Notification success",
                JOptionPane.INFORMATION_MESSAGE
        );
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

    /**
     * Sets the controller responsible for handling the logic related to sending
     * notifications to users who have RSVP'd for events.
     *
     * @param controller the {@link NotifyUserController} instance to associate with this view.
     *                   This controller manages notification-related operations.
     * @throws IllegalArgumentException if the provided {@code controller} is null.
     */
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
