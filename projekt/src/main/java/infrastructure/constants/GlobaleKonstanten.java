package infrastructure.constants;

import java.io.File;

/**
 * Globale Konstanten, die für Backend und Frontend gueltig sind
 *
 * @author Lars Schlegelmilch
 */
public abstract class GlobaleKonstanten {

    /**
     * Name des Spiels
     */
    public static final String SPIELNAME = "Sternenhimmel DELUXE";

    /**
     * Dateiendung .sav - Dateiendung fuer gespeicherte Spiele
     */
    public static final String SPIELSTAND_DATEITYP = ".sav";

    /**
     * Dateiendung .puz - Dateiendung fuer gespeicherte erstelle Puzzles
     */
    public static final String PUZZLE_ERSTELLEN_DATEITYP = ".puz";

    /**
     * Benutzerverzeichnis
     */
    public static final File USERHOME = new File(System.getProperty("user.home"));

    /**
     * Spielverzeichnis, in dem Dateien ausgelagert werden
     */
    public static final File SPIELVERZEICHNIS = getSpielverzeichnis();

    /**
     * Standardverzeichnis fuer gespeicherte Spiele
     */
    public static final File DEFAULT_SPIEL_SAVE_DIR = getDefaultSpielSaveDir();

    /**
     * Standardverzeichnis fuer gespeicherte erstellte Puzzles
     */
    public static final File DEFAULT_PUZZLE_SAVE_DIR = getDefaultPuzzleSaveDir();

    /**
     * Gibt das Spielverzeichnis zurueck, in dem Dateien des Spiels abgelegt
     * werden
     *
     * @return Spielverzeichnis
     */
    private static File getSpielverzeichnis() {
        File spielverzeichnis = new File(USERHOME, SPIELNAME);
        boolean success = true;
        if (!spielverzeichnis.exists()) {
            success = spielverzeichnis.mkdir();
        }
        if (!success) {
            return USERHOME;
        } else {
            return spielverzeichnis;
        }
    }

    /**
     * Gibt das Standardverzeichnis fuer gespeicherte Spiele zurueck
     * - wenn es nicht existiert, wird es angelegt
     *
     * @return Standardverzeichnis fuer gespeicherte Spiele
     */
    private static File getDefaultSpielSaveDir() {
        File defaultSpielSaveDir = new File(SPIELVERZEICHNIS, "save");
        boolean success = true;
        if (!defaultSpielSaveDir.exists()) {
            success = defaultSpielSaveDir.mkdir();
        }
        if (!success) {
            return SPIELVERZEICHNIS;
        } else {
            return defaultSpielSaveDir;
        }
    }

    /**
     * Gibt das Standardverzeichnis fuer gespeicherte erstellte Puzzle
     * zurueck - wenn es nicht existiert, wird es angelegt
     *
     * @return Standardverzeichnis fuer gespeicherte erstelle Puzzle
     */
    private static File getDefaultPuzzleSaveDir() {
        File defaultPuzzleSaveDir = new File(SPIELVERZEICHNIS, "puzzles");
        boolean success = true;
        if (!defaultPuzzleSaveDir.exists()) {
            success = defaultPuzzleSaveDir.mkdir();
        }
        if (!success) {
            return SPIELVERZEICHNIS;
        } else {
            return defaultPuzzleSaveDir;
        }
    }


}

