package severeLobster.backend.spiel;

import infrastructure.constants.enums.SpielmodusEnumeration;

import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;

import severeLobster.backend.command.Aktion;
import severeLobster.backend.command.PrimaerAktion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

/**
 * Schnittstelle zwischen Backendlogik und Frontenddarstellung. Logik und
 * Informationen, die von der GUI aufgerufen bzw angezeigt werden, sind ueber
 * diese Klasse zugaengich sein - Direkt oder gekapselt. Instanz benachrichtigt
 * angemeldete ISternenSpielApplicationBackendListener, wenn sich irgendetwas am
 * Zustand der Anwendung aendert.
 * 
 * @author Lutz Kleiber
 * 
 */
public class SternenSpielApplicationBackend {

    private final EventListenerList listeners = new EventListenerList();
    private final ISpielListener innerSpielListener = new InnerSpielListener();
    private Spiel currentlyPlayedSpiel;
    /** Tracking: */
    private Stack<Aktion> spielZuege;
    private Stack<Integer> trackingPunkte;
    private int letzterFehlerfreierSpielzug;

    public SternenSpielApplicationBackend() {
        this.currentlyPlayedSpiel = new Spiel();
        currentlyPlayedSpiel.addSpielListener(innerSpielListener);
        spielZuege = new Stack<Aktion>();
        trackingPunkte = new Stack<Integer>();
        letzterFehlerfreierSpielzug = 0;
    }

    public Spiel getSpiel() {
        return this.currentlyPlayedSpiel;
    }

    public void setzeTrackingPunkt() {
        trackingPunkte.push(spielZuege.size());
    }

    private void nimmSpielzugZurueck() {
        spielZuege.pop().undo();
    }

    public void zurueckZumLetztenFehlerfreienSpielzug() {
        while (spielZuege.size() > letzterFehlerfreierSpielzug) {
            nimmSpielzugZurueck();
        }
    }

    public void zurueckZumLetztenTrackingPunkt() {
        int trackingPunkt = trackingPunkte.pop();
        while (spielZuege.size() > trackingPunkt) {
            nimmSpielzugZurueck();
        }
    }

    /**
     * NEUE SCHNITTSTELLE, UM DAS SPIELFELD NICHT KOMPLETT NACH AUSSEN SICHTBAR
     * MACHEN ZU MUESSEN UND DAS TRACKING HIER ODER IN SPIEL MACHEN ZU KOENNEN.
     * 
     * 
     * ANFANG
     * 
     */

    /***
     * Setzt beim aktuellen Spielfeld einen Stein. Verhalten ist nach aussen so
     * wie: Spielfeld.setSpielstein().
     * 
     * @param x
     * @param y
     * @param spielstein
     */
    public void setSpielstein(final int x, final int y,
            final Spielstein spielstein) {
        PrimaerAktion spielZug = new PrimaerAktion(getSpiel());
        spielZuege.push(spielZug);
        if (!spielZug.execute(x, y, spielstein)) {
            letzterFehlerfreierSpielzug = spielZuege.size();
        }
    }

    /***
     * Gibt vom aktuellen Spielfeld den Spielstein an der Stelle.
     * 
     * @param x
     * @param y
     */
    public Spielstein getSpielstein(final int x, final int y) {
        return getSpiel().getSpielfeld().getSpielstein(x, y);
    }

    public int getSpielfeldBreite() {
        return getSpiel().getSpielfeld().getBreite();
    }

    public int getSpielfeldHoehe() {
        return getSpiel().getSpielfeld().getHoehe();
    }

    public int getCountSterneSpale(final int x) {
        return getSpiel().getSpielfeld().countSterneSpalte(x);
    }

    public int getCountSterneZeile(final int y) {
        return getSpiel().getSpielfeld().countSterneZeile(y);
    }

    public List<? extends Spielstein> listAvailableStates(int x, int y) {
        return getSpiel().getSpielfeld().listAvailableStates(x, y);
    }

    /**
     * NEUE SCHNITTSTELLE, UM DAS SPIELFELD NICHT KOMPLETT NACH AUSSEN SICHTBAR
     * MACHEN ZU MUESSEN UND DAS TRACKING HIER ODER IN SPIEL MACHEN ZU KOENNEN.
     * 
     * ENDE
     * 
     */

    public void startNewSpielFrom(final String spielname)
            throws FileNotFoundException, IOException {
        final Spiel newGame = Spiel.newGame(spielname);
        setSpiel(newGame);
    }

