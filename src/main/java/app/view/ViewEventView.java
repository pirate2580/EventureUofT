package app.view;

import app.entity.Event.Event;
import app.interface_adapter.filter_event.FilterEventState;
import app.interface_adapter.rsvp_event.RSVPController;
import app.interface_adapter.view_event.ViewEventController;
import app.interface_adapter.view_event.ViewEventState;
import app.interface_adapter.view_event.ViewEventViewModel;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ViewEventView extends JPanel implements PropertyChangeListener{
    private static final String VIEW_NAME = "viewEvent";

    private ViewEventViewModel viewEventViewModel;
    private final JButton rsvpButton;
    private final JButton homeButton;
    private ViewEventController viewEventController;
    private RSVPController rsvpController;

    public ViewEventView(ViewEventViewModel viewEventViewModel) {
        this.viewEventViewModel = viewEventViewModel;
        this.viewEventViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        rsvpButton = new JButton("RSVP");
        rsvpButton.addActionListener(e -> rsvpEvent());

        homeButton = new JButton("Go Home");
        homeButton.addActionListener(e -> goBackHome());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Align buttons horizontally
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
            eventDetailsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

            // Add labels with VERY large fonts
            JLabel titleLabel = new JLabel("Title: " + event.getTitle());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 36));

            JLabel organizerLabel = new JLabel("Organizer: " + event.getOrganizer());
            organizerLabel.setFont(new Font("Arial", Font.PLAIN, 30));

            JLabel descriptionLabel = new JLabel("<html>Description: " + event.getDescription() + "</html>");
            descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 30));

            JLabel dateTimeLabel = new JLabel("Date & Time: " + event.getDateTime());
            dateTimeLabel.setFont(new Font("Arial", Font.PLAIN, 30));

            JLabel capacityLabel = new JLabel("Capacity: " + event.getCapacity());
            capacityLabel.setFont(new Font("Arial", Font.PLAIN, 30));

            JLabel tagsLabel = new JLabel("Tags: " + String.join(", ", event.getTags()));
            tagsLabel.setFont(new Font("Arial", Font.PLAIN, 30));

            // Add labels to the details panel
            eventDetailsPanel.add(titleLabel);
            eventDetailsPanel.add(Box.createVerticalStrut(15));
            eventDetailsPanel.add(organizerLabel);
            eventDetailsPanel.add(Box.createVerticalStrut(15));
            eventDetailsPanel.add(descriptionLabel);
            eventDetailsPanel.add(Box.createVerticalStrut(15));
            eventDetailsPanel.add(dateTimeLabel);
            eventDetailsPanel.add(Box.createVerticalStrut(15));
            eventDetailsPanel.add(capacityLabel);
            eventDetailsPanel.add(Box.createVerticalStrut(15));
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
            this.invalidate(); // Mark the panel as invalid
            this.revalidate(); // Recalculate layout
            this.repaint();    // Trigger a repaint
        }
    }


    public void goBackHome() {
        viewEventController.switchToHomeView();
    }

    public String getViewName(){
        return VIEW_NAME;
    }

    public void setViewEventController(ViewEventController controller) {
        this.viewEventController = controller;
    }

    public void setRSVPEventController (RSVPController controller) {
        this.rsvpController = controller;
    }
}
