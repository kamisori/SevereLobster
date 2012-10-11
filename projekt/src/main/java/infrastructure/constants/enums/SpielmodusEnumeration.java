package infrastructure.constants.enums;

/**
 * Spielmodus Enumeration - Gibt an, in welchem Modus sich der Spieler bzw. das
 * aktuelle Spiel gerade befindet.
 * 
 * @author Lars Schlegelmilch
 */
public enum SpielmodusEnumeration {

    /**
     * Spielmodus: Spielen - Der Benutzer befindet sich im Spiel, indem er
     * Sterne bzw. leere Felder tippen muss.
     */
    SPIELEN,

    /**
     * Spielmodus: Editieren - Der Benutzer befindet sich im Editiermodus, indem
     * er ein eigenes Puzzle erstellt.
     */
    EDITIEREN
}
