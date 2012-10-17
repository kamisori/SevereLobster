package infrastructure;

import com.google.common.base.Preconditions;

import javax.swing.ImageIcon;
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

    /**
     * Gibt die Instanz des ResourceManager zurueck
     *
     * @return ResourceManager-Instanz
     */
    public static ResourceManager get() {
        return instance;
    }

    /**
     * Aender die Spracheinstellung des ResourceManagers,
     * sodass auf Properties der jeweiligen Sprache
     * zurueckgegriffen wird
     *
     * @param locale Sprache/Land
     */
    public void setLanguage(Locale locale) {
        try {
            propertyfile.load(getProperties(locale));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gibt den Text aus der Propertiesdatei zurueck
     *
     * @param key Schluessel des Textes
     * @return Text zum Schluessel
     */
    public String getText(String key) {
        return propertyfile.getProperty(key);
    }


    /**
     * Gibt die Properties-Datei(Standard: Deutsch) im Package
     * src/main/resources/infrastructure/constants zurueck
     *
     * @return InputStream der Properties-Datei
     */
    private InputStream getProperties() {
        return getClass().getResourceAsStream("text.properties");
    }

    /**
     * Gibt die Properties-Datei zu einer bestimmten Sprache
     * im Package src/main/resources/infrastructure/constants
     * zurueck - (Standard: Deutsch)
     *
     * @param locale Sprache/Land
     * @return Propertiesdatei als InputStream
     */
    private InputStream getProperties(Locale locale) {
        Preconditions.checkNotNull(locale);

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
     * @param graphicName Dateiname
     * @return URL der Grafik
     */
    public URL getGraphicURL(String graphicName) {
        return this.getClass().getResource("graphics/" + graphicName);
    }

    /**
     * Gibt die URL eines Icons im Package
     * src/main/resources/infrastructure/graphics/icons
     * zurueck
     *
     * @param iconName Dateiname
     * @return URL des Icons
     */
    public URL getIconURL(String iconName) {
        return getClass().getResource("grahpics/icons/" + iconName);
    }

    /**
     * Gibt das ImageIcon anhand des iconNames aus dem Package
     * src/main/resources/infrastructure/graphics/icons
     * zurueck
     *
     * @param iconName Dateiname
     * @return Icon als ImageIcon
     */
    public ImageIcon getImageIcon(String iconName) {
        return new ImageIcon(getClass().getResource("graphics/icons/" + iconName));
    }

    /**
     * Gibt die URL eines Icons im Package
     * src/main/resources/infrastructure/graphics/icons
     * zurueck
     *
     * @param iconName Dateiname
     * @return URL des Icons
     */
    @Deprecated
    public URL getIcon(String iconName) {
        return getClass().getResource("grahpics/icons/" + iconName);
    }

    /**
     * Gibt die URL einer Grafik im Package
     * src/main/resources/infrastructure/graphics zurueck
     *
     * @param graphicName Dateiname
     * @return URL der Grafik
     */
    @Deprecated
    public URL getGraphic(String graphicName) {
        return this.getClass().getResource("graphics/" + graphicName);
    }
}
