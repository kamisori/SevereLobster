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
    public static final String SPIELSTAND_DATEITYP = "sav";

    /**
     * Dateiendung .puz - Dateiendung fuer gespeicherte erstelle Puzzles
     */
    public static final String PUZZLE_DATEITYP = "puz";

    /**
     * Standardverzeichnis fuer gespeicherte Spiele
     */
    public static final File DEFAULT_SPIEL_SAVE_DIR = getDefaultSpielSaveDir();

    /**
     * Standardverzeichnis fuer gespeicherte erstellte Puzzles
     */
    public static final File DEFAULT_PUZZLE_SAVE_DIR = getDefaultPuzzleSaveDir();

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
     * Gibt das Standardverzeichnis fuer gespeicherte erstellte Puzzle zurueck -
     * wenn es nicht existiert, wird es angelegt
     * 
     * @return Standardverzeichnis fuer gespeicherte erstelle Puzzle
     */
    private static File getDefaultPuzzleSaveDir() {
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
