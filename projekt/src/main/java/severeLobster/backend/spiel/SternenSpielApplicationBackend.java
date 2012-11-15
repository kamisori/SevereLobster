package severeLobster.backend.spiel;

import infrastructure.constants.enums.SpielmodusEnumeration;
import severeLobster.backend.command.PrimaerAktion;
import severeLobster.frontend.application.MainFrame;
import severeLobster.frontend.dialogs.GewonnenDialog;

import javax.swing.event.EventListenerList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.List;

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

    public void setzeTrackingPunkt() {
        currentlyPlayedSpiel.getTrackingPunkte().push(
                currentlyPlayedSpiel.getSpielZuege().size());
    }

    private void nimmSpielzugZurueck() {
        currentlyPlayedSpiel.getSpielZuege().pop().undo();
    }

    public void zurueckZumLetztenFehlerfreienSpielzug() {

        if (currentlyPlayedSpiel.getSpielZuege().size() > currentlyPlayedSpiel
                .getLetzterFehlerfreierSpielzug()) {
            while (currentlyPlayedSpiel.getSpielZuege().size() > currentlyPlayedSpiel
                    .getLetzterFehlerfreierSpielzug()) {
                nimmSpielzugZurueck();
            }
        }

    }

    public void zurueckZumLetztenTrackingPunkt() {

        /** Try-Catch ist Quickfix fuer Emptystackexception: */
        try {
            int trackingPunkt = currentlyPlayedSpiel.getTrackingPunkte().pop();
            while (currentlyPlayedSpiel.getSpielZuege().size() > trackingPunkt) {
                nimmSpielzugZurueck();
            }
        } catch (EmptyStackException e) {
            /**
             * Wenn keine Trackingpunkte gespeichert sind, mach einfach nix.
             */
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
        currentlyPlayedSpiel.getSpielZuege().push(spielZug);
        if (!spielZug.execute(x, y, spielstein)) {
            currentlyPlayedSpiel
                    .setLetzterFehlerfreierSpielzug(currentlyPlayedSpiel
                            .getSpielZuege().size());
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
            throws IOException {
        final Spiel newGame = Spiel.newSpiel(spielname);
        setSpiel(newGame);
    }

    public void loadSpielFrom(final String spielname)
            throws IOException {
        final Spiel loadedSpiel = Spiel.loadSpiel(spielname,
                SpielmodusEnumeration.SPIELEN);
        setSpiel(loadedSpiel);
    }

    public void saveCurrentSpielTo(final String spielname)
            throws IOException {
        getSpiel().saveSpiel(spielname);
    }

    public void loadPuzzleFrom(final String puzzlename)
            throws IOException {
        final Spiel loadedPuzzle = Spiel.loadSpiel(puzzlename,
                SpielmodusEnumeration.EDITIEREN);
        setSpiel(loadedPuzzle);
    }

    public void saveCurrentPuzzleTo(final String puzzlename)
            throws IOException {
        getSpiel().saveSpiel(puzzlename);
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
            if (spiel.isSolved()
                    && spiel.getSpielmodus().equals(
                            SpielmodusEnumeration.SPIELEN)) {
                {
                    int result = GewonnenDialog.show(null,
                            spiel.getHighscore(), spiel.getSpielZeit(),
                            spiel.getAnzahlZuege());
                    if (GewonnenDialog.neues_spiel_starten
                            .equals(GewonnenDialog.options[result])) {
                        MainFrame.neuesSpielOeffnen();
                    } else if (GewonnenDialog.zurueck_zum_menue
                            .equals(GewonnenDialog.options[result])) {
                        MainFrame.mainPanel.addMenuPanel();

                    } else if (GewonnenDialog.spiel_beenden
                            .equals(GewonnenDialog.options[result])) {
                        System.exit(0);
                    }
                }
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
