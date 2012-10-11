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
    // private Spielstein[][] koordinaten;
    private final Spielstein[][] koordinaten;

    // public Spielfeld() {
    // }

    /**
     * Im Vergleich zur vorherigen API sind breite und laenge bei parameterliste
     * vertauscht, um Einheitlichkeit mit getSpielstein() zu haben.
     * 
     * @param breite
     * @param hoehe
     */
    public Spielfeld(final int breite, final int hoehe) {
        if (breite < 1 || hoehe < 1) {
            throw new IllegalArgumentException("Nicht erlaubte Breite/Hoehe");
        }
        this.koordinaten = new Spielstein[breite][hoehe];

        /** Feld mit NullState Spielsteinen f�llen */
        for (int hoeheIndex = 0; hoeheIndex < hoehe; hoeheIndex++) {
            for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                koordinaten[breiteIndex][hoeheIndex] = new Spielstein();
            }
        }
    }

    /**
     * Bestimmt Anhang der der Groesse des Spielfeldes und der Anzahl an
     * Spielsteinen einen Schwierigkeitsgrad
     * 
     * @return Schwierigkeitsgrad des Spielfeldes
     */
    public SchwierigkeitsgradEnumeration getSchwierigkeitsgrad() {
        // TODO Anhand von Groesse und Spielsteinen einen Schwierigkeitsgrad
        // ermitteln
        return SchwierigkeitsgradEnumeration.LEICHT;
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
        koordinaten[x][y] = spielstein;
    }
}
