package app.view;

// Import required libraries and imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

// Import applications specific classes for map functionality and JXMapViewer

import app.interface_adapter.display_event.DisplayEventController;
import app.interface_adapter.home.HomeController;
import app.interface_adapter.home.HomeViewModel;
import app.interface_adapter.view_event.ViewEventState;
import app.interface_adapter.view_rsvp.ViewRSVPController;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.*;
import java.awt.geom.Point2D;
import app.interface_adapter.view_event.ViewEventController;
import app.interface_adapter.view_rsvp.ViewRSVPController;

public class MapViewerSingleton {
    private static JXMapViewer mapViewer; // Static field for single instance

    // Private constructor to prevent instantiation
    private MapViewerSingleton() {}

    // Public static method to provide access to the instance
    public static JXMapViewer getInstance() {
        if (mapViewer == null) {
            mapViewer = new JXMapViewer();

            // Configure mapViewer as needed
            TileFactoryInfo info = new TileFactoryInfo(
                    1, 17, 17, 256, true, true,
                    "https://tile.openstreetmap.org/", "x", "y", "z") {
                @Override
                public String getTileUrl(int x, int y, int zoom) {
                    return this.baseURL + (17 - zoom) + "/" + x + "/" + y + ".png";
                }
            };

            DefaultTileFactory tileFactory = new DefaultTileFactory(info);
            mapViewer.setTileFactory(tileFactory);

            GeoPosition uoftCampus = new GeoPosition(43.6629, -79.3957);
            mapViewer.setZoom(1);
            mapViewer.setAddressLocation(uoftCampus);
        }

        return mapViewer;
    }
}
