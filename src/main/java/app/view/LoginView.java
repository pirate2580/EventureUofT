package app.view;

import app.interface_adapter.login.LoginController;
import app.interface_adapter.login.LoginState;
import app.interface_adapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "login";
    private JPanel parentPanel;
    private final LoginViewModel loginViewModel;
    private final JTextField usernameInputField;
    private final JPasswordField passwordInputField;
    private final JButton signupButton;
    private final JButton loginButton;
    private LoginController loginController;

    public LoginView(LoginViewModel loginViewModel) {

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.decode("#D3D3D3"));
        this.setBorder(new EmptyBorder(20, 20, 20 ,20));

        JLabel titleLabel = new JLabel("EventureUofT", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.decode("#2E4C34")); // Dark green color

        // Build input fields
        usernameInputField = createInputField("username");
        passwordInputField = createPasswordField();

        signupButton = createButton("Sign up", evt -> handleSignupAction());
        loginButton = createButton("Log in", evt -> handleLoginAction());

        addDocumentListener(usernameInputField, () -> updateState("username"));
        addDocumentListener(passwordInputField, () -> updateState("password"));

        this.add(Box.createVerticalStrut(10));
        this.add(titleLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(createLabelTextPanel("username", usernameInputField));
        this.add(Box.createVerticalStrut(10));
        this.add(createLabelTextPanel("password", passwordInputField));
        this.add(Box.createVerticalStrut(30));
        this.add(createButtonPanel());
    }
    public String getViewName() {
        return VIEW_NAME; // return string instead of object
    }

    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    //  navigate to a different view
    public void navigateTo(String viewName) {
        if (parentPanel != null && parentPanel.getLayout() instanceof java.awt.CardLayout) {
            System.out.println("Navigating to: " + viewName); // Debug log
            java.awt.CardLayout layout = (java.awt.CardLayout) parentPanel.getLayout();
            layout.show(parentPanel, viewName);
        } else {
            System.out.println("Navigation failed: parentPanel or layout is null");
        }
    }

    private JTextField createInputField(String placeholder) {
        JTextField inputField = new JTextField(20);
        configureInputField(inputField, placeholder);
        return inputField;
    }
    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        configureInputField(passwordField, "password");
        return passwordField;
    }

    private void configureInputField(JTextComponent inputField, String placeholder) {
        inputField.setPreferredSize(new Dimension(400, 30));
        inputField.setMaximumSize(new Dimension(400, 30));
        inputField.setMinimumSize(new Dimension(400, 30));
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

    private JButton createButton(String text, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(150, 40));
        button.setBackground(Color.decode("#48BF67"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.addActionListener(actionListener);

        // Add hover effect
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
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(inputField);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
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
        LoginState currentState = loginViewModel.getState();
        if ("username".equals(fieldType)) {
            currentState.setUsername(usernameInputField.getText());
        } else if ("password".equals(fieldType)) {
            currentState.setPassword(new String(passwordInputField.getPassword()));
        }
        loginViewModel.setState(currentState);
    }

    private void handleSignupAction() {
        navigateTo("register");
    }

    private void handleLoginAction() {
        System.out.println("test");
        System.out.println("implement firebase logic here");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    // TODO: Find way to use this? idk
    public void setLoginController(LoginController loginController){
        this.loginController = loginController;
    }
}
