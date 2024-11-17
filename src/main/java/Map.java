import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;

public class Map {
    public static void main(String[] args) {
        final double ZOOM_LEVEL = 0.00001;
        JFrame frame = new JFrame("JXMapViewer example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JXMapViewer mapViewer = new JXMapViewer();

        // set up title factory (map provider, built in)
        TileFactoryInfo info = new TileFactoryInfo(
                1,    // min zoom level
                17,   // max zoom level
                17,   // number of zoom levels
                256,  // tile size
                true, // X/Y axis flipping
                true, // use tile cache
                "https://tile.openstreetmap.org/", // base URL
                "x", "y", "z"                      // title params
        ) {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                return this.baseURL + (17 - zoom) + "/" + x + "/" + y + ".png";
            }
        };

        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // set map location
        GeoPosition uoftCampus = new GeoPosition(43.6629, -79.3957); // UofT campus coords
        mapViewer.setZoom(1);
        mapViewer.setAddressLocation(uoftCampus);

        // dragging functionality
        final Point[] lastMousePosition = {null};

        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMousePosition[0] = e.getPoint();
            }
        });

        mapViewer.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastMousePosition[0] != null) {
                    Point currentMousePosition = e.getPoint();

                    int deltaX = currentMousePosition.x - lastMousePosition[0].x;
                    int deltaY = currentMousePosition.y - lastMousePosition[0].y;

                    GeoPosition currentPosition = mapViewer.getAddressLocation();
                    double longitudeDelta = -deltaX * ZOOM_LEVEL;
                    double latitudeDelta = deltaY * ZOOM_LEVEL;

                    GeoPosition newPosition = new GeoPosition(
                            currentPosition.getLatitude() + latitudeDelta,
                            currentPosition.getLongitude() + longitudeDelta
                    );

                    mapViewer.setAddressLocation(newPosition);

                    lastMousePosition[0] = currentMousePosition;
                }
            }
        });

        frame.add(mapViewer, BorderLayout.CENTER);

        JPanel zoomPanel = new JPanel();
        zoomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton zoomInButton = new JButton("Zoom In");
        zoomInButton.addActionListener(e -> {
            int currentZoom = mapViewer.getZoom();
            if (currentZoom > 1) { // set zoom cap
                mapViewer.setZoom(currentZoom - 1);
            }
        });
        zoomPanel.add(zoomInButton);

        JButton zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.addActionListener(e -> {
            int currentZoom = mapViewer.getZoom();
            if (currentZoom < 15) { // set zoom cap
                mapViewer.setZoom(currentZoom + 1);
            }
        });
        zoomPanel.add(zoomOutButton);

        frame.add(zoomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
