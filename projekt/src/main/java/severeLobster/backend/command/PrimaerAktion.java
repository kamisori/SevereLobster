package severeLobster.backend.command;

import severeLobster.backend.spiel.Spiel;

/**
 * Primaeraktion - Primaere Aktion die vom Spieler aus gesteuert wird. (Z.B.
 * Mauslinksklick)
 ************************************************************************** 
 * TODO: Im Spiel Modus wird ein Stern getoggelt.
 ************************************************************************** 
 * TODO: Im Editmodus muss mit uebergeben werden welche Steinart (Stern/Pfeil)
 * gesetzt wird. Um die Richtung zu bestimmen koennte man auch mehrfach angaben
 * verwenden
 * 
 * @author Lars Schlegelmilch, Paul Bruell
 */
public class PrimaerAktion implements Aktion {

    private Spiel spiel;

    public PrimaerAktion(Spiel spiel) {
        this.spiel = spiel;
    }

    /**
     * Fuehrt anhand des aktuellem Spielmodus verschieden Methoden aus: Befinden
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
        spiel.primaerAktion(x, y);
    }

    @Override
    public void undo() {
    }
}
