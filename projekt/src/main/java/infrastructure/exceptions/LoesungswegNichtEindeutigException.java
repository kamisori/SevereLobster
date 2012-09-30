package infrastructure.exceptions;

/**
 * Exception wird geworfen, wenn kein eindeutiger Lösungsweg für ein erstelltes Puzzle vorhanden ist.
 *
 * @author Lars Schlegelmilch
 */
public class LoesungswegNichtEindeutigException extends Exception {

    public LoesungswegNichtEindeutigException(String message) {
        super("Der Lösungsweg ist nicht eindeutig!");
    }
}
