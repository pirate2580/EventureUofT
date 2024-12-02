package app.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import app.interface_adapter.register.RegisterController;
import app.interface_adapter.register.RegisterState;
import app.interface_adapter.register.RegisterViewModel;

public class RegisterView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "register";

    private static final int PANEL_PADDING = 20;
    private static final int INPUT_FIELD_WIDTH = 400;
    private static final int INPUT_FIELD_HEIGHT = 30;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 40;
    private static final int VERTICAL_SPACING_HIGH = 5;
    private static final int VERTICAL_SPACING_SMALL = 10;
    private static final int VERTICAL_SPACING_MEDIUM = 20;
    private static final int VERTICAL_SPACING_EXTRA_LARGE = 50;

    // Font Sizes
    private static final int TITLE_FONT_SIZE = 32;
    private static final int INPUT_LABEL_FONT_SIZE = 14;
    private static final int BUTTON_FONT_SIZE = 16;

    private final RegisterViewModel registerViewModel;
    private final JTextField usernameInputField;
    private final JTextField emailInputField;
    private final JPasswordField passwordInputField;
    private final JButton signupButton;
    private final JButton loginButton;
    private RegisterController registerController;

    public RegisterView(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
        this.registerViewModel.addPropertyChangeListener(this);

        // Configure panel layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.decode("#D3D3D3"));
        this.setBorder(new EmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        // Add "EventureUofT" heading
        JLabel titleLabel = new JLabel("EventHiveUofT", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.decode("#2E4C34"));

        // Build input fields
        usernameInputField = createInputField("username");
        emailInputField = createInputField("email");
        passwordInputField = createPasswordField();

        // Create buttons
        signupButton = createButton("Sign up", evt -> handleSignupAction());
        loginButton = createButton("Log in", evt -> handleLoginAction());

        // Add listeners to input fields
        addDocumentListener(usernameInputField, () -> updateState("username"));
        addDocumentListener(emailInputField, () -> updateState("email"));
        addDocumentListener(passwordInputField, () -> updateState("password"));

        // Add components to panel
        this.add(Box.createVerticalStrut(VERTICAL_SPACING_SMALL));
        this.add(titleLabel);
        this.add(Box.createVerticalStrut(VERTICAL_SPACING_MEDIUM));
        this.add(createLabelTextPanel("username", usernameInputField));
        this.add(Box.createVerticalStrut(VERTICAL_SPACING_SMALL));
        this.add(createLabelTextPanel("email", emailInputField));
        this.add(Box.createVerticalStrut(INPUT_FIELD_HEIGHT));
        this.add(createLabelTextPanel("password", passwordInputField));
        this.add(Box.createVerticalStrut(VERTICAL_SPACING_EXTRA_LARGE));
        this.add(createButtonPanel());
    }

    private JTextField createInputField(String placeholder) {
        JTextField inputField = new JTextField(VERTICAL_SPACING_MEDIUM);
        configureInputField(inputField, placeholder);
        return inputField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField(VERTICAL_SPACING_MEDIUM);
        configureInputField(passwordField, "password");
        return passwordField;
    }

    private void configureInputField(JTextComponent inputField, String placeholder) {
        inputField.setPreferredSize(new Dimension(INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT));
        inputField.setMaximumSize(new Dimension(INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT));
        inputField.setMinimumSize(new Dimension(INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT));
        inputField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        inputField.setFont(new Font("Arial", Font.PLAIN, INPUT_LABEL_FONT_SIZE));
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

    private JButton createButton(String text, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setBackground(Color.decode("#48BF67"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.addActionListener(actionListener);

        // Add hover effect (simply make bg darker)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#2E7A46"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#48BF67"));
            }
        });

        return button;
    }


    private JPanel createLabelTextPanel(String labelText, JTextComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText, JLabel.LEFT);
        label.setFont(new Font("Arial", Font.PLAIN, INPUT_LABEL_FONT_SIZE));
        panel.add(label);
        panel.add(Box.createVerticalStrut(VERTICAL_SPACING_HIGH));
        panel.add(inputField);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, VERTICAL_SPACING_MEDIUM, 0));
        buttonPanel.setOpaque(false);

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        return buttonPanel;
    }

    private void addDocumentListener(JTextComponent inputField, Runnable updateAction) {
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateAction.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateAction.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateAction.run();
            }
        });
    }

    private void updateState(String fieldType) {
        RegisterState currentState = registerViewModel.getState();
        if ("username".equals(fieldType)) {
            currentState.setUsername(usernameInputField.getText());
        }
        else if ("password".equals(fieldType)) {
            currentState.setPassword(new String(passwordInputField.getPassword()));
        }
        else if ("email".equals(fieldType)) {
            currentState.setEmail(emailInputField.getText());
        }
        registerViewModel.setState(currentState);
    }

    private void handleSignupAction() {
        RegisterState currentState = registerViewModel.getState();
        registerController.execute(currentState.getUsername(), currentState.getEmail(), currentState.getPassword());
    }

    private void handleLoginAction() {
        System.out.println("navigating to login");
        registerController.switchToLoginView();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        RegisterState state = (RegisterState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    /**
     * Retrieves the name of this view. This name is used for navigation
     * purposes within the application.
     * @return the name of this view as a {@link String}.
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    /**
     * Sets the controller responsible for handling user interactions
     * and business logic related to registration.
     * @param controller the {@link RegisterController} instance to associate
     *                   with this view. It must not be null.
     * @throws IllegalArgumentException if {@code controller} is null.
     */
    public void setRegisterController(RegisterController controller) {
        this.registerController = controller;
    }
}
