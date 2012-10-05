package severeLobster.backend.spieler;

import java.awt.event.MouseAdapter;

/**
 * Vom Spieler werden alle Aktionen die in der GUI geschehen an das Backend bzw. an das Spiel weitergeleitet.
 *
 * @author Lars Schlegelmilch
 */
public class Spieler extends MouseAdapter {

    private int anzahlZuege;

    public Spieler() {
        anzahlZuege = 0;
    }
}
