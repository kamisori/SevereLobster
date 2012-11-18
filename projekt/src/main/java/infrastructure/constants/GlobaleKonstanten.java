package infrastructure.constants;

import java.awt.Font;
import java.io.File;

/**
 * Globale Konstanten, die für Backend und Frontend gueltig sind
 * 
 * @author Lars Schlegelmilch
 */
public abstract class GlobaleKonstanten {

    /**
     * Globale Schriftart der Application
     */
    public static final Font FONT = new Font("Arial", java.awt.Font.PLAIN, 22);

    /**
     * Name des Spiels
     */
    public static final String SPIELNAME = "Sternenhimmel DELUXE";

    /**
     * Dateiendung .sav - Dateiendung fuer gespeicherte Spiele
     */
    public static final String SPIELSTAND_DATEITYP = "sav";

    /**
     * Dateiendung .puz - Dateiendung fuer gespeicherte erstelle Puzzles
     */
    public static final String PUZZLE_DATEITYP = "puz";

    /**
     * Standardverzeichnis fuer gespeicherte Docs
     */
    public static final File DEFAULT_DOC_SAVE_DIR = getDefaultDocsSaveDir();

    /**
     * Standardverzeichnis fuer gespeicherte Docs
     */
    public static final File DEFAULT_CONF_SAVE_DIR = getDefaultConfigsSaveDir();

    /**
     * Standardverzeichnis fuer gespeicherte Spiele
     */
    public static final File DEFAULT_SPIEL_SAVE_DIR = getDefaultSpielSaveDir();

    /**
     * Standardverzeichnis fuer gespeicherte erstellte Puzzles
     */
    public static final File DEFAULT_PUZZLE_SAVE_DIR = getDefaultPuzzleSaveDir();


    public static final File USER_PROPERTIES = new File(DEFAULT_CONF_SAVE_DIR, "user.properties");
    /**
     * Standardverzeichnis fuer gespeicherte freigegebene Puzzles
     */
    public static final File DEFAULT_FREIGEGEBENE_PUZZLE_SAVE_DIR = getDefaultFreigegebenePuzzleSaveDir();


    /**
     * Gibt das Standardverzeichnis fuer gespeicherte Spiele zurueck - wenn es
     * nicht existiert, wird es angelegt
     * 
     * @return Standardverzeichnis fuer gespeicherte Spiele
     */
    private static File getDefaultSpielSaveDir() {
        File spielverzeichnis = new File("./bin/save");
        boolean success = true;
        if (!spielverzeichnis.exists()) {
            success = spielverzeichnis.mkdir();
        }
        if (!success) {
            return null;
        } else {
            return spielverzeichnis;
        }
    }

    /**
     * Gibt das Standardverzeichnis fuer Docs zurueck -
     * wenn es nicht existiert, wird es angelegt
     *
     * @return Standardverzeichnis fuer Docs
     */
    private static File getDefaultDocsSaveDir() {
        File docverzeichnis = new File("./bin/doc");
        boolean success = true;
        if (!docverzeichnis.exists()) {
            success = docverzeichnis.mkdir();
        }
        if (!success) {
            return null;
        } else {
            return docverzeichnis;
        }
    }

    /**
     * Gibt das Standardverzeichnis fuer Cinfigs zurueck -
     * wenn es nicht existiert, wird es angelegt
     *
     * @return Standardverzeichnis fuer Configs
     */
    private static File getDefaultConfigsSaveDir() {
        File docverzeichnis = new File("./conf");
        boolean success = true;
        if (!docverzeichnis.exists()) {
            success = docverzeichnis.mkdir();
        }
        if (!success) {
            return null;
        } else {
            return docverzeichnis;
        }
    }

    /**
     * Gibt das Standardverzeichnis fuer gespeicherte erstellte Puzzle zurueck -
     * wenn es nicht existiert, wird es angelegt
     * 
     * @return Standardverzeichnis fuer gespeicherte erstelle Puzzle
     */
    private static File getDefaultPuzzleSaveDir() {
        File spielverzeichnis = new File("./bin/edit");
        boolean success = true;
        if (!spielverzeichnis.exists()) {
            success = spielverzeichnis.mkdir();
        }
        if (!success) {
            return null;
        } else {
            return spielverzeichnis;
        }
    }

    /**
     * Gibt das Standardverzeichnis fuer gespeicherte freigegebene Puzzle zurueck -
     * wenn es nicht existiert, wird es angelegt
     *
     * @return Standardverzeichnis fuer gespeicherte freigegebene Puzzle
     */
    private static File getDefaultFreigegebenePuzzleSaveDir() {
        File spielverzeichnis = new File("./bin/puzzles");
        boolean success = true;
        if (!spielverzeichnis.exists()) {
            success = spielverzeichnis.mkdir();
        }
        if (!success) {
            return null;
        } else {
            return spielverzeichnis;
        }
    }
}
