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
     * Standardverzeichnis fuer gespeicherte Spiele
     */
    public static final File DEFAULT_SPIEL_SAVE_DIR = getDefaultSpielSaveDir();

    /**
     * Standardverzeichnis fuer gespeicherte erstellte Puzzles
     */
    public static final File DEFAULT_PUZZLE_SAVE_DIR = getDefaultPuzzleSaveDir();

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
