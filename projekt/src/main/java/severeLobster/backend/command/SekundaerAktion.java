package severeLobster.backend.command;

import infrastructure.constants.enums.SpielmodusEnumeration;
import severeLobster.backend.spiel.Ausschluss;
import severeLobster.backend.spiel.Spiel;

/**
 * Sekundäraktion - Sekundäre Aktion die vom Spieler aus gesteuert wird. (Z.B. Mausrechtsklick)
 *
 * @author Lars Schlegelmilch
 */
public class SekundaerAktion implements Aktion {

    private Spiel spiel;

    public SekundaerAktion(Spiel spiel) {
        this.spiel = spiel;
    }

    @Override
    public void execute(int x, int y) {
        if (SpielmodusEnumeration.SPIELEN.equals(spiel.getSpielmodus())) {
            spiel.spielsteinTippen(x, y, new Ausschluss(true));
        } else if (SpielmodusEnumeration.EDITIEREN.equals(spiel.getSpielmodus())) {
            //TODO: Hier käme die Spielstein setzen für das Erstellen eines Puzzles hin - Später!!!
        }
    }

    @Override
    public void undo() {
        // TODO ...
    }
}
