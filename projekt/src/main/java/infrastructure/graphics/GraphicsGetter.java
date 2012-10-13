package infrastructure.graphics;

import java.net.URL;

/**
 * Helperklasse zur Resourcenverwaltung
 * 
 * @author Lars Schlegelmilch
 */
public class GraphicsGetter {

    private static final GraphicsGetter instance = new GraphicsGetter();

    private GraphicsGetter() {
    }

    /**
     * Gibt die URL einer Grafik im Package src/main/resources/graphics zurueck
     * 
     * @param graphicName
     *            Dateiname
     * @return URL der Grafik
     */
    public static URL getGraphic(String graphicName) {
        return instance.getClass().getResource(graphicName);
    }

    /**
     * Gibt die URL eines Icons im Package src/main/resources/graphics/icons
     * zurueck
     * 
     * @param iconName
     *            Dateiname
     * @return URL des Icons
     */
    public static URL getIcon(String iconName) {
        return instance.getClass().getResource("icons/" + iconName);
    }
}
