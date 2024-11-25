package app.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import app.entity.Event.Event; // TODO: Naoroj: this follows CA right?

import app.interface_adapter.filter_event.FilterEventController;
import app.interface_adapter.filter_event.FilterEventState;
import app.interface_adapter.filter_event.FilterEventViewModel;

public class FilterEventView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "filter events";

    private final FilterEventViewModel filterEventViewModel;
    private final JCheckBox musicCheckbox; //= new JCheckBox("Music");
    private final JCheckBox sportsCheckbox; // = new JCheckBox("Sports");
    private final JCheckBox artCultureCheckbox; // = new JCheckBox("Art and Culture");
    private final JCheckBox foodDrinkCheckbox;
    private final JCheckBox educationCheckbox;
    private final JCheckBox travelCheckbox;
    private final JCheckBox gamingCheckbox;
    private final JCheckBox festivalCheckbox;

    private final JTextField locationTextField;

    private final JButton submitFilterButton;
    
    JList<String> filteredEventsInfo; // we get the filteredEvents as List<Event> and use toString to format it
    JScrollPane filteredEventsScrollPane;

    private JPanel parentPanel; // idk where needed
    private FilterEventController filterEventController;

    public FilterEventView(FilterEventViewModel filterEventViewModel) {
        this.filterEventViewModel = filterEventViewModel;
        this.filterEventViewModel.addPropertyChangeListener(this);



        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

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

        locationTextField = new JTextField(20);
        locationTextField.setText("Enter location here");

        // Add checkboxes to the panel
        checkboxPanel.add(musicCheckbox);
        checkboxPanel.add(sportsCheckbox);
        checkboxPanel.add(artCultureCheckbox);
        checkboxPanel.add(foodDrinkCheckbox);
        checkboxPanel.add(educationCheckbox);
        checkboxPanel.add(travelCheckbox);
        checkboxPanel.add(gamingCheckbox);
        checkboxPanel.add(festivalCheckbox);

        add(checkboxPanel, BorderLayout.WEST);

        // Create a list for filtered events
        filteredEventsInfo = new JList<>(new DefaultListModel<>());
        filteredEventsScrollPane = new JScrollPane(filteredEventsInfo);
        filteredEventsScrollPane.setBorder(new LineBorder(Color.GRAY, 1, true));

        add(filteredEventsScrollPane, BorderLayout.CENTER);
        // Create a submit button
        submitFilterButton = new JButton("Filter Events");
        submitFilterButton.addActionListener(e -> applyFilters());
        add(submitFilterButton, BorderLayout.SOUTH);
    }

    private void applyFilters() {
        // Get selected categories
        List<String> selectedCategories = new ArrayList<>();
        if (musicCheckbox.isSelected()) selectedCategories.add("Music");
        if (sportsCheckbox.isSelected()) selectedCategories.add("Sports");
        if (artCultureCheckbox.isSelected()) selectedCategories.add("Art and Culture");
        if (foodDrinkCheckbox.isSelected()) selectedCategories.add("Food & Drink");
        if (educationCheckbox.isSelected()) selectedCategories.add("Education");
        if (travelCheckbox.isSelected()) selectedCategories.add("Travel");
        if (gamingCheckbox.isSelected()) selectedCategories.add("Gaming");
        if (festivalCheckbox.isSelected()) selectedCategories.add("Festival");

        filterEventController.execute(selectedCategories);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        FilterEventState state = (FilterEventState) evt.getNewValue();
        System.out.println("New Value: " + evt.getNewValue());
        // there is no error state for it bc of the way its made, but it should update the Jlist
        // TODO: idk how this works
        if ("filteredEvents".equals(evt.getPropertyName())) {
            // Update the list of filtered events
            List<Event> events = (List<Event>) evt.getNewValue();
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Event event : events) {
                listModel.addElement(event.getTitle());
            }
            filteredEventsInfo.setModel(listModel);
        }
    }

    public void setParentPanel(JPanel parentPanel) {this.parentPanel = parentPanel;}


    public String getViewName() {return VIEW_NAME; }

    public void setFilterEventsController(FilterEventController controller) {this.filterEventController = controller;}

}
