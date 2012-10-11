package severeLobster.backend.command;

import severeLobster.backend.spiel.Spiel;

/**
 * Primäraktion - Primäre Aktion die vom Spieler aus gesteuert wird. (Z.B.
 * Mauslinksklick)
 * 
 * @author Lars Schlegelmilch
 */
public class PrimaerAktion implements Aktion {

    private Spiel spiel;

    public PrimaerAktion(Spiel spiel) {
        this.spiel = spiel;
    }

    /**
     * Führt anhand des aktuellem Spielmodus verschieden Methoden aus: Befinden
     * wir uns im Modus "SPIELEN", so wird ein Spielstein auf einem Feld
     * getippt, im Modus "EDITIEREN" wird ein Spielstein platziert.
     * 
     * @param x
     *            X-Achsenwert auf dem der Spielstein gesetzt/getippt wird
     * @param y
     *            Y-Achsenwert auf dem der Spielstein gesetzt/getippt wird
     */
    @Override
    public void execute(int x, int y) {
    }

    @Override
    public void undo() {
        // TODO ...
    }
}
