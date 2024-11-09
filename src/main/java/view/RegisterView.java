package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.ImageIcon;


import interface_adapter.register.RegisterController;
import interface_adapter.register.RegisterState;
import interface_adapter.register.RegisterViewModel;

//TODO: The code follows lab-5:
//TODO: RegisterViewModel is a class containing info about Strings for the Labels and Buttons in the view
//TODO: When instantiates, it creates a new RegisterState that it stores in the state variable it inherits from
// ViewModel abstract class
public class RegisterView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "register";
    private final RegisterViewModel registerViewModel;
    // TODO: The line of code below the view listens for changes in the registerViewModel and thats how UI updates
    // TODO: triggered

    private final JTextField usernameInputField = new JTextField(20);
    private final JPasswordField passwordInputField = new JPasswordField(20);
    private RegisterController registerController;

    // TODO add button for login
    private final JButton register;

    public RegisterView(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
        registerViewModel.addPropertyChangeListener(this);

        // Configure input fields to take up full width
        usernameInputField.setPreferredSize(new Dimension(500, 40));
        usernameInputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameInputField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));

        passwordInputField.setPreferredSize(new Dimension(500, 40));
        passwordInputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordInputField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Choose username:"), usernameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Choose password:"), passwordInputField);

        usernameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Configure register button
        this.register = new JButton("Register");
        this.register.setPreferredSize(new Dimension(500, 40));
        this.register.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        this.register.setBackground(new Color(72, 191, 103));
        this.register.setForeground(Color.WHITE);
        this.register.setFocusPainted(false);
        this.register.setOpaque(true);
        this.register.setContentAreaFilled(true);
        this.register.setBorderPainted(false);
        this.register.setFont(new Font("Arial", Font.BOLD, 16));
        this.register.setAlignmentX(Component.CENTER_ALIGNMENT);

        register.addActionListener(evt -> {
            if (evt.getSource().equals(register)) {
                final RegisterState currentState = registerViewModel.getState();
                registerController.execute(currentState.getUsername(), currentState.getPassword());
            }
        });

        // TODO: Notice that these are from the methods below, when the view is initialized, it sets up the
        // TODO: document listeners for username and password
        // TODO: if you wnat to see for yourself, comment a line out, click register, youll see that only an empty string
        // TODO: is passed to DAO in the case its commented out
         addUsernameListener();
        addPasswordListener();

        // Layout configuration
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Load and configure the image
        ImageIcon eventsImage = new ImageIcon(new ImageIcon("images/eventimage.png").getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(eventsImage);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adding components to the panel
        this.add(usernameInfo);
        this.add(Box.createVerticalStrut(15));
        this.add(passwordInfo);
        this.add(Box.createVerticalStrut(30));
        this.add(imageLabel); // Centered larger image
        this.add(Box.createVerticalStrut(40)); // Extra spacing below the image
        this.add(register);

    }

    /**
     * TODO: NOTE THAT THIS IS A TEMPLATE METHOD FROM LAB 5 SO THAT WHEN USER CHANGES SOMETHING IN THEIR USERNAME
     * IT UPDATES UI IMMEDIATELY
     */
    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final RegisterState currentState = registerViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                registerViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    /**
     * TODO: NOTE THAT THIS IS A TEMPLATE METHOD FROM LAB 5 SO THAT WHEN USER CHANGES SOMETHING IN THEIR USERNAME
     * IT UPDATES UI IMMEDIATELY
     */
    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final RegisterState currentState = registerViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                registerViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    /**
     * TODO: COPIED FROM LAB WON'T DO ANYTHIGN
     * @param evt the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final RegisterState state = (RegisterState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return this.viewName;
    }

    public void setSignupController(RegisterController controller) {
        this.registerController = controller;
    }
}

class LabelTextPanel extends JPanel {
    LabelTextPanel(JLabel label, JTextField textField) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        this.add(label);
        this.add(Box.createVerticalStrut(5)); // Spacing between label and input
        this.add(textField);
        this.setOpaque(false); // Transparent background for styling
    }
}
