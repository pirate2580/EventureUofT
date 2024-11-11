package app.view;

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
import javax.swing.text.JTextComponent;

import app.interface_adapter.register.RegisterController;
import app.interface_adapter.register.RegisterState;
import app.interface_adapter.register.RegisterViewModel;

public class RegisterView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "register";

    private final RegisterViewModel registerViewModel;
    private final JTextField usernameInputField;
    private final JPasswordField passwordInputField;
    private final JButton registerButton;

    private RegisterController registerController;

    public RegisterView(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
        this.registerViewModel.addPropertyChangeListener(this);

        // Configure the layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Build components
        usernameInputField = createInputField("Enter your username...");
        passwordInputField = createPasswordField("Enter your password...");
        registerButton = createRegisterButton();

        // Add listeners
        addDocumentListener(usernameInputField, () -> updateState("username"));
        addDocumentListener(passwordInputField, () -> updateState("password"));

        // Add components to the panel
        this.add(createLabelTextPanel("Choose Username:", usernameInputField));
        this.add(Box.createVerticalStrut(15)); // Spacing
        this.add(createLabelTextPanel("Choose Password:", passwordInputField));
        this.add(Box.createVerticalStrut(30)); // Spacing
        this.add(createImageLabel("images/eventimage.png"));
        this.add(Box.createVerticalStrut(40)); // Extra spacing
        this.add(registerButton);
    }

    /**
     * Creates a styled input field with a placeholder.
     */
    private JTextField createInputField(String placeholder) {
        JTextField inputField = new JTextField(20);
        configureInputField(inputField, placeholder);
        return inputField;
    }

    /**
     * Creates a styled password field with a placeholder.
     */
    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(20);
        configureInputField(passwordField, placeholder);
        return passwordField;
    }

    /**
     * Configures common input field properties.
     */
    private void configureInputField(JTextComponent inputField, String placeholder) {
        inputField.setPreferredSize(new Dimension(500, 40));
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        inputField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setForeground(Color.GRAY);
        inputField.setText(placeholder);

        // Placeholder behavior
        inputField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (inputField.getText().equals(placeholder)) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (inputField.getText().isEmpty()) {
                    inputField.setForeground(Color.GRAY);
                    inputField.setText(placeholder);
                }
            }
        });
    }

    /**
     * Creates a styled "Register" button.
     */
    private JButton createRegisterButton() {
        JButton button = new JButton("Register");
        button.setPreferredSize(new Dimension(500, 40));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setBackground(new Color(72, 191, 103));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 153, 76)); // Darker green
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(72, 191, 103)); // Original color
            }
        });

        // Add action listener
        button.addActionListener(evt -> handleRegisterAction());
        return button;
    }

    /**
     * Handles the register button click event.
     */
    private void handleRegisterAction() {
        final RegisterState currentState = registerViewModel.getState();
        registerController.execute(currentState.getUsername(), currentState.getPassword());
    }

    /**
     * Creates a panel with a label and an input field.
     */
    private JPanel createLabelTextPanel(String labelText, JTextComponent inputField) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(inputField);
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Loads and displays an image with the specified path.
     */
    private JLabel createImageLabel(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return imageLabel;
    }

    /**
     * Adds a document listener to the input field.
     */
    private void addDocumentListener(JTextComponent inputField, Runnable updateAction) {
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                updateAction.run();
            }

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }
            @Override
            public void removeUpdate(DocumentEvent e) { update(); }
            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
        });
    }

    /**
     * Updates the state in the view model based on the input field.
     */
    private void updateState(String fieldType) {
        final RegisterState currentState = registerViewModel.getState();
        if ("username".equals(fieldType)) {
            currentState.setUsername(usernameInputField.getText());
        } else if ("password".equals(fieldType)) {
            currentState.setPassword(new String(passwordInputField.getPassword()));
        }
        registerViewModel.setState(currentState);
    }

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
        return VIEW_NAME;
    }

    public void setRegisterController(RegisterController controller) {
        this.registerController = controller;
    }

    public void setSignupController(RegisterController controller) {

    }
}
