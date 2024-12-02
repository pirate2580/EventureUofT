package app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import app.entity.Event.Event;
import app.interface_adapter.filter_event.FilterEventController;
import app.interface_adapter.filter_event.FilterEventState;
import app.interface_adapter.filter_event.FilterEventViewModel;
import app.interface_adapter.view_event.ViewEventController;

/**
 * This class represents the Filter Event view in the application.
 * It allows users to filter events based on selected categories, such as Music, Sports, Art & Culture, etc.
 * The filtered events are then displayed dynamically on the same screen.
 */
public class FilterEventView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "filterEvent";

    private final FilterEventViewModel filterEventViewModel;
    private final JCheckBox musicCheckbox;
    private final JCheckBox sportsCheckbox;
    private final JCheckBox artCultureCheckbox;
    private final JCheckBox foodDrinkCheckbox;
    private final JCheckBox educationCheckbox;
    private final JCheckBox travelCheckbox;
    private final JCheckBox gamingCheckbox;
    private final JCheckBox festivalCheckbox;

    private final JButton submitFilterButton;
    private final JButton backButton;
    private final JPanel eventsPanel;
    private final int border = 10;

    private FilterEventController filterEventController;
    private ViewEventController viewEventController;

    /**
     * Constructor for FilterEventView.
     * Initializes the components, sets up the layout, and adds listeners for user actions.
     *
     * @param filterEventViewModel The ViewModel for the filter event functionality.
     */
    public FilterEventView(FilterEventViewModel filterEventViewModel) {
        this.filterEventViewModel = filterEventViewModel;
        this.filterEventViewModel.addPropertyChangeListener(this);

        // Set layout to BorderLayout since we're using BorderLayout constraints
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(border, border, border, border));

        // Create a panel for checkboxes
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setBorder(new LineBorder(Color.GRAY, 1, true));

        // Initialize checkboxes
        musicCheckbox = new JCheckBox("Music");
        sportsCheckbox = new JCheckBox("Sports");
        artCultureCheckbox = new JCheckBox("Art and Culture");
        foodDrinkCheckbox = new JCheckBox("Food & Drink");
        educationCheckbox = new JCheckBox("Education");
        travelCheckbox = new JCheckBox("Travel");
        gamingCheckbox = new JCheckBox("Gaming");
        festivalCheckbox = new JCheckBox("Festival");

        // Add checkboxes to the panel
        checkboxPanel.add(musicCheckbox);
        checkboxPanel.add(sportsCheckbox);
        checkboxPanel.add(artCultureCheckbox);
        checkboxPanel.add(foodDrinkCheckbox);
        checkboxPanel.add(educationCheckbox);
        checkboxPanel.add(travelCheckbox);
        checkboxPanel.add(gamingCheckbox);
        checkboxPanel.add(festivalCheckbox);

        // Add the checkbox panel to the WEST of the main panel
        add(checkboxPanel, BorderLayout.WEST);

        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JScrollPane eventsScrollPane = new JScrollPane(eventsPanel);
        eventsScrollPane.setBorder(new LineBorder(Color.GRAY, 1, true));
        add(eventsScrollPane, BorderLayout.CENTER);

        // Create a submit button
        submitFilterButton = new JButton("Filter Events");
        submitFilterButton.addActionListener(e -> applyFilters());
        backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> goToHome());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitFilterButton);
        buttonPanel.add(backButton);

        // Add the submit button to the SOUTH
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Applies the selected filters and triggers the filtering logic.
     * Retrieves the selected categories and passes them to the filter event controller.
     */
    private void applyFilters() {
        // Get selected categories
        List<String> selectedCategories = new ArrayList<>();
        if (musicCheckbox.isSelected()) {
            selectedCategories.add("Music");
        }
        if (sportsCheckbox.isSelected()) {
            selectedCategories.add("Sports");
        }
        if (artCultureCheckbox.isSelected()) {
            selectedCategories.add("Art and Culture");
        }
        if (foodDrinkCheckbox.isSelected()) {
            selectedCategories.add("Food & Drink");
        }
        if (educationCheckbox.isSelected()) {
            selectedCategories.add("Education");
        }
        if (travelCheckbox.isSelected()) {
            selectedCategories.add("Travel");
        }
        if (gamingCheckbox.isSelected()) {
            selectedCategories.add("Gaming");
        }
        if (festivalCheckbox.isSelected()) {
            selectedCategories.add("Festival");
        }

        // Execute the filter event use case
        if (filterEventController != null) {
            filterEventController.execute(selectedCategories);
        }
        else {
            // Handle the case where the controller is not set
            JOptionPane.showMessageDialog(this,
                    "FilterEventController is not set.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the displayed events when the filtered results are received.
     * Clears the previous events and adds the new events to the panel.
     *
     * @param evt The property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        FilterEventState state = (FilterEventState) evt.getNewValue();
        List<Event> events = state.getFilteredEvents();
        final int borderPropertyChange = 10;

        // Clear the events panel
        eventsPanel.removeAll();
        if (events != null) {
            // Add each event to the panel
            for (Event event : events) {
                JPanel eventPanel = new JPanel();
                eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
                eventPanel.setBorder(new EmptyBorder(borderPropertyChange, borderPropertyChange,
                        borderPropertyChange, borderPropertyChange));

                // Ensure the panel spans the full width
                eventPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                eventPanel.setMaximumSize(new Dimension(eventsPanel.getWidth(), Integer.MAX_VALUE));

                JLabel titleLabel = new JLabel("Title: " + event.getTitle());
                JLabel organizerLabel = new JLabel("Organizer: " + event.getOrganizer());
                JLabel descriptionLabel = new JLabel("Description: " + event.getDescription());
                JLabel dateTimeLabel = new JLabel("Date & Time: " + event.getDateTime());
                JLabel capacityLabel = new JLabel("Capacity: " + event.getCapacity());
                JLabel tagsLabel = new JLabel("Tags: " + String.join(", ", event.getTags()));

                JButton viewEventButton = new JButton("View Event");
                viewEventButton.addActionListener(e -> viewEvent(event));

                eventPanel.add(titleLabel);
                eventPanel.add(organizerLabel);
                eventPanel.add(descriptionLabel);
                eventPanel.add(dateTimeLabel);
                eventPanel.add(capacityLabel);
                eventPanel.add(tagsLabel);
                eventPanel.add(viewEventButton);
                eventPanel.setBorder(new LineBorder(Color.GRAY));

                eventsPanel.add(eventPanel);
            }

            // Refresh the events panel
            eventsPanel.revalidate();
            eventsPanel.repaint();
        }
    }

    /**
     * Opens the detailed view of the selected event.
     * @param event The event whose details will be displayed.
     */
    private void viewEvent(Event event) {
        viewEventController.execute(event.getTitle());
    }

    /**
     * Switches to the home view.
     */
    private void goToHome() {
        filterEventController.switchToHomeView();
    }

    /**
     * Gets the name of the view.
     *
     * @return The view name.
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    /**
     * Sets the FilterEventController for this view.
     * @param controller The controller to set.
     */
    public void setFilterEventsController(FilterEventController controller) {
        this.filterEventController = controller;
    }

    /**
     * Sets the ViewEventController for this view.
     * @param controller The controller to set.
     */
    public void setViewEventController(ViewEventController controller) {
        this.viewEventController = controller;
    }
}
