package severeLobster.backend.spiel;

import infrastructure.constants.enums.SpielmodusEnumeration;

import java.io.Serializable;

/**
 * Spiel - Besteht aus einem Spielfeld von Spielsteinen
 *
 * @author Lars Schlegelmilch
 */
public class Spiel implements Serializable {

    private Spielfeld spielfeld;
    private SpielmodusEnumeration spielmodus;

    /**
     * Ein Spiel hat ein Spielfeld und einen Modus
     * @param spielfeld Spielfeld des Spiels
     * @param spielmodus Spielmodus des Spiels - Wird das Spiel gerade erstellt oder gespielt?
     */
    public Spiel(Spielfeld spielfeld, SpielmodusEnumeration spielmodus) {
        this.spielfeld = spielfeld;
        this.spielmodus = spielmodus;
    }

    /**
     * Tippt einen Spielstein in einem Koordinatensystem
     *
     * @param x              X-Achsenwert auf dem sich der Spielstein befinden soll
     * @param y              Y-Achsenwert auf dem sich der Spielstein befinden soll
     * @param spielsteinTipp Spielstein, der getippt wird.
     * @return Ist der Tipp richtig?
     */
    public boolean spielsteinTippen(int x, int y, Spielstein spielsteinTipp) {
        Spielstein spielfeldSpielstein = spielfeld.getSpielstein(x, y);

        return spielsteinTipp.getClass().equals(spielfeldSpielstein.getClass());
    }

    public Spielfeld getSpielfeld() {
        return spielfeld;
    }

    public SpielmodusEnumeration getSpielmodus() {
        return spielmodus;
    }
}
