package app.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import app.interface_adapter.view_rsvp.ViewRSVPController;
import app.interface_adapter.view_rsvp.ViewRSVPState;
import app.interface_adapter.view_rsvp.ViewRSVPViewModel;

public class ViewRSVPView extends JPanel implements PropertyChangeListener {

    private static final String VIEW_NAME = "viewRSVP";
    private final ViewRSVPViewModel viewRSVPViewModel;
    private final JPanel eventsPanel;
    private final JButton backButton;
    private final JButton viewRSVPButton;
    private ViewRSVPController viewRSVPController;

    public ViewRSVPView(ViewRSVPViewModel viewRSVPViewModel) {
        this.viewRSVPViewModel = viewRSVPViewModel;
        this.viewRSVPViewModel.addPropertyChangeListener(this);

        // Set up the panel layout
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel for events
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JScrollPane eventsScrollPane = new JScrollPane(eventsPanel);
        eventsScrollPane.setBorder(new LineBorder(Color.GRAY, 1, true));
        this.add(eventsScrollPane, BorderLayout.CENTER);

        // Back button to return to the home view
        viewRSVPButton = new JButton("view RSVPs");
        viewRSVPButton.addActionListener(e -> viewRSVP());
        backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> goToHome());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(viewRSVPButton);
        buttonPanel.add(backButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Update the view whenever the ViewRSVPState changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ViewRSVPState state = (ViewRSVPState) evt.getNewValue();
        List<String> rsvpEvents = state.getViewRSVP();

        System.out.println("view rsvp event");
        eventsPanel.removeAll();

        // Add a title label at the top
        JLabel titleLabel = new JLabel("Your RSVPed Events:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set a larger, bold font for the title
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding around the title
        eventsPanel.add(titleLabel);

        if (rsvpEvents != null && !rsvpEvents.isEmpty()) {
            for (String eventName : rsvpEvents) {
                // Create a panel for each event to group the label and separator
                JPanel eventPanel = new JPanel();
                eventPanel.setLayout(new BorderLayout());
                eventPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Add padding to the panel

                // Create the event label
                JLabel eventLabel = new JLabel(eventName);
                eventLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Customize font size
                eventPanel.add(eventLabel, BorderLayout.CENTER);

                // Add the event panel to the main events panel
                eventsPanel.add(eventPanel);

                // Add a horizontal separator
                JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
                separator.setForeground(Color.GRAY);
                eventsPanel.add(separator);
            }
        } else {
            // Add a label indicating no events if the list is empty
            JLabel noEventsLabel = new JLabel("No RSVP events found.");
            noEventsLabel.setFont(new Font("Arial", Font.ITALIC, 18)); // Italic font for the message
            noEventsLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding around the label
            eventsPanel.add(noEventsLabel);
        }

        // Refresh the events panel
        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    private void viewRSVP() {
        ViewRSVPState currentState = viewRSVPViewModel.getState();
        viewRSVPController.execute(currentState.getUsernameState());
    }

    /**
     * Navigate back to the home view.
     */
    private void goToHome() {
        viewRSVPController.switchToHomeView();
    }

    /**
     * Set the ViewRSVPController for this view.
     *
     * @param controller The ViewRSVPController to set.
     */
    public void setViewRSVPController(ViewRSVPController controller) {
        this.viewRSVPController = controller;
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
