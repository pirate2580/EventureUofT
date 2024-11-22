package app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import app.interface_adapter.home.HomeViewModel;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;

public class HomeView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "Home";
    private final HomeViewModel homeViewModel;
    private JPanel parentPanel;
    private final JButton zoomInButton;
    private final JButton zoomOutButton;
    private final JButton logOutButton;
    private final JButton createEventButton;
    private static final double ZOOM_LEVEL = 0.00001;
    private JXMapViewer mapViewer;

    public HomeView(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
        this.homeViewModel.addPropertyChangeListener(this);

        zoomInButton = createButton("Zoom in", evt -> handleZoomInAction());
        zoomOutButton = createButton("Zoom out", evt -> handleZoomOutAction());
        logOutButton = createButton("Log out", evt -> handleLogoutAction());
        createEventButton = createButton("Create event", evt -> handleEventAction());

        setLayout(new BorderLayout());
        setupView();
    }

    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    private void setupView() {
        // setup map viewer
        mapViewer = setupMapViewer();

        // make sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.add(logOutButton);
        sidebar.add(createEventButton);
        add(sidebar, BorderLayout.WEST);

        // zoom panel
        JPanel zoomPanel = new JPanel();
        zoomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        zoomPanel.add(zoomInButton);
        zoomPanel.add(zoomOutButton);
        add(zoomPanel, BorderLayout.SOUTH);

        add(mapViewer, BorderLayout.CENTER);
    }

    private JXMapViewer setupMapViewer() {
        JXMapViewer mapViewer = new JXMapViewer();
        // map information
        TileFactoryInfo info = new TileFactoryInfo(
                1, 17, 17, 256, true, true,
                "https://tile.openstreetmap.org/", "x", "y", "z") {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                return this.baseURL + (17 - zoom) + "/" + x + "/" + y + ".png";
            }
        };
        // JXmapviewer stuff
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);
        // get campus coords
        GeoPosition uoftCampus = new GeoPosition(43.6629, -79.3957);
        // default zoom in
        mapViewer.setZoom(1);
        mapViewer.setAddressLocation(uoftCampus);

        setupDragFunctionality(mapViewer);
        return mapViewer;
    }

    private void setupDragFunctionality(JXMapViewer mapViewer) {
        final Point[] lastMousePosition = {null};

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
                if (lastMousePosition[0] != null) {
                    Point currentMousePosition = e.getPoint();
                    int deltaX = currentMousePosition.x - lastMousePosition[0].x;
                    int deltaY = currentMousePosition.y - lastMousePosition[0].y;

                    GeoPosition currentPosition = mapViewer.getAddressLocation();
                    // adjust based off dragging
                    double longitudeDelta = -deltaX * ZOOM_LEVEL;
                    double latitudeDelta = deltaY * ZOOM_LEVEL;
                    // update location
                    GeoPosition newPosition = new GeoPosition(
                            currentPosition.getLatitude() + latitudeDelta,
                            currentPosition.getLongitude() + longitudeDelta
                    );

                    mapViewer.setAddressLocation(newPosition);
                    lastMousePosition[0] = currentMousePosition;
                }
            }
        });
    }
    // adjust JXMapViewer's zoom levels
    private void handleZoomInAction() {
        int currentZoom = mapViewer.getZoom();
        if (currentZoom > 1) {
            mapViewer.setZoom(currentZoom - 1);
        }
    }

    private void handleZoomOutAction() {
        int currentZoom = mapViewer.getZoom();
        if (currentZoom < 17) {
            mapViewer.setZoom(currentZoom + 1);
        }
    }
    // switch screens once buttons are pressed
    private void handleEventAction() {
        navigateTo("createEvent");
    }
    // go back to sign up / log in page when logged out
    private void handleLogoutAction() {
        navigateTo("register");
    }

    public void navigateTo(String viewName) {
        if (parentPanel != null && parentPanel.getLayout() instanceof CardLayout) {
            // debugging:
            System.out.println("Navigating to: " + viewName);

            CardLayout layout = (CardLayout) parentPanel.getLayout();
            layout.show(parentPanel, viewName);

            parentPanel.revalidate();
            parentPanel.repaint();
        } else {
            // debugging
            System.out.println("Navigation failed: parentPanel or layout is not set up correctly.");
        }
    }



    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // don't need any property change, just need to override because of IntelliJ
    }

    private JButton createButton(String text, java.awt.event.ActionListener actionListener) {
        // logic for creating a button
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40));
        button.setMaximumSize(new Dimension(120, 40));
        button.setBackground(Color.decode("#48BF67"));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.addActionListener(actionListener);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(Color.decode("#2E7A46"));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.decode("#48BF67"));
            }
        });
        return button;
    }

    public String getViewName() {
        return VIEW_NAME;
    }

}
