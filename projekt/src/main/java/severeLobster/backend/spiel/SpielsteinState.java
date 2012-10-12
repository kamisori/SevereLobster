package severeLobster.backend.spiel;

import java.io.Serializable;

/**
 * Abstrakter SpielsteinState - Spiegelt den Status eines Spielsteines
 * wieder
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public abstract class SpielsteinState implements Serializable {

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
