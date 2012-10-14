package severeLobster.backend.command;

import severeLobster.backend.spiel.Spiel;

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
