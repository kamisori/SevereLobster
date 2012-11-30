package severeLobster.backend.spiel;

import infrastructure.constants.enums.SchwierigkeitsgradEnumeration;
import infrastructure.constants.enums.SpielmodusEnumeration;
import infrastructure.ResourceManager;

import javax.swing.event.EventListenerList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Spielfeld eines Spiels - verhaelt sich nach aussen wie ein zweidimensionales
 * Feld. Besteht in wirklichkeit aus je einer zweidimensionalen Schicht pro
 * Spielmodus (also 2). Nach aussen ist jeweils nur die Schicht fuer den
 * jeweiligen Spielmodus sichtbar. Nach der Erstellung ist die Groesse des
 * Spielfeldes konstant. Klasse ist nicht Thread-safe.
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber, Christian Lobach, Paul Bruell
 */
public class Spielfeld implements Serializable, ISpielfeldReadOnly {
    private final ResourceManager resourceManager = ResourceManager.get();
    /**
     * Liste mit den fuer die Spielfeldinstanz angemeldeten
     * SpielfeldListenern(Observer).
     */
    private final EventListenerList listeners = new EventListenerList();
    /** Wirklich gesetzte bzw im Editiermodus sichtbare Steine: */
    private final Spielstein[][] realSteine;
    /** Geratene bzw. im Spielmodus sichtbare Steine: */
    private final Spielstein[][] visibleSteine;
    /**
     * Spielmodus ist aussen gesetzt, und Spielfeld greift ueber das Interface
     * immer auf den aktuellen Spielstand zu. Der Spielmodus definiert das
     * Verhalten von Spielfeld nach aussen.
     */
    private IGotSpielModus gotSpielModus;

