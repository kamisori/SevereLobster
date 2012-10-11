package infrastructure.exceptions;

/**
 * Exception fuer eine ungueltige Groessenangabe eines Spielfeldes.
 *
 * @author Lars Schlegelmilch
 */
public class UngueltigeSpielfeldgroessenangabeException extends IllegalArgumentException {

    public UngueltigeSpielfeldgroessenangabeException() {
        super("Die Groessenangabe des Spielfeldes ist ungueltig!");
    }
}
