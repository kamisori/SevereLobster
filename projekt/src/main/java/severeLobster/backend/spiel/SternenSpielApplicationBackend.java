package severeLobster.backend.spiel;

import infrastructure.constants.enums.SpielmodusEnumeration;

import javax.swing.event.EventListenerList;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    public SternenSpielApplicationBackend() {
        this.currentlyPlayedSpiel = new Spiel();
        currentlyPlayedSpiel.addSpielListener(innerSpielListener);
    }

    public Spiel getSpiel() {
        return this.currentlyPlayedSpiel;
    }

    public void loadSpielFrom(final String spielname)
            throws FileNotFoundException, IOException {
        final Spiel loadedSpiel = Spiel.load(spielname, SpielmodusEnumeration.SPIELEN);
        setSpiel(loadedSpiel);
    }

    public void saveCurrentSpielTo(final String spielname)
            throws FileNotFoundException, IOException {
        getSpiel().save(spielname);
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
