package app.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import app.entity.Event.Event;
import app.interface_adapter.rsvp_event.RSVPController;
import app.interface_adapter.view_event.ViewEventController;
import app.interface_adapter.view_event.ViewEventState;
import app.interface_adapter.view_event.ViewEventViewModel;

public class ViewEventView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "viewEvent";

    // Padding and Margins
    private static final int PANEL_PADDING_SMALL = 10;
    private static final int PANEL_PADDING_LARGE = 20;
    private static final int VERTICAL_SPACING = 15;

    // Font Sizes
    private static final int TITLE_FONT_SIZE = 36;
    private static final int LABEL_FONT_SIZE_LARGE = 30;

    private ViewEventViewModel viewEventViewModel;
    private final JButton rsvpButton;
    private final JButton homeButton;
    private ViewEventController viewEventController;
    private RSVPController rsvpController;

    public ViewEventView(ViewEventViewModel viewEventViewModel) {
        this.viewEventViewModel = viewEventViewModel;
        this.viewEventViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(PANEL_PADDING_SMALL, PANEL_PADDING_SMALL,
                PANEL_PADDING_SMALL, PANEL_PADDING_SMALL));

        rsvpButton = new JButton("RSVP");
        rsvpButton.addActionListener(e -> rsvpEvent());

        homeButton = new JButton("Go Home");
        homeButton.addActionListener(e -> goBackHome());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        // Align buttons horizontally
        buttonPanel.add(rsvpButton);
        buttonPanel.add(homeButton);

        // Add the button panel to the bottom (SOUTH) of the main layout
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void rsvpEvent() {
        ViewEventState currentState = viewEventViewModel.getState();
        rsvpController.execute(currentState.getUsernameState(), currentState.getViewEvent().getEventId());
        JOptionPane.showMessageDialog(
                null,
                "You have RSVPed to: " + currentState.getViewEvent().getEventId(),
                "RSVP Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ViewEventState state = (ViewEventState) evt.getNewValue();
        Event event = state.getViewEvent();

        this.removeAll();
        if (event != null) {
            JPanel eventDetailsPanel = new JPanel();
            eventDetailsPanel.setLayout(new BoxLayout(eventDetailsPanel, BoxLayout.Y_AXIS));
            eventDetailsPanel.setBorder(new EmptyBorder(PANEL_PADDING_LARGE, PANEL_PADDING_LARGE,
                    PANEL_PADDING_LARGE, PANEL_PADDING_LARGE));

            // Add labels with VERY large fonts
            JLabel titleLabel = new JLabel("Title: " + event.getTitle());
            titleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_SIZE));

            JLabel organizerLabel = new JLabel("Organizer: " + event.getOrganizer());
            organizerLabel.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT_SIZE_LARGE));

            JLabel descriptionLabel = new JLabel("<html>Description: " + event.getDescription() + "</html>");
            descriptionLabel.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT_SIZE_LARGE));

            JLabel dateTimeLabel = new JLabel("Date & Time: " + event.getDateTime());
            dateTimeLabel.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT_SIZE_LARGE));

            JLabel capacityLabel = new JLabel("Capacity: " + event.getCapacity());
            capacityLabel.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT_SIZE_LARGE));

            JLabel tagsLabel = new JLabel("Tags: " + String.join(", ", event.getTags()));
            tagsLabel.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT_SIZE_LARGE));

            // Add labels to the details panel
            eventDetailsPanel.add(titleLabel);
            eventDetailsPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
            eventDetailsPanel.add(organizerLabel);
            eventDetailsPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
            eventDetailsPanel.add(descriptionLabel);
            eventDetailsPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
            eventDetailsPanel.add(dateTimeLabel);
            eventDetailsPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
            eventDetailsPanel.add(capacityLabel);
            eventDetailsPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
            eventDetailsPanel.add(tagsLabel);

            // Add the details panel to the center of the main layout
            this.add(eventDetailsPanel, BorderLayout.CENTER);

            // Add buttons back at the bottom
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(rsvpButton);
            buttonPanel.add(homeButton);

            this.add(buttonPanel, BorderLayout.SOUTH);

            // Forcefully revalidate and repaint to ensure the UI updates
            this.invalidate();
            // Mark the panel as invalid
            this.revalidate();
            // Recalculate layout
            this.repaint();
            // Trigger a repaint
        }
    }

    /**
     * Navigates back to the home view by delegating the action
     * to the associated {@link ViewEventController}.
     */
    public void goBackHome() {
        viewEventController.switchToHomeView();
    }

    /**
     * Retrieves the name of this view. This name is used for navigation
     * purposes within the application.
     *
     * @return the name of this view as a {@link String}.
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    /**
     * Sets the controller responsible for handling the logic and
     * navigation actions related to viewing events.
     * @param controller the {@link ViewEventController} instance to associate
     *                   with this view. It must not be null.
     * @throws IllegalArgumentException if the provided {@code controller} is null.
     */
    public void setViewEventController(ViewEventController controller) {
        this.viewEventController = controller;
    }

    /**
     * Sets the controller responsible for handling RSVP actions for events.
     * @param controller the {@link RSVPController} instance to associate
     *                   with this view. It must not be null.
     * @throws IllegalArgumentException if the provided {@code controller} is null.
     */
    public void setRSVPEventController(RSVPController controller) {
        this.rsvpController = controller;
    }
}
