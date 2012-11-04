package infrastructure.exceptions;

import infrastructure.ResourceManager;

/**
 * Exception fuer eine ungueltige Groessenangabe eines Spielfeldes.
 * 
 * @author Lars Schlegelmilch
 */
public class UngueltigeSpielfeldgroessenangabeException extends
        IllegalArgumentException {

    private static final ResourceManager resourceManager = ResourceManager.get();

    public UngueltigeSpielfeldgroessenangabeException() {
        //super("Die Groessenangabe des Spielfeldes ist ungueltig!");
        super(resourceManager.getText("exception.size.of.playing.field.invalid"));
    }
}
