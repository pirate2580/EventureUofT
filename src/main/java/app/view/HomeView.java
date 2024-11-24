package app.view;

// Import required libraries and imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

// Import applications specific classes for map functionality and JXMapViewer
import app.interface_adapter.home.HomeViewModel;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.*;

public class HomeView extends JPanel implements PropertyChangeListener {
    // Constant for the view name for navigation purposes
    private static final String VIEW_NAME = "Home";

    // View model required for managing logic for view
    private final HomeViewModel homeViewModel;
    private JPanel parentPanel;

    // Buttons for zooming in, out, logging out, and creating an event
    private final JButton zoomInButton;
    private final JButton zoomOutButton;
    private final JButton logOutButton;
    private final JButton createEventButton;

    // Constant declaring the zoom level
    private static final double ZOOM_LEVEL = 0.00001;

    // Map viewer to display the map
    private JXMapViewer mapViewer;

    /**
     * Constructor for the HomeView class to set up the view model,
     * swing components, and map components.
     * @param homeViewModel .
     * */
    public HomeView(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
        // Add view as listener
        this.homeViewModel.addPropertyChangeListener(this);

        // Create buttons with labels and event handles
        zoomInButton = createButton("Zoom in", evt -> handleZoomInAction());
        zoomOutButton = createButton("Zoom out", evt -> handleZoomOutAction());
        logOutButton = createButton("Log out", evt -> handleLogoutAction());
        createEventButton = createButton("Create event", evt -> handleEventAction());

        // Set panel layout
        setLayout(new BorderLayout());

        // Set up view components
        setupView();
    }

    /**
     * Function that sets parent panel in order for navigation
     * between views to be possible.
     * @param parentPanel .
     * */
    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    /**
     * Function that draws and sets up the map viewer, sidebar, and
     * zoom panel.
     * */
    private void setupView() {
        // Set up the map viewer
        mapViewer = setupMapViewer();

        // Create sidebar by initializing a new JPanel
        JPanel sidebar = new JPanel();

        // Set layout for sidebar
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Add required buttons
        sidebar.add(logOutButton);
        sidebar.add(createEventButton);
        // set the sidebar to be at the screen's left
        add(sidebar, BorderLayout.WEST);

        // Create zoom panel using the same logic as above
        JPanel zoomPanel = new JPanel();
        zoomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        zoomPanel.add(zoomInButton);
        zoomPanel.add(zoomOutButton);

        // Set the zoom panel to be at the bottom of the screen
        add(zoomPanel, BorderLayout.SOUTH);

        // Set the map to be at the center of the screen
        add(mapViewer, BorderLayout.CENTER);
    }

    /**
     * Create and set up the JXMapViewer and its
     * initial properties.
     * */
    private JXMapViewer setupMapViewer() {
        // Instantiate JXMapViewer
        JXMapViewer mapViewer = new JXMapViewer();

        // Map information using tile factory to use OpenStreetMap's built-in tiles
        TileFactoryInfo info = new TileFactoryInfo(
                1, 17, 17, 256, true, true,
                "https://tile.openstreetmap.org/", "x", "y", "z") {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                return this.baseURL + (17 - zoom) + "/" + x + "/" + y + ".png";
            }
        };

        // Make a tile factory for the map viewer to use
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Get campus coordinates for UofT and store it as a GeoPosition
        GeoPosition uoftCampus = new GeoPosition(43.6629, -79.3957);

        // Set the map's default zoom in level
        mapViewer.setZoom(1);

        // Set the map's default location to be the UofT campus
        mapViewer.setAddressLocation(uoftCampus);

        // Add a marker right on top of the UofT campus to test markers
        addMarkerToMap(mapViewer, uoftCampus);

        // Enable dragging functionality for the map
        setupDragFunctionality(mapViewer);

