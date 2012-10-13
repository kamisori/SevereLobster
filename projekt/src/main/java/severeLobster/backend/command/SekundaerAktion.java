package severeLobster.backend.command;

import severeLobster.backend.spiel.Spiel;

/**
 * Sekundaeraktion - Sekundaere Aktion die vom Spieler aus gesteuert wird. (Z.B.
 * Mausrechtsklick)
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
    }

    @Override
    public void undo() {
        // TODO ...
    }
}