    /**
     * Erstellt ein neues, leeres Spielfeld der angegebenen Groesse. Alle
     * Feldelemente sind mit KeinStein Instanzen initialisiert.
     * 
     * 
     * @param breite
     *            Breite des Spielfeldes
     * @param hoehe
     *            Hoehe des Spielfeldes
     */
    public Spielfeld(final IGotSpielModus gotSpielModus, final int breite,
            final int hoehe) {
        if (breite < 1 || hoehe < 1) {
            throw new IllegalArgumentException(resourceManager.getText("backend.spiel.not.allowed.size"));
        }
        if (null == gotSpielModus) {
            throw new NullPointerException(resourceManager.getText("backend.spiel.is.null"));
        }
        this.gotSpielModus = gotSpielModus;
        this.realSteine = new Spielstein[breite][hoehe];
        this.visibleSteine = new Spielstein[breite][hoehe];

        /** Beide Feldansichten mit KeinStein Spielsteinen fuellen */
        for (int hoeheIndex = 0; hoeheIndex < hoehe; hoeheIndex++) {
            for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
                realSteine[breiteIndex][hoeheIndex] = KeinStein.getInstance();
                visibleSteine[breiteIndex][hoeheIndex] = KeinStein
                        .getInstance();
            }
        }
    }

    private int countPfeileZeile(int y) {
        int result = 0;
        for (int i = 0; i < getBreite(); i++) {
            if (realSteine[i][y] instanceof Pfeil)
                result++;
        }
        return result;
    }

    private int countPfeileSpalte(int x) {
        int result = 0;
        for (int i = 0; i < getHoehe(); i++) {
            if (realSteine[x][i] instanceof Pfeil)
                result++;
        }
        return result;
    }

    public int countSterneZeile(int y) {
        int result = 0;
        for (int i = 0; i < getBreite(); i++) {
            if (realSteine[i][y] instanceof Stern)
                result++;
        }
        return result;
    }

    public int countSterneSpalte(int x) {
        int result = 0;
        for (int i = 0; i < getHoehe(); i++) {
            if (realSteine[x][i] instanceof Stern)
                result++;
        }
        return result;
    }

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
    public int countSterne() {
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
     * Zaehlt die getippten Sterne auf dem Spielfeld.
     *
     * @return result Die Anzahl der getippten Sterne auf dem Spielfeld.
     */
    public int countSterneGetippt() {
        int result = 0;
        for (Spielstein[] zeile : visibleSteine) {
            for (Spielstein stein : zeile) {
                if (stein instanceof Stern)
                    result++;
            }
        }
        return result;
    }

    /**
     * Schaetzt anhand der Groesse des Spielfeldes und den Verhaeltnisen
     * zwischen Pfeilen und Sternen sowie zwischen belegten und unbelegten
     * Spielfeldern einen Schwierigkeitsgrad
     * 
     * @return Schwierigkeitsgrad des Spielfeldes
     */
    public SchwierigkeitsgradEnumeration getSchwierigkeitsgrad() {
        try {
            float sterne = countSterne();
            float pfeile = countPfeile();
            float spielfeldFlaeche = getBreite() * getHoehe();

            double schwierigkeit = pfeile * pfeile;
            schwierigkeit += pfeile * sterne;
            schwierigkeit /= sterne * spielfeldFlaeche * spielfeldFlaeche;
            schwierigkeit *= 100;

            if (schwierigkeit > 70.0)
                return SchwierigkeitsgradEnumeration.LEICHT;
            else if (schwierigkeit > 25.0)
                return SchwierigkeitsgradEnumeration.MITTEL;
            else
                return SchwierigkeitsgradEnumeration.SCHWER;
        } catch (ArithmeticException e) {
            return SchwierigkeitsgradEnumeration.LEICHT;
        }

    }

    public int getBreite() {
        return realSteine.length;
    }

    public int getHoehe() {
        return realSteine[0].length; // TODO Das funktioniert erstmal nur fuer
                                     // rechteckige Spielfelder
    }

    /**
     * Liefert den Spielstein an der angegebenen Position im Spielfeld.
     * Verhalten unterscheidet sich bei den unterschiedlichen Spielmodi. Beim
     * Modus Spielen wird der sichtbare Stein zurueckgegeben. Bei den Modi
     * Editieren und Loesen wird der reale Stein zurueckgegeben.
     * 
     * @param x
     *            X-Achsen Koordinatenwert
     * @param y
     *            Y-Achsen Koordinatenwert
     */
    public Spielstein getSpielstein(final int x, final int y)
            throws IndexOutOfBoundsException {

        throwExceptionIfIndexOutOfBounds(x, y);
        if (isEditierModus() || gotSpielModus.getSpielmodus().equals(SpielmodusEnumeration.LOESEN)) {
            return realSteine[x][y];
        } else {
            return visibleSteine[x][y];
        }
    }

    /**
     * Der gotSpielModus muss geändert werden können, falls ein Spielfeld zum Lösen kopiert wird
     * @param newGotSpielModus der neue gotSpielModus
     *
     */
    public void setGotSpielModus(IGotSpielModus newGotSpielModus)
    {
        this.gotSpielModus = newGotSpielModus;
    }



    /**
     * Setzt einen Spielstein an eine bestimmte Koordinate. Verhalten
     * unterscheidet sich bei den unterschiedlichen Spielmodi.
     * ......................................................................
     * Beim Modus Spielen wird der sichtbare Stein gesetzt - der reale Stein
     * wird davon nicht beeinflusst.
     * ......................................................................
     * Beim Modus Editieren wird der reale Stein gesetzt. Darueber hinaus wird
     * der sichtbare Stein gesetzt, wenn es sich beim neu gesetzten Stein um
     * einen Pfeil handelt (weil diese ja in beiden Modi dargestellt werden
     * muessen).
     * ......................................................................
     * Spielfelder lassen sich zu jedem Zeitpunkt und in jedem Zustand komplett
     * speichern und wieder laden. Bereits angefangene Spielfelder sind immer
     * noch editierbar. Wenn newStein null ist, wird KeinStein als Stein
     * gesetzt.
     * 
     * @param x
     *            X-Achsen Koordinatenwert
     * @param y
     *            Y-Achsen Koordinatenwert
     * @param newStein
     *            Spielstein der gesetzt werden soll
     */
    public void setSpielstein(final int x, final int y, Spielstein newStein)
            throws IndexOutOfBoundsException {

        throwExceptionIfIndexOutOfBounds(x, y);
        if (null == newStein) {
            newStein = KeinStein.getInstance();
        }
        if (gotSpielModus.getSpielmodus().equals(SpielmodusEnumeration.LOESEN))
        {
            /**
             * Im Loesen-Modus dürfen Stern, MoeglicherStern, Ausschluss und KeinStein gesetzt werden
             * Observer müssen nicht benachrichtigt werden
             */
            if(newStein instanceof Stern || newStein instanceof MoeglicherStern || newStein instanceof Ausschluss || newStein instanceof KeinStein ){
                realSteine[x][y] = newStein;
            }

        }
        else if (isEditierModus()) {
            /**
             * Im Editiermodus duerfen Pfeil, Stern und KeinStein gesetzt werden
             */
            if (newStein instanceof Pfeil || newStein instanceof Stern
                    || newStein instanceof KeinStein) {
                /** Neuen Stein in der realen Schicht setzen */
                realSteine[x][y] = newStein;
                if (newStein instanceof Pfeil) {
                    /**
                     * Wenn neuer Stein ein Pfeil ist, dann auch in der
                     * sichtbaren Schicht setzen, da Pfeile in jedem Modus
                     * gleichermassen sichtbar sind.
                     */
                    visibleSteine[x][y] = newStein;
                }
            }
            /**
             * Listener/Observer ueber Aenderungen im Spielfeld benachrichtigen.
             * Dabei werden die Koordinaten der Aenderung und der neue
             * Spielstein an der Stelle mitgegeben.
             */
            fireSpielsteinChanged(x, y, newStein);
        } else {

            /**
             * Ausser Pfeilen darf im Spielmodus alles gesetzt werden. Es
             * duerfen aber auch keine Pfeile ueberschrieben werden.
             */
            if (!(getSpielstein(x, y) instanceof Pfeil)
                    && !(newStein instanceof Pfeil)) {
                visibleSteine[x][y] = newStein;

                /**
                 * Listener/Observer ueber Aenderungen im Spielfeld
                 * benachrichtigen. Dabei werden die Koordinaten der Aenderung
                 * und der neue Spielstein an der Stelle mitgegeben.
                 */
                fireSpielsteinChanged(x, y, newStein);
            }
        }
    }

    /**
     * Benachrichtigt alle Listener dieses Spielsfelds ueber den neuen
     * Spielstein an der angegebenen Koordinate. Implementation ist glaube ich
     * aus JComponent oder Component kopiert.
     * 
     * @param newStein
     *            - Der neue Stein, der an die Listener mitgeteilt wird.
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
     * Fuegt den angegebenen Listener zu der Liste hinzu.
     * 
     * @param listener
     *            ISpielfeldListener
     */
    public void addSpielfeldListener(final ISpielfeldListener listener) {
        listeners.add(ISpielfeldListener.class, listener);
    }

    /**
     * Entfernt den uebergebenen Listener von der Liste.
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
                    resourceManager.getText("backend.spiel.given.coordinate") + " X:" + x + " Y:" + y
                            + resourceManager.getText("backend.spiel.outside.playing.field"));
        }
    }

    /**
     * Gibt eine Liste mit den fuer diese Koordinate aktuell setzbaren
     * Spielsteinen zurueck. Die fuer dieses Spielfeldelement aktuell setzbaren
     * Spielsteine haengen vom SpielModus ab.
     * 
     * @return Eine Liste mit den fuer diese Koordinate aktuell auswaehlbaren
     *         Spielsteinen.
     */
    public List<? extends Spielstein> listAvailableStates(final int x,
            final int y) {

        if (isEditierModus()) {
            /**
             * Im Editiermodus kann KeinStein, Stern und die verschiedenen
             * Pfeile gesetzt werden.
             */
            final List<Spielstein> editierModusList = new ArrayList<Spielstein>(
                    11);
            editierModusList.add(new KeinStein());
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
                /** Gebe leere Liste zurueck */
                return new ArrayList<Spielstein>();
            } else {
                /**
                 * Ansonsten kann man im Spielmodus KeinStein setzen, Ausschluss
                 * und Stern raten.
                 */
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
     * Convenience Methode. Holt von IGotSpielstein den aktuell eingestellten
     * Spielmodus. Gibt true zurueck, wenn der Spielmodus EDITIEREN ist.
     * 
     * @return True, wenn der Spielmodus EDITIEREN ist.
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
     * @return Fehler vorhanden?
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