        return mapViewer;
    }

    /**
     * Add a marker to the map at a specific location
     * @param position, the given Geo position
     * @param mapViewer, the map viewer object.
     * */
    private void addMarkerToMap(JXMapViewer mapViewer, GeoPosition position) {
        // Create a set of waypoints
        Set<Waypoint> waypoints = new HashSet<>();
        waypoints.add(new DefaultWaypoint(position));

        // Create a waypoint painter to draw the waypoints
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);

        // Combine painters if needed
        mapViewer.setOverlayPainter(waypointPainter);
    }

    /**
     * Add dragging functionality to the map, so that when a user
     * drags the map the screen follows the cursor and centralizes
     * the map to show the dragged location.
     * @param mapViewer, the map viewer object.
     * */
    private void setupDragFunctionality(JXMapViewer mapViewer) {
        // Start tracking the cursor's location by setting it to nothing.
        final Point[] lastMousePosition = {null};

        // Record the cursor's position by tracking when the mouse is pressed
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMousePosition[0] = e.getPoint();
            }
        });
        // user dragging screen makes the map move:
        mapViewer.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // If the user moved their mouse, track the distance
                if (lastMousePosition[0] != null) {
                    Point currentMousePosition = e.getPoint();
                    // Record the change in X coordinate
                    int deltaX = currentMousePosition.x - lastMousePosition[0].x;

                    // Record the change in Y coordinate
                    int deltaY = currentMousePosition.y - lastMousePosition[0].y;
                    GeoPosition currentPosition = mapViewer.getAddressLocation();

                    // Adjust the location based off of the change in coordinates and zoom level
                    double longitudeDelta = -deltaX * ZOOM_LEVEL;
                    double latitudeDelta = deltaY * ZOOM_LEVEL;

                    // Update the location displayed on the user's screen
                    GeoPosition newPosition = new GeoPosition(
                            currentPosition.getLatitude() + latitudeDelta,
                            currentPosition.getLongitude() + longitudeDelta
                    );

                    // Update the map viewer's address location
                    mapViewer.setAddressLocation(newPosition);
                    lastMousePosition[0] = currentMousePosition;
                }
            }
        });
    }
    /**
     * Function to zoom in on the map by
     * decreasing mapViewer's zoom level
     * */
    private void handleZoomInAction() {
        int currentZoom = mapViewer.getZoom();
        if (currentZoom > 1) {
            mapViewer.setZoom(currentZoom - 1);
        }
    }

    /**
     * Function to zoom out on the map by
     * increasing mapViewer's zoom level
     * */
    private void handleZoomOutAction() {
        int currentZoom = mapViewer.getZoom();
        if (currentZoom < 17) {
            mapViewer.setZoom(currentZoom + 1);
        }
    }

    /**
     * Function to switch screens to the create
     * event screen when the create event button
     * is pressed
     * */
    private void handleEventAction() {
        navigateTo("createEvent");
    }

    /**
     * Function to switch screens to the signup
     * screen whenever the logout button is pressed
     * */
    private void handleLogoutAction() {
        navigateTo("register");
    }

    /**
     * Function to navigate from this screen to a different screen
     * using the name of the new screen.
     * @param viewName the name of the view you want to navigate to
     * */
    public void navigateTo(String viewName) {
        // Check if the parentPanel is valid for debugging purposes
        if (parentPanel != null && parentPanel.getLayout() instanceof CardLayout) {
            // Debug statement in console:
            System.out.println("Navigating to: " + viewName);

            CardLayout layout = (CardLayout) parentPanel.getLayout();
            layout.show(parentPanel, viewName);

            // Revalidate and redraw the parent panel after you navigate to it
            parentPanel.revalidate();
            parentPanel.repaint();
        } else {
            // Console error statement if the navigation doesn't work
            System.out.println("Navigation failed: parentPanel or layout is not set up correctly.");
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // don't need any property change, just need to override because of IntelliJ
    }

    /**
     * Function to create a button and set its desired
     * properties like size, colour, font, and interactions
     * @param text, text you want the button to display
     * @param actionListener, desired action listener
     **/
    private JButton createButton(String text, java.awt.event.ActionListener actionListener) {
        // Create a new button
        JButton button = new JButton(text);

        // Set desired dimenstions
        button.setPreferredSize(new Dimension(120, 40));
        button.setMaximumSize(new Dimension(120, 40));

        // Set desired colour scheme and appearance
        button.setBackground(Color.decode("#48BF67"));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));

        // Add an action listener for the button
        button.addActionListener(actionListener);

        // Add hover effect to button by making the background darker when clicked
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(Color.decode("#2E7A46"));
            }
            // Set the background back to its original colour after hovering
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.decode("#48BF67"));
            }
        });
        return button;
    }
    /**
     * Function to return the view name of homeview.
     * */
    public String getViewName() {
        return VIEW_NAME;
    }

}
