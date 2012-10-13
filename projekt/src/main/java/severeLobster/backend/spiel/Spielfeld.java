package severeLobster.backend.spiel;

import infrastructure.constants.enums.SchwierigkeitsgradEnumeration;

import java.io.Serializable;

/**
 * Spielfeld eines Spiels - Besteht aus einem 2Dimensionalem-Spielstein
 * Koordinatensysten. Vorschlag fuer geaenderte API (ohne auskommentierte
 * Sachen): Eingeschraenkte API fuer weniger moegliche Zustaende, die man
 * kontrollieren muss. Fuer Groessenaenderunge einfach neue Instanz erzeugen.
 * Wenn einmal erstellt, ist das Feld mit den Spielsteinen konstant.
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public class Spielfeld implements Serializable {

    private static final long serialVersionUID = -4673868060555706754L;
    private final Spielstein[][] koordinaten;

    /**
     * Zaehlt die Pfeile auf dem Spielfeld.
     * 
     * @return result Die Anzahl der Pfeile auf dem Spielfeld.
     */
    private int countPfeile() {
        int result = 0;
        for (Spielstein[] zeile : koordinaten) {
            for (Spielstein stein : zeile) {
                if (stein.getRealState() instanceof Pfeil)
                    result++;
            }
        }
        return result;
    }

    /**
     * Zaehlt die Sterne auf dem Spielfeld.
     * 
     * @return result Die Anzahl der Sterne auf dem Spielfeld.
     */
    private int countSterne() {
        int result = 0;
        for (Spielstein[] zeile : koordinaten) {
            for (Spielstein stein : zeile) {
                if (stein.getRealState() instanceof Stern)
                    result++;
            }
        }
        return result;
    }

    /**
     * Im Vergleich zur vorherigen API sind breite und hoehe bei parameterliste
     * vertauscht, um Einheitlichkeit mit getSpielstein() zu haben.
     * 
     * @param breite
     *            Breite des Spielfeldes
     * @param hoehe
     *            Hoehe des Spielfeldes
     */
    public Spielfeld(final int breite, final int hoehe) {
        if (breite < 1 || hoehe < 1) {
            throw new IllegalArgumentException("Nicht erlaubte Breite/Hoehe");
        }
        this.koordinaten = new Spielstein[breite][hoehe];

        /** Feld mit NullState Spielsteinen fuellen */
        for (int hoeheIndex = 0; hoeheIndex < hoehe; hoeheIndex++) {
            for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                koordinaten[breiteIndex][hoeheIndex] = new Spielstein();
            }
        }
    }

    /**
     * Schaetzt anhand der Groesse des Spielfeldes und den Verhaeltnisen
     * zwischen Pfeilen und Sternen sowie zwischen belegten und unbelegten
     * Spielfeldern einen Schwierigkeitsgrad
     * 
     * @return Schwierigkeitsgrad des Spielfeldes
     */
    public SchwierigkeitsgradEnumeration getSchwierigkeitsgrad() {
        int sterne = countSterne();
        int pfeile = countPfeile();
        int spielfeldFlaeche = getBreite() * getHoehe();

        float sterndichte = sterne / spielfeldFlaeche;
        float pfeildichte = pfeile / spielfeldFlaeche;
        float schwierigkeit = pfeile / sterne + sterndichte + pfeildichte;

        if (schwierigkeit > 4)
            return SchwierigkeitsgradEnumeration.LEICHT;
        else if (schwierigkeit > 2)
            return SchwierigkeitsgradEnumeration.MITTEL;
        else
            return SchwierigkeitsgradEnumeration.SCHWER;
    }

    public Spielstein getSpielstein(int x, int y) {
        return koordinaten[x][y];
    }

    public int getBreite() {
        return koordinaten.length;
    }

    public int getHoehe() {
        return koordinaten[0].length; // TODO Das funktioniert erstmal nur fuer
                                      // quadratische Spielfelder...
    }

    /**
     * Neuer Workaround: Die urspruengliche Methode haette sich mit dem Konzept,
     * dass das Spielfeld nach dem Erstellen konstant ist, nicht vertragen. Wenn
     * ein vorhandener Spielstein ueberschrieben wuerde, waeren hierfuer ja
     * keine Listener mehr registriert. Da die Methode von einigen benoetigt
     * wird, nun folgender Workaround: Es wird nicht der uebergebene Spielstein
     * an die Stelle im Spielfeld gesetzt, sondern der Spielstein an dieser
     * Stelle wird nur mit dem SpielsteinState des uebergebenen Spielsteins
     * aktualisiert/ueberschrieben. So verhaelt sich das Spielfeld nach au�en
     * wie gehabt und die Tests sollten weiterhin funktionieren .
     * 
     * Setzt einen Spielstein fuer ein Spielfeld an eine bestimmte Koordinate
     * 
     * @param x
     *            X-Achsen Koordinatenwert
     * @param y
     *            Y-Achsen Koordinatenwert
     * @param spielstein
     *            Spielstein der gesetzt werden soll
     */
    public void setSpielstein(int x, int y, Spielstein spielstein) {
        koordinaten[x][y].setRealState(spielstein.getRealState());
        koordinaten[x][y].setVisibleState(spielstein.getVisibleState());
    }
}
