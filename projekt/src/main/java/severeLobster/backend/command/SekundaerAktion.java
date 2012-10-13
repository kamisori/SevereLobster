package severeLobster.backend.command;

import infrastructure.constants.enums.SpielmodusEnumeration;
import severeLobster.backend.spiel.Ausschluss;
import severeLobster.backend.spiel.Pfeil;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.Stern;

/**
 * Sekundaeraktion - Sekundaere Aktion die vom Spieler aus gesteuert wird. (Z.B.
 * Mausrechtsklick)
 ************************************************************************** 
 * TODO: Im Spiel Modus wird ein Ausschluss getoggelt.
 ************************************************************************** 
 * TODO: Im Editmodus werden hiermit Steine entfernt.
 * 
 * @author Lars Schlegelmilch, Paul Bruell
 */
public class SekundaerAktion implements Aktion {

    private Spiel spiel;

    public SekundaerAktion(Spiel spiel) {
        this.spiel = spiel;
    }

    @Override
    public void execute(int x, int y) {
        spiel.sekundaerAktion(x, y);
    }

    @Override
    public void undo() {
        // TODO ...
    }
}
