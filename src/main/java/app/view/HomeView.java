package app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

import app.interface_adapter.display_event.DisplayEventController;
import app.interface_adapter.home.HomeController;
import app.interface_adapter.home.HomeViewModel;
import app.interface_adapter.view_event.ViewEventController;

/**
 * The HomeView class represents the main view of the application. It provides a graphical interface
 * for users to interact with events, maps, and navigation functionality.
 * This class manages the layout, components, and interactions within the home view.
 */
public class HomeView extends JPanel implements PropertyChangeListener {

    private static final int MIN_ZOOM_LEVEL = 1;
    private static final int MAX_ZOOM_LEVEL = 17;
    private static final double EARTH_RADIUS = 6371000.0;
    private static final double ZOOM_THRESHOLD_LOW = 0.000025;
    private static final double ZOOM_THRESHOLD_MEDIUM = 0.00025;
    private static final double ZOOM_THRESHOLD_HIGH = 0.001;
    private static final double ZOOM_MULTIPLIER = 0.005;
    private static final double GEO_LATITUTE = 43.6629;
    private static final double GEO_LONGITUDE = -79.3957;
    private static final int LOW_ZOOM_THRESHOLD = 5;
    private static final int MEDIUM_ZOOM_THRESHOLD = 7;
    private static final int HIGH_ZOOM_THRESHOLD = 9;
    private static final int TILE_SIZE = 256;
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 30;
    private static final int BUTTON_FONT_SIZE = 16;
    private static final int WAYPOINT_RADIUS = 6;
    private static final Map<String, Color> EVENT_TAG_COLORS = Map.of(
            "Gaming", Color.GREEN,
            "Music", Color.BLACK,
            "Sports", Color.CYAN,
            "Art and Culture", Color.ORANGE,
            "Education", Color.PINK,
            "Travel", Color.GRAY,
            "Festival", Color.YELLOW
    );

    // Constant for the view name for navigation purposes
    private static final String VIEW_NAME = "Home";

    // View model required for managing logic for view
    private final HomeViewModel homeViewModel;
    private final DisplayEventController displayEventController;
    private ViewEventController viewEventController;

    private JPanel parentPanel;

    private HomeController homeController;

    // List of events
    private ArrayList<ArrayList<Object>> events;

    // Buttons for zooming in, out, logging out, and creating an event
    private final JButton zoomInButton;
    private final JButton zoomOutButton;
    private final JButton logOutButton;
    private final JButton filterButton;
    private final JButton createEventButton;
    private final JButton viewRSVPButton;
    private final JButton viewCreatedButton;
    private final JButton modifyEventButton;

    // Double declaring the zoom level, which is adjusted later in the code
    private double ZOOM_LEVEL;

    // Map viewer to display the map
    private JXMapViewer mapViewer;

    /**
     * Constructor for the HomeView class.
     * Initializes the view components, map viewer, and event listeners.
     *
     * @param homeViewModel the view model for the home view.
     * @param displayEventController the controller for handling event display logic.
     */
    public HomeView(HomeViewModel homeViewModel, DisplayEventController displayEventController) {
        this.homeViewModel = homeViewModel;
        this.displayEventController = displayEventController;
        this.events = displayEventController.execute();
        // Add view as listener
        this.homeViewModel.addPropertyChangeListener(this);

        // Create buttons with labels and event handles
        zoomInButton = createButton("Zoom in", evt -> handleZoomInAction());
        zoomOutButton = createButton("Zoom out", evt -> handleZoomOutAction());
        logOutButton = createButton("Log out", evt -> handleLogoutAction());
        filterButton = createButton("Filter", evt -> handleFilterAction());
        createEventButton = createButton("Create event", evt -> handleEventAction());
        viewRSVPButton = createButton("View RSVP", evt -> handleViewRSVPAction());
        viewCreatedButton = createButton("View Created", evt -> handleViewCreatedEvents());
        modifyEventButton = createButton("Modify Event", evt -> handleViewModifyEvent());

        // Set panel layout
        setLayout(new BorderLayout());

        // Set up view components
        setupView();
    }

    /**
     * Function that sets parent panel in order for navigation
     * between views to be possible.
     *
     * @param parentPanel .
     */
    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    /**
     * Function that draws and sets up the map viewer, sidebar, and
     * zoom panel.
     */
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
        sidebar.add(filterButton);
        sidebar.add(viewRSVPButton);
        sidebar.add(viewCreatedButton);
        sidebar.add(modifyEventButton);

