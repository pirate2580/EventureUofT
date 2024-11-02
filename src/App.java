import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

// Note: app does not work yet.
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // create the frame (main window)
        JFrame frame = new JFrame("Basic Swing App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // create button
        JButton button = new JButton("Click Me");

        // add action listener to the button
        button.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Hello, Swing!"));

        // add button to frame
        frame.getContentPane().add(button);

        // make frame visible
        frame.setVisible(true);
    }
}
