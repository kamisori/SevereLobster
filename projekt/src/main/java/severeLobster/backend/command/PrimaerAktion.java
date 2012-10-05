package severeLobster.backend.command;

import infrastructure.constants.enums.SpielmodusEnumeration;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.Stern;

/**
 * Primäraktion - Primäre Aktion die vom Spieler aus gesteuert wird. (Z.B. Mauslinksklick)
 *
 * @author Lars Schlegelmilch
 */
public class PrimaerAktion implements Aktion {

    private Spiel spiel;

    public PrimaerAktion(Spiel spiel) {
        this.spiel = spiel;
    }


    /**
     * Führt anhand des aktuellem Spielmodus verschieden Methoden aus:
     * Befinden wir uns im Modus "SPIELEN", so wird ein Spielstein auf einem Feld getippt,
     * im Modus "EDITIEREN" wird ein Spielstein platziert.
     *
     * @param x          X-Achsenwert auf dem der Spielstein gesetzt/getippt wird
     * @param y          Y-Achsenwert auf dem der Spielstein gesetzt/getippt wird
     */
    @Override
    public void execute(int x, int y) {
        if (SpielmodusEnumeration.SPIELEN.equals(spiel.getSpielmodus())) {
            spiel.spielsteinTippen(x, y, new Stern(true));
        } else if (SpielmodusEnumeration.EDITIEREN.equals(spiel.getSpielmodus())) {
            //TODO: Hier käme die Spielstein setzen für das Erstellen eines Puzzles hin - Später!!!
        }
    }

    @Override
    public void undo() {
        // TODO ...
    }
}
