package infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;

/**
 * Helperklasse zur Resourcenverwaltung
 *
 * @author Lars Schlegelmilch
 */
public class ResourceManager {

    private static final ResourceManager instance = new ResourceManager();
    private Properties propertyfile;

    private ResourceManager() {
        propertyfile = new Properties();
        try {
            propertyfile.load(getProperties());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ResourceManager get() {
        return instance;
    }

    public void setLanguage(Locale locale) {
        try {
            propertyfile.load(getProperties(locale));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getText(String key) {
        return propertyfile.getProperty(key);
    }


    /**
     * Gibt die Properties-Datei im Package
     * src/main/resources/infrastructure/constants zurueck
     *
     * @return InputStream der Properties-Datei
     */
    private InputStream getProperties() {
        return getClass().getResourceAsStream("text.properties");
    }

    private InputStream getProperties(Locale locale) {
        if (locale.getLanguage().equals(Locale.GERMAN.getLanguage())) {
            return getClass().getResourceAsStream("text.properties");
        }
        if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
            return getClass().getResourceAsStream("text_en.properties");
        }
        // Default Deutsch
        return getClass().getResourceAsStream("text.properties");
    }

    /**
     * Gibt die URL einer Grafik im Package
     * src/main/resources/infrastructure/graphics zurueck
     *
     * @param graphicName
     *            Dateiname
     * @return URL der Grafik
     */
    public URL getGraphic(String graphicName) {
        return this.getClass().getResource("graphics/" + graphicName);
    }

    /**
     * Gibt die URL eines Icons im Package
     * src/main/resources/infrastructure/graphics/icons
     * zurueck
     *
     * @param iconName
     *            Dateiname
     * @return URL des Icons
     */
    public URL getIcon(String iconName) {
        return getClass().getResource("grahpics/icons/" + iconName);
    }
}
