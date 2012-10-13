package severeLobster.backend.spiel;

import infrastructure.constants.enums.SchwierigkeitsgradEnumeration;
import infrastructure.constants.enums.SpielmodusEnumeration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

/**
 * Spielfeld eines Spiels - Besteht aus einem 2Dimensionalem-Spielstein
 * Koordinatensysten. Fuer Groessenaenderunge einfach neue Instanz erzeugen.
 * Wenn einmal erstellt, ist das Feld mit den Spielsteinen konstant. Klasse ist
 * nicht Thread-safe.
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public class Spielfeld implements Serializable {

    private static final long serialVersionUID = -4673868060555706754L;

    private final EventListenerList listeners = new EventListenerList();
    /** Groesse ist Konstant, aber Steine koennen ueber setter gesetzt werden */
    private final Spielstein[][] realSteine;
    private final Spielstein[][] visibleSteine;
    private final IGotSpielModus gotSpielModus;

    /**
     * Zaehlt die Pfeile auf dem Spielfeld.
     * 
     * @return result Die Anzahl der Pfeile auf dem Spielfeld.
     */
    private int countPfeile() {
        int result = 0;
        for (Spielstein[] zeile : realSteine) {
            for (Spielstein stein : zeile) {
                if (stein instanceof Pfeil)
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
        for (Spielstein[] zeile : realSteine) {
            for (Spielstein stein : zeile) {
                if (stein instanceof Stern)
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
    public Spielfeld(final IGotSpielModus gotSpielModus, final int breite,
            final int hoehe) {
        if (breite < 1 || hoehe < 1) {
            throw new IllegalArgumentException("Nicht erlaubte Breite/Hoehe");
        }
        if (null == gotSpielModus) {
            throw new NullPointerException("Spiel ist null");
        }
        this.gotSpielModus = gotSpielModus;
        this.realSteine = new Spielstein[breite][hoehe];
        this.visibleSteine = new Spielstein[breite][hoehe];

        /** Beide Feldansichten mit Null State Spielsteinen fuellen */
        for (int hoeheIndex = 0; hoeheIndex < hoehe; hoeheIndex++) {
            for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                realSteine[breiteIndex][hoeheIndex] = KeinStein.getInstance();
                visibleSteine[breiteIndex][hoeheIndex] = KeinStein
                        .getInstance();
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

    public int getBreite() {
        return realSteine.length;
    }

    public int getHoehe() {
        return realSteine[0].length; // TODO Das funktioniert erstmal nur fuer
                                     // quadratische Spielfelder...
    }

    /**
     * Liefert den Spielstein an der angegebenen Position im Spielfeld.
     * Verhalten unterscheidet sich bei den unterschiedlichen Spielmodi. Beim
     * Modus Spielen wird der sichtbare Stein zurueckgegeben. Beim Modus
     * Editieren wird der reale Stein zurueckgegeben.
     * 
     * 
     * @param x
     *            X-Achsen Koordinatenwert
     * @param y
     *            Y-Achsen Koordinatenwert
     * @param spielstein
     *            Spielstein der gesetzt werden soll
     */
    public Spielstein getSpielstein(final int x, final int y)
            throws IndexOutOfBoundsException {

        throwExceptionIfIndexOutOfBounds(x, y);
        if (isEditierModus()) {
            return realSteine[x][y];
        } else {
            return visibleSteine[x][y];
        }
    }

    /**
     * Setzt einen Spielstein an eine bestimmte Koordinate. Verhalten
     * unterscheidet sich bei den unterschiedlichen Spielmodi. Beim Modus
     * Spielen wird newStein als sichtbarer Stein gesetzt - der reale Stein wird
     * davon nicht beeinflusst. Beim Modus Editieren wird der reale Stein
     * gesetzt und der sichtbare Stein wird nur gesetzt, wenn es sich beim Stein
     * um einen Pfeil handelt (weil diese ja in beiden Modi dargestellt werden
     * muessen) - ansonsten wird der sichtbare Stein im Editiermodus nicht
     * beeinflusst. Bereits angefangene Spielfelder sind immer noch editierbar;
     * Spielfelder lassen sich zu jedem Zeitpunkt und in jedem Zustand komplett
     * speichern und wieder laden.
     * 
     * @param x
     *            X-Achsen Koordinatenwert
     * @param y
     *            Y-Achsen Koordinatenwert
     * @param spielstein
     *            Spielstein der gesetzt werden soll
     */
    public void setSpielstein(final int x, final int y, Spielstein newStein)
            throws IndexOutOfBoundsException {
        throwExceptionIfIndexOutOfBounds(x, y);
        if (null == newStein) {
            newStein = KeinStein.getInstance();
        }
        if (isEditierModus()) {
            /**
             * Im Editiermodus darf jeder Spielstein gesetzt werden - keine
             * Ueberpruefung.
             */
            realSteine[x][y] = newStein;
            /** Pfeile werden in jedem Modus immer angezeigt. */
            if (newStein instanceof Pfeil) {
                visibleSteine[x][y] = newStein;
            }
            /** Listener benachrichtigen */
            fireSpielsteinChanged(x, x, newStein);
            return;
        }
        /** Spielmodus: */
        if (!isEditierModus()) {
            /** Ausser Pfeilen darf im Spielmodus alles gesetzt werden */
            if (!(newStein instanceof Pfeil)) {
                visibleSteine[x][y] = newStein;
                /** Listener benachrichtigen */
                fireSpielsteinChanged(x, y, newStein);
            }
        }

    }

    /**
     * Benachrichtigt alle Listener dieses Spielsfelds ueber einen neuen Wert an
     * den uebergeben Koordinaten. Implementation ist glaube ich aus JComponent
     * oder Component kopiert.
     * 
     * @param newState
     *            - Der neue Status, der an die Listener mitgeteilt wird.
     */
    private void fireSpielsteinChanged(final int x, final int y,
            Spielstein newStein) {

        /** Gibt ein Array zurueck, das nicht null ist */
        final Object[] currentListeners = listeners.getListenerList();
        /**
         * Rufe die Listener auf, die als ISpielfeldListener angemeldet sind.
         */
        for (int i = currentListeners.length - 2; i >= 0; i -= 2) {
            if (currentListeners[i] == ISpielfeldListener.class) {
                ((ISpielfeldListener) currentListeners[i + 1])
                        .spielsteinChanged(this, x, y, newStein);
            }
        }
    }

    /**
     * Fuegt listener zu der Liste hinzu.
     * 
     * @param listener
     *            ISpielfeldListener
     */
    public void addSpielfeldListener(final ISpielfeldListener listener) {
        listeners.add(ISpielfeldListener.class, listener);
    }

    /**
     * Entfernt listener von der Liste.
     * 
     * @param listener
     *            ISpielsteinListener
     */
    public void removeSpielfeldListener(final ISpielfeldListener listener) {
        listeners.remove(ISpielfeldListener.class, listener);
    }

    /**
     * Wirft eine IndexOutOfBoundException mit eigener Nachricht, wenn
     * Koordinaten ausserhalb des Spielfeldes liegen
     */
    private void throwExceptionIfIndexOutOfBounds(final int x, final int y) {
        if ((x < 0) || (x > getBreite() - 1) || (y < 0) || (y > getHoehe() - 1)) {
            throw new ArrayIndexOutOfBoundsException(
                    "The passed koordinates X:" + x + " Y:" + y
                            + " lie outside of the current Spielfeld.");
        }
    }

    /**
     * Gibt eine Liste mit den fuer diese Koordinate aktuell setzbaren
     * Spielsteinen zurueck. Die moeglichen Spielsteine haengen vom SpielModus
     * ab.
     * 
     * @return Eine Liste mit den fuer diese Koordinate aktuell auswaehlbaren
     *         Spielsteinen.
     */
    public List<? extends Spielstein> listAvailableStates(final int x,
            final int y) {

        if (isEditierModus()) {
            final List<Spielstein> editierModusList = new ArrayList<Spielstein>(
                    11);
            editierModusList.add(new KeinStein());
            editierModusList.add(new Ausschluss());
            editierModusList.add(new Stern());
            editierModusList.addAll(Pfeil.listAlleMoeglichenPfeile());
            return editierModusList;
        } else {
            /** Spielmodus */
            /**
             * Wenn an der Stelle ein Pfeil ist und man im Spielmodus ist, kann
             * man nichts auswaehlen.
             */
            if (getSpielstein(x, y) instanceof Pfeil) {

                return new ArrayList<Spielstein>();
            } else {
                final List<Spielstein> spielModusList = new ArrayList<Spielstein>(
                        3);
                spielModusList.add(new KeinStein());
                spielModusList.add(new Ausschluss());
                spielModusList.add(new Stern());
                return spielModusList;
            }
        }

    }

    /**
     * 
     * @return
     */
    private boolean isEditierModus() {
        return gotSpielModus.getSpielmodus().equals(
                SpielmodusEnumeration.EDITIEREN);
    }

    /**
     * Ueberprueft ob das Spielfeld geloest wurde (Sieg).
     * 
     * @return sieg
     */
    public boolean isSolved() {
        for (int i = 0; i < this.getBreite(); i++) {

            for (int k = 0; k < this.getHoehe(); k++) {
                Spielstein currentVisibleItem = visibleSteine[i][k];
                Spielstein currentRealItem = realSteine[i][k];

                if (currentVisibleItem instanceof Stern
                        && !(currentRealItem instanceof Stern)) {

                    // System.out.println("kein Stern, Stern getippt");
                    return false;
                }

                else if ((currentVisibleItem instanceof KeinStein || currentVisibleItem instanceof Ausschluss)
                        && !(currentRealItem instanceof KeinStein)) {
                    // System.out.println("nicht NullState, Ausschluss oder nichts getippt");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Ueberprueft ob Fehler in einem Spielfeld vorhanden sind, d.h. Tipps
     * abgegeben wurden, die nicht der Loesung entsprechen
     * 
     * @return fehler vorhanden
     */
    public boolean hasErrors() {

        for (int i = 0; i < this.getBreite(); i++) {

            for (int k = 0; k < this.getHoehe(); k++) {
                Spielstein currentVisibleItem = visibleSteine[i][k];
                Spielstein currentRealItem = realSteine[i][k];
                if (currentVisibleItem instanceof Ausschluss
                        && currentRealItem instanceof Stern) {
                    return true;
                } else if (currentVisibleItem instanceof Stern
                        && currentRealItem instanceof KeinStein) {
                    return true;
                }

            }
        }
        return false;
    }

}