    public void loadSpielFrom(final String spielname)
            throws FileNotFoundException, IOException {
        final Spiel loadedSpiel = Spiel.loadSpiel(spielname,
                SpielmodusEnumeration.SPIELEN);
        setSpiel(loadedSpiel);
    }

    public void saveCurrentSpielTo(final String spielname)
            throws FileNotFoundException, IOException {
        getSpiel().saveSpiel(spielname);
    }

    public void setSpiel(final Spiel spiel) {
        final Spiel currentlyListenedSpiel = getSpiel();
        if (null != currentlyListenedSpiel) {
            currentlyListenedSpiel.removeSpielListener(innerSpielListener);
        }
        spiel.addSpielListener(innerSpielListener);
        this.currentlyPlayedSpiel = spiel;
        fireSpielChanged(spiel);
    }

    /**
     * Fuegt listener zu der Liste hinzu.
     * 
     * @param listener
     *            ISpielfeldListener
     */
    public void addApplicationBackendListener(
            final ISternenSpielApplicationBackendListener listener) {
        listeners.add(ISternenSpielApplicationBackendListener.class, listener);
    }

    /**
     * Entfernt listener von der Liste.
     * 
     * @param listener
     *            ISpielsteinListener
     */
    public void removeApplicationBackendListener(
            final ISternenSpielApplicationBackendListener listener) {
        listeners.remove(ISternenSpielApplicationBackendListener.class,
                listener);
    }

    private void fireSpielChanged(Spiel spiel) {
        /** Gibt ein Array zurueck, das nicht null ist */
        final Object[] currentListeners = listeners.getListenerList();
        for (int i = currentListeners.length - 2; i >= 0; i -= 2) {
            if (currentListeners[i] == ISternenSpielApplicationBackendListener.class) {
                ((ISternenSpielApplicationBackendListener) currentListeners[i + 1])
                        .spielChanged(this, spiel);
            }
        }
    }

    private void fireSpielmodusChanged(final Spiel spiel,
            final SpielmodusEnumeration newSpielmodus) {

        /** Gibt ein Array zurueck, das nicht null ist */
        final Object[] currentListeners = listeners.getListenerList();
        for (int i = currentListeners.length - 2; i >= 0; i -= 2) {
            if (currentListeners[i] == ISternenSpielApplicationBackendListener.class) {
                ((ISternenSpielApplicationBackendListener) currentListeners[i + 1])
                        .spielmodusChanged(this, spiel, newSpielmodus);
            }
        }
    }

    private void fireSpielsteinChanged(final Spiel spiel,
            final Spielfeld spielfeld, final int x, final int y,
            Spielstein newStein) {

        /** Gibt ein Array zurueck, das nicht null ist */
        final Object[] currentListeners = listeners.getListenerList();
        for (int i = currentListeners.length - 2; i >= 0; i -= 2) {
            if (currentListeners[i] == ISternenSpielApplicationBackendListener.class) {
                ((ISternenSpielApplicationBackendListener) currentListeners[i + 1])
                        .spielsteinChanged(this, spiel, spielfeld, x, y,
                                newStein);
            }
        }
    }

    private void fireSpielfeldChanged(final Spiel spiel,
            final Spielfeld newSpielfeld) {

        /** Gibt ein Array zurueck, das nicht null ist */
        final Object[] currentListeners = listeners.getListenerList();
        for (int i = currentListeners.length - 2; i >= 0; i -= 2) {
            if (currentListeners[i] == ISternenSpielApplicationBackendListener.class) {
                ((ISternenSpielApplicationBackendListener) currentListeners[i + 1])
                        .spielfeldChanged(this, spiel, newSpielfeld);
            }
        }
    }

    /**
     * Zur Weiterleitung.
     * 
     * @author Lutz Kleiber
     * 
     */
    private class InnerSpielListener implements ISpielListener {

        @Override
        public void spielsteinChanged(Spiel spiel, Spielfeld spielfeld, int x,
                int y, Spielstein newStein) {
            fireSpielsteinChanged(spiel, spielfeld, x, y, newStein);
            if (spiel.isSolved()) {
                JOptionPane.showMessageDialog(null,
                        "Sie haben das Puzzle gelöst! "
                                + " Herzlichen Glückwunsch!", "Puzzle gelöst!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

        @Override
        public void spielfeldChanged(Spiel spiel, Spielfeld newSpielfeld) {
            fireSpielfeldChanged(spiel, newSpielfeld);
        }

        @Override
        public void spielmodusChanged(Spiel spiel,
                SpielmodusEnumeration newSpielmodus) {
            fireSpielmodusChanged(spiel, newSpielmodus);
        }

    }

}
