package severeLobster.backend.spiel;

import java.io.Serializable;

/**
 * Abstrakter Spielstein.
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public abstract class Spielstein implements Serializable {

    /**
     * Abgeleitete Klassen sollen eigene toString Methode implementieren.
     */
    @Override
    public abstract String toString();

    /**
     * Abgeleitete Klassen sollen eigene equals Methode implementieren.
     */
    @Override
    public abstract boolean equals(Object obj);

}
