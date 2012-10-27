package infrastructure.exceptions;

import java.io.IOException;

/**
 * Exception fuer ein nicht loeschbares Spiel
 * 
 * @author Lars Schlegelmilch
 */
public class SpielNichtLoeschbarException extends IOException {

    public SpielNichtLoeschbarException() {
        super("Die Spieldatei konnte nicht geloescht werden!");
    }
}
