package app.view;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 * Singleton class for creating and managing a shared instance of the JXMapViewer.
 */
public class MapViewerSingleton {
    /** Minimum zoom level for the map viewer. */
    private static final int MIN_ZOOM_LEVEL = 1;

    /** Maximum zoom level for the map viewer. */
    private static final int MAX_ZOOM_LEVEL = 17;

    /** Tile size for the map viewer. */
    private static final int TILE_SIZE = 256;

    /** Default map zoom level. */
    private static final int DEFAULT_ZOOM_LEVEL = 1;

    /** URL template for OpenStreetMap tiles. */
    private static final String TILE_BASE_URL = "https://tile.openstreetmap.org/";

    /** Latitude of the University of Toronto campus. */
    private static final double UOFT_LATITUDE = 43.6629;

    /** Longitude of the University of Toronto campus. */
    private static final double UOFT_LONGITUDE = -79.3957;

    private static JXMapViewer mapViewer;

    /**
     * Private constructor to prevent instantiation.
     */
    private MapViewerSingleton() {
    }

    /**
     * Provides access to the singleton instance of the JXMapViewer.
     * If the instance does not exist, it is created and configured.
     *
     * @return the singleton instance of the {@link JXMapViewer}.
     */
    public static JXMapViewer getInstance() {
        if (mapViewer == null) {
            mapViewer = new JXMapViewer();

            // Configure mapViewer as needed
            TileFactoryInfo info = new TileFactoryInfo(
                    MIN_ZOOM_LEVEL, MAX_ZOOM_LEVEL, MAX_ZOOM_LEVEL, TILE_SIZE, true, true,
                    TILE_BASE_URL, "x", "y", "z") {
                @Override
                public String getTileUrl(int x, int y, int zoom) {
                    return this.baseURL + (MAX_ZOOM_LEVEL - zoom) + "/" + x + "/" + y + ".png";
                }
            };

            DefaultTileFactory tileFactory = new DefaultTileFactory(info);
            mapViewer.setTileFactory(tileFactory);

            GeoPosition uoftCampus = new GeoPosition(UOFT_LATITUDE, UOFT_LONGITUDE);
            mapViewer.setZoom(DEFAULT_ZOOM_LEVEL);
            mapViewer.setAddressLocation(uoftCampus);
        }

        return mapViewer;
    }
}
