package infrastructure;

import infrastructure.constants.GlobaleKonstanten;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
    private Properties userpropertyFile;
    private Locale language;

    private ResourceManager() {
        propertyfile = new Properties();
        try {
            propertyfile.load(getProperties());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userpropertyFile = new Properties();
        try {
            userpropertyFile.load(getUserProperties());
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
     * Aender die Spracheinstellung des ResourceManagers, sodass auf Properties
     * der jeweiligen Sprache zurueckgegriffen wird
     *
     * @param locale Sprache/Land
     */
    public void setLanguage(Locale locale) {
        if (locale == null) {
            String localeString = (String) userpropertyFile.get("user.language");
            if (localeString.equals(Locale.GERMAN.toString())) {
                locale = Locale.GERMAN;
            } else if (localeString.equals(Locale.ENGLISH.toString())) {
                locale = Locale.ENGLISH;
            } else {
                locale = Locale.GERMAN;
            }
        }
        language = locale;
        try {
            propertyfile.load(getProperties(language));
        } catch (IOException e) {
            e.printStackTrace();
        }
        userpropertyFile.put("user.language", locale.toString());
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(GlobaleKonstanten.USER_PROPERTIES);
            userpropertyFile.store(stream, "User.properties");
        } catch (IOException e) {
            System.out.println("Neue Benutzereinstellungen konnten nicht gesichert werden.");
        }

    }

    /**
     * Getter fuer aktuelle Spracheinstellung
     */
    public Locale getLanguage() {
        return language;
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

    public void setAvatar(URL value) {
        userpropertyFile.put("user.avatar", new File(value.getFile()).getName());
        try {
            OutputStream stream = new FileOutputStream(GlobaleKonstanten.USER_PROPERTIES);
            userpropertyFile.store(stream, "User.properties");
        } catch (IOException e) {
            System.out.println("Neue Benutzereinstellungen konnten nicht gesichert werden.");
        }
    }

    public URL getUserAvatar() {
        String avatarName = (String) userpropertyFile.get("user.avatar");
        if (avatarName == null || getAvatarURL(avatarName) == null) {
            return getAvatarURL("spielinfo_w1.jpg");
        }
        return getAvatarURL(avatarName);
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
     * Gibt die Properties-Datei zu einer bestimmten Sprache im Package
     * src/main/resources/infrastructure/constants zurueck - (Standard: Deutsch)
     *
     * @param locale Sprache/Land
     * @return Propertiesdatei als InputStream
     */
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
     * Gibt die Userspezifischen Properties zurueck
     *
     * @return Userspezifische Properties
     */
    private InputStream getUserProperties() {
        InputStream propertiesFile = null;
        try {
            propertiesFile = new FileInputStream
                    (new File(GlobaleKonstanten.DEFAULT_CONF_SAVE_DIR, "user.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return propertiesFile;
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
     * Gibt die URL einer Avatar-Grafik im Package
     * src/main/resources/infrastructure/graphics/avatar zurueck
     *
     * @param avatarName Dateiname
     * @return URL des Avatars
     */
    public URL getAvatarURL(String avatarName) {
        return this.getClass().getResource("graphics/avatar/" + avatarName);
    }

    /**
     * Gibt das ImageIcon eines Avatar-Grafik-Previews im Package
     * src/main/resources/infrastructure/graphics/avatar zurueck
     *
     * @param avatarName Dateiname
     * @return ImageIcon des AvatarPreviews
     */
    public ImageIcon getAvatarPreviewImageIcon(String avatarName) {
        avatarName = avatarName.replace(".", "_preview.");
        return new ImageIcon(this.getClass().getResource("graphics/avatar/" + avatarName));
    }

    /**
     * Gibt die URL eines Icons im Package
     * src/main/resources/infrastructure/graphics/icons zurueck
     *
     * @param iconName Dateiname
     * @return URL des Icons
     */
    public URL getIconURL(String iconName) {
        return getClass().getResource("graphics/icons/" + iconName);
    }

    /**
     * Gibt das ImageIcon anhand des iconNames aus dem Package
     * src/main/resources/infrastructure/graphics/icons zurueck
     *
     * @param iconName Dateiname
     * @return Icon als ImageIcon
     */
    public ImageIcon getImageIcon(String iconName) {
        return new ImageIcon(getClass().getResource(
                "graphics/icons/" + iconName));
    }

    /**
     * Gibt das BufferedImage anhand des iconnamens aus dem Package
     * src/main/resources/infrastructure/graphics/icons zurueck
     *
     * @param imageName Dateiname
     * @return Icon Bild als BufferedImage
     * @throws IOException Wenn die Datei nicht gefunden wird.
     */
    public BufferedImage getIconAsBufferedImage(String imageName)
            throws IOException {
        return ImageIO.read(getClass().getResource(
                "graphics/icons/" + imageName));
    }

    /**
     * Gibt die URL eines Icons im Package
     * src/main/resources/infrastructure/graphics/icons zurueck
     *
     * @param iconName Dateiname
     * @return URL des Icons
     */
    @Deprecated
    public URL getIcon(String iconName) {
        return getClass().getResource("graphics/icons/" + iconName);
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