        // Set the sidebar to be at the screen's left
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
     * Because there are no features for clicking waypoints, this function
     * is intended to take the distance between two geopositions on our map, using the
     * Haversine formula, which is a formula commonly used for finding the distance between
     * two points on a sphere (cool new knowledge gained!)
     *
     * @param pos1 the first location on the map.
     * @param pos2 the second location on the map.
     */
    private double calculateDistance(GeoPosition pos1, GeoPosition pos2) {

        // Do some math and convert the latitude and longitude from degrees to radians using built-in function
        double lat1 = Math.toRadians(pos1.getLatitude());
        double lon1 = Math.toRadians(pos1.getLongitude());
        double lat2 = Math.toRadians(pos2.getLatitude());
        double lon2 = Math.toRadians(pos2.getLongitude());

        // Calculate distance between points
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        // Use super secret and special Haversine formula (too long to type out)
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        // Central angle calculation for Haversine's formula
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Return the distance between the points in meters
        return EARTH_RADIUS * c;
    }

    /**
     * Create and set up the JXMapViewer and its
     * initial properties. Includes drawing all the waypoints
     * to the map.
     */
    private JXMapViewer setupMapViewer() {
        JXMapViewer mapViewer = MapViewerSingleton.getInstance();

        TileFactoryInfo info = new TileFactoryInfo(
                MIN_ZOOM_LEVEL, MAX_ZOOM_LEVEL, MAX_ZOOM_LEVEL, TILE_SIZE, true, true,
                "https://tile.openstreetmap.org/", "x", "y", "z") {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                return this.baseURL + (MAX_ZOOM_LEVEL - zoom) + "/" + x + "/" + y + ".png";
            }
        };

        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        GeoPosition uoftCampus = new GeoPosition(GEO_LATITUTE, GEO_LONGITUDE);
        mapViewer.setZoom(1);
        mapViewer.setAddressLocation(uoftCampus);

        Set<DefaultWaypoint> waypoints = new HashSet<>();
        Map<DefaultWaypoint, Color> waypointColors = new HashMap<>();
        Map<DefaultWaypoint, String> waypointTitles = new HashMap<>();

        for (ArrayList<Object> event : events) {
            Color colour = Color.CYAN;
            float latitude = (float) event.get(2);
            float longitude = (float) event.get(3);
            String title = (String) event.get(1);
            ArrayList<String> tags = (ArrayList<String>) event.get(4);

            for (String tag : tags) {
                switch (tag) {
                    case "Gaming":
                        colour = Color.GREEN;
                        break;
                    case "Music":
                        colour = Color.BLACK;
                        break;
                    case "Sports":
                        colour = Color.CYAN;
                        break;
                    case "Art and Culture":
                        colour = Color.ORANGE;
                        break;
                    case "Education":
                        colour = Color.PINK;
                        break;
                    case "Travel":
                        colour = Color.GRAY;
                        break;
                    case "Festival":
                        colour = Color.YELLOW;
                        break;
                }
            }
            DefaultWaypoint waypoint = new DefaultWaypoint(new GeoPosition(latitude, longitude));
            waypoints.add(waypoint);
            waypointColors.put(waypoint, colour);
            waypointTitles.put(waypoint, title);
        }

        WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setRenderer((g, map, waypoint) -> {
            Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());
            Color color = waypointColors.getOrDefault(waypoint, Color.BLACK);
            String title = waypointTitles.getOrDefault(waypoint, "");

            g.setColor(color);
            g.fillOval((int) point.getX() - WAYPOINT_RADIUS, (int) point.getY() - WAYPOINT_RADIUS,
                    2 * WAYPOINT_RADIUS, 2 * WAYPOINT_RADIUS);

            g.setColor(Color.BLACK);
            g.drawString(title, (int) point.getX() + WAYPOINT_RADIUS + 2, (int) point.getY());
        });

        waypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(waypointPainter);

        mapViewer.addMouseListener(new MouseAdapter() {
            /**
             * Mouse listener to get extract geoposition, which is then compared against
             * all events and the first geoposition within 15 meters of the user's mouse
             * click will pop up.
             * */
            public void mouseClicked(MouseEvent e) {
                GeoPosition clickedGeoPosition = mapViewer.convertPointToGeoPosition(e.getPoint());

                // Check if the click is near a waypoint by looping through all waypoints...
                for (DefaultWaypoint waypoint : waypoints) {
                    GeoPosition wpPosition = waypoint.getPosition();
                    double distance = calculateDistance(clickedGeoPosition, wpPosition);

                    if (distance < (20 * mapViewer.getZoom())) {
                        System.out.println(mapViewer.getZoom());
                        String title = waypointTitles.get(waypoint);
                        viewEvent(title);
                    }
                }
            }
        });

        setupDragFunctionality(mapViewer);

        return mapViewer;
    }

    /**
     * Open the event's info page after clicking on it.
     * @param title the title of the event
     */
    private void viewEvent(String title) {
        viewEventController.execute(title);
    }

    /**
     * Add dragging functionality to the map, so that when a user
     * drags the map the screen follows the cursor and centralizes
     * the map to show the dragged location.
     * @param mapViewer the map viewer object.
     */
    private void setupDragFunctionality(JXMapViewer mapViewer) {
        // Start tracking the cursor's location by setting it to nothing
        final Point[] lastMousePosition = {null};

        // Record the cursor's position by tracking when the mouse is pressed
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMousePosition[0] = e.getPoint();
            }
        });
        // User dragging screen makes the map move:
        mapViewer.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // First, adjust tiles scrolled based off of user's zoom level.
                System.out.println(mapViewer.getZoom());
                if (mapViewer.getZoom() <= LOW_ZOOM_THRESHOLD) {
                    ZOOM_LEVEL = ZOOM_THRESHOLD_LOW;
                }
                else if (mapViewer.getZoom() <= MEDIUM_ZOOM_THRESHOLD) {
                    ZOOM_LEVEL = ZOOM_THRESHOLD_MEDIUM;
                }
                else if (mapViewer.getZoom() <= HIGH_ZOOM_THRESHOLD) {
                    ZOOM_LEVEL = ZOOM_THRESHOLD_HIGH;
                }
                else {
                    ZOOM_LEVEL = ZOOM_MULTIPLIER * mapViewer.getZoom();
                }
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
     * decreasing mapViewer's zoom level.
     */
    private void handleZoomInAction() {
        int currentZoom = mapViewer.getZoom();
        if (currentZoom > 1) {
            mapViewer.setZoom(currentZoom - 1);
        }
    }

    /**
     * Function to zoom out on the map by
     * increasing mapViewer's zoom level.
     */
    private void handleZoomOutAction() {
        int currentZoom = mapViewer.getZoom();
        if (currentZoom < MAX_ZOOM_LEVEL) {
            mapViewer.setZoom(currentZoom + 1);
        }
    }

    /**
     * Function to switch screens to the create
     * event screen when the create event button
     * is pressed.
     */
    private void handleEventAction() {
        homeController.switchToCreateEventView();
    }

    /**
     * Function to switch screens to the signup
     * screen whenever the logout button is pressed.
     */
    private void handleLogoutAction() {
        homeController.switchToLoginView();
    }

    private void handleFilterAction() {
        homeController.switchToFilterEventView();
    }

    private void handleViewRSVPAction() {
        homeController.switchToViewRSVPView();
    }

    private void handleViewCreatedEvents() {
        homeController.switchToViewCreatedEventsView();
    }

    private void handleViewModifyEvent() {
        homeController.switchToModifyEventView();
    }

    /**
     * Function to navigate from this screen to a different screen
     * using the name of the new screen.
     */

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // don't need any property change, just need to override because of IntelliJ
    }

    /**
     * Function to create a button and set its desired
     * properties like size, colour, font, and interactions
     *
     * @param text           text you want the button to display.
     * @param actionListener desired action listener.
     **/
    private JButton createButton(String text, java.awt.event.ActionListener actionListener) {
        // Create a new button
        JButton button = new JButton(text);
        final int createButtonWidth = BUTTON_WIDTH;
        final int createButtonHeight = BUTTON_HEIGHT;
        final int createButtonFontSize = BUTTON_FONT_SIZE;

        // Set desired dimenstions
        button.setPreferredSize(new Dimension(createButtonWidth, createButtonHeight));
        button.setMaximumSize(new Dimension(createButtonWidth, createButtonHeight));

        // Set desired colour scheme and appearance
        button.setBackground(Color.decode("#48BF67"));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, createButtonFontSize));

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
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    public void setHomeController(HomeController controller) {
        this.homeController = controller;
    }

    public void setViewEventController(ViewEventController controller) {
        this.viewEventController = controller;
    }
}
