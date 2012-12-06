package severeLobster.backend.spiel;

import infrastructure.components.FTPConnector;
import infrastructure.components.AudioPlayer;
import infrastructure.constants.GlobaleKonstanten;
import infrastructure.constants.enums.SpielmodusEnumeration;
import infrastructure.exceptions.LoesungswegNichtEindeutigException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.event.EventListenerList;

import severeLobster.backend.command.PrimaerAktion;
import severeLobster.frontend.application.MainFrame;
import severeLobster.frontend.dialogs.GewonnenDialog;
import severeLobster.frontend.view.MainView;

/**
 * Schnittstelle zwischen Backendlogik und Frontenddarstellung. Logik und
 * Informationen, die von der GUI aufgerufen bzw angezeigt werden, sind ueber
 * diese Klasse zugaengich sein - Direkt oder gekapselt. Instanz benachrichtigt
 * angemeldete ISternenSpielApplicationBackendListener, wenn sich irgendetwas am
 * Zustand der Anwendung aendert. Es gibt von dieser Klasse nur eine Instanz
 * (Singleton Pattern).
 * 
 * @author Lutz Kleiber, Paul Bruell, Lars Schlegelmilch
 * 
 */
public class SternenSpielApplicationBackend {

    /** Einzige Instanz */
    private static final SternenSpielApplicationBackend UNIQUE_INSTANCE = new SternenSpielApplicationBackend();
    private final EventListenerList listeners = new EventListenerList();
    private final ISpielListener innerSpielListener = new InnerSpielListener();
    private Spiel currentlyPlayedSpiel;
    private boolean zeigeFadenkreuz;

    private SternenSpielApplicationBackend() {
        this.currentlyPlayedSpiel = new Spiel();
        currentlyPlayedSpiel.addSpielListener(innerSpielListener);
    }

    public static SternenSpielApplicationBackend getInstance() {
        return UNIQUE_INSTANCE;
    }

    public Spiel getSpiel() {
        return this.currentlyPlayedSpiel;
    }

    public void setzeTrackingPunkt() {
        ActionHistoryObject current = currentlyPlayedSpiel.getSpielZuege()
                .getCurrent();
        current.setzeTrackingPunktNachDiesemZug(true);
        currentlyPlayedSpiel.getTrackingPunkte().push(current);
    }

    public void zurueckZumLetztenFehlerfreienSpielzug() {
        currentlyPlayedSpiel.getSpielZuege().zurueckZuLetztemFehlerfreiemZug();
    }

    public void zurueckZumLetztenTrackingPunkt() {
        currentlyPlayedSpiel.getSpielZuege().zurueckZuLetztemCheckpoint();
        if (currentlyPlayedSpiel.getTrackingPunkte().size() > 0)
            currentlyPlayedSpiel.getTrackingPunkte().pop()
                    .setzeTrackingPunktNachDiesemZug(false);
    }

    public void entferneAlleTrackingPunkte() {
        currentlyPlayedSpiel.entferneAlleTrackingPunkte();
    }

    public void aendereSpielfeldGroesse(int x, int y) {
        getSpiel().aendereSpielfeldGroesse(x, y);
    }

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
        boolean fehler;
        PrimaerAktion spielZug = new PrimaerAktion(getSpiel());
        fehler = spielZug.execute(x, y, spielstein);
        currentlyPlayedSpiel.getSpielZuege().neuerSpielzug(spielZug, fehler);
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

    public void startNewSpielFrom(final String spielname) throws IOException,
            LoesungswegNichtEindeutigException {
        final Spiel newGame = Spiel.newSpiel(spielname);
        setSpiel(newGame);
    }

    public void puzzleFreigeben(String spielname)
            throws LoesungswegNichtEindeutigException, IOException {
        getSpiel().gebeSpielFrei(spielname);
    }

    public void uploadPuzzle(String spielname) {
        try {
            if (MainView.ftpConnector.isOnline()) {
                MainView.ftpConnector
                        .upload(GlobaleKonstanten.DEFAULT_FREIGEGEBENE_PUZZLE_SAVE_DIR
                                + "\\"
                                + spielname
                                + "."
                                + GlobaleKonstanten.PUZZLE_DATEITYP,
                                spielname
                                        + "-"
                                        + getSpiel().getSchwierigkeitsgrad()
                                        + "-"
                                        + (getSpiel().getSpielfeld()
                                                .getBreite() * getSpiel()
                                                .getSpielfeld().getHoehe())
                                        + "-" + System.getProperty("user.name")
                                        + "-."
                                        + GlobaleKonstanten.PUZZLE_DATEITYP);
                MainView.ftpConnector.updateFiles();
            }

        } catch (Exception e) {
            System.out.println("Fehler beim Upload " + e.toString());
        }
    }

    public void loadSpielFrom(final String spielname) throws IOException {
        final Spiel loadedSpiel = Spiel.loadSpiel(spielname,
                SpielmodusEnumeration.SPIELEN);
        setSpiel(loadedSpiel);
    }

    public void saveCurrentSpielTo(final String spielname) throws IOException {
        getSpiel().saveSpiel(spielname);
    }

    public void loadPuzzleFrom(final String puzzlename) throws IOException {
        final Spiel loadedPuzzle = Spiel.loadSpiel(puzzlename,
                SpielmodusEnumeration.EDITIEREN);
        setSpiel(loadedPuzzle);
    }

    public void saveCurrentPuzzleTo(final String puzzlename) throws IOException {
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
                    try {
                        AudioPlayer.playWinSound();
                    } catch (Exception e) {
                        System.out.println("Sound wird überbewertet");
                        System.out.println(e.toString());
                    }
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

    public void setFadenkreuzAktiviert(boolean newValue) {
        this.zeigeFadenkreuz = newValue;
    }

    public boolean istFadenkreuzAktiviert() {
        return this.zeigeFadenkreuz;
    }

}
