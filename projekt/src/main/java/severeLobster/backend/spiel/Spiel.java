package severeLobster.backend.spiel;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import infrastructure.constants.GlobaleKonstanten;
import infrastructure.constants.enums.SchwierigkeitsgradEnumeration;
import infrastructure.constants.enums.SpielmodusEnumeration;
import severeLobster.backend.command.Aktion;

import javax.swing.event.EventListenerList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;

/**
 * Spiel - Besteht aus einem Spielfeld von Spielsteinen. Stellt ein laufendes
 * Spiel dar und beinhaltet einen aktuellen Spielstand, der gespeichert und
 * geladen werden kann. Instanzen dieser Klasse sind in ihrem Zustand komplett
 * unabhaengig voneinander.
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber, Paul Bruell
 */
public class Spiel implements IGotSpielModus {

    private final EventListenerList listeners = new EventListenerList();
    /** Spielfeld wird vom Spiel erstellt oder geladen. */
    private Spielfeld currentSpielfeld;
    private SpielmodusEnumeration spielmodus = SpielmodusEnumeration.SPIELEN;
    private final ISpielfeldListener innerSpielfeldListener = new InnerSpielfeldListener();
    private String saveName;
    /** Tracking: */
    private Stack<Aktion> spielZuege;
    private Stack<Integer> trackingPunkte;
    private int letzterFehlerfreierSpielzug;

    /**
     * Default constructor. Nach dem erstellen ist man im Spielmodus.Spielen.
     * Spielfeld wird mit Standardfeld initialisiert.
     */
    public Spiel() {
        this(SpielmodusEnumeration.SPIELEN);
    }

    /**
     * Spielfeld wird mit Standardfeld initialisiert.
     * 
     * @param spielmodus
     *            Spielmodus des Spiels
     */
    public Spiel(SpielmodusEnumeration spielmodus) {

        this.spielmodus = spielmodus;
        this.currentSpielfeld = new Spielfeld(this, 10, 10);
        currentSpielfeld.addSpielfeldListener(innerSpielfeldListener);
        spielZuege = new Stack<Aktion>();
        trackingPunkte = new Stack<Integer>();
        letzterFehlerfreierSpielzug = 0;
    }

    /**
     * Spielstein setzen.
     * 
     * @param x
     *            X-Achsenwert
     * @param y
     *            Y-Achsenwert
     * @param spielstein
     *            zu setzender Spielstein
     * @return ob spielfeld fehler enthaelt.
     */
    public boolean setSpielstein(int x, int y, Spielstein spielstein) {
        currentSpielfeld.setSpielstein(x, y, spielstein);
        return hasErrors();
    }

    public Spielstein getSpielstein(int x, int y) {
        return currentSpielfeld.getSpielstein(x, y);
    }

    /**
     * Gibt den Schwierigkeitsgrad des Spielfeldes zurueck
     * 
     * @return Schwierigkeitsgrad
     */
    public SchwierigkeitsgradEnumeration getSchwierigkeitsgrad() {
        return getSpielfeld().getSchwierigkeitsgrad();
    }

    /**
     * Es darf nicht moeglich sein von aussen ein Spielfeld zu setzen, da bei
     * dem neuen Spielfeld der SPielmodus vom alten Spiel abhaengen wuerde,
     * durch das das Spielfeld erstellt wurde (IGotSpielmodus). Auf Aenderungen
     * beim Spielmodus in dieser Instanz wuerde das Spielfeld entsprechend nicht
     * reagieren.
     * 
     */
    // public void setSpielfeld(Spielfeld spielfeld) {
    // currentSpielfeld = spielfeld;
    // }

    public Spielfeld getSpielfeld() {
        return currentSpielfeld;
    }

    /***
     * Setzt ein neues, leeres Spielfeld fuer dieses Spiel. Benachrichtigt
     * listener dieser Instanz ueber spielfeldChanged().
     * 
     * @param x
     *            Laenge der x-Achse
     * @param y
     *            Laenge der y-Achse
     */
    public void initializeNewSpielfeld(final int x, final int y) {
        final Spielfeld listeningSpielfeld = getSpielfeld();
        if (null != listeningSpielfeld) {
            listeningSpielfeld.removeSpielfeldListener(innerSpielfeldListener);
        }
        final Spielfeld newSpielfeld = new Spielfeld(this, x, y);
        newSpielfeld.addSpielfeldListener(innerSpielfeldListener);
        this.currentSpielfeld = newSpielfeld;
        fireSpielfeldChanged(currentSpielfeld);
    }

    // Implementiert IGotSpielModus.
    @Override
    public SpielmodusEnumeration getSpielmodus() {
        return spielmodus;
    }

    /**
     * Speichert das aktuelle Spiel
     * 
     * @param spielname
     *            Name der Datei (ohne Dateiendung)
     * @throws IOException
     *             Exception falls Datei nicht speicherbar
     */
    public void saveSpiel(String spielname) throws IOException {
        XStream xstream = new XStream(new DomDriver());
        String dateiendung = "." + getDateiendung(getSpielmodus());
        File verzeichnis = new File(getVerzeichnis(getSpielmodus()), spielname
                + dateiendung);
        OutputStream outputStream = new FileOutputStream(verzeichnis);
        xstream.toXML(this, outputStream);
        outputStream.close();
    }

    /**
     * Laed ein Spiel aus .sav / .puz - Dateien
     * 
     * @param spielname
     *            Name der Datei (ohne Dateiendung)
     * @throws IOException
     *             Exception falls Datei nicht lesbar
     */
    public static Spiel loadSpiel(String spielname,
            SpielmodusEnumeration spielmodus) throws IOException {
        XStream xstream = new XStream(new DomDriver());
        String dateiendung = "." + getDateiendung(spielmodus);
        File verzeichnis = new File(getVerzeichnis(spielmodus), spielname
                + dateiendung);
        InputStream inputStream = new FileInputStream(verzeichnis);
        Spiel spiel = (Spiel) xstream.fromXML(inputStream);
        inputStream.close();

        return spiel;
    }

    /**
     * Laed ein erstelles Puzzle und aendert den Modus in den Spielmodus.
     * 
     * @param spielname
     *            Name der Datei (ohne Dateiendung)
     * @return Erstelltes Spiel im Spielmodus
     * @throws IOException
     *             Wirft Exception, wenn Datei nicht vorhanden
     */
    public static Spiel newSpiel(String spielname) throws IOException {
        Spiel neuesSpiel = loadSpiel(spielname, SpielmodusEnumeration.EDITIEREN);
        neuesSpiel.setSpielmodus(SpielmodusEnumeration.SPIELEN);

        return neuesSpiel;
    }

    /**
     * Ueberprueft ob das Spielfeld geloest wurde (Sieg).
     * 
     * @return sieg
     */
    public boolean isSolved() {
        /***
         * Methode in Spielfeld verschoben, um Spielfeld besser kapseln zu
         * koennen.
         */
        return currentSpielfeld.isSolved();
    }

    /**
     * Ueberprueft ob Fehler in einem Spielfeld vorhanden sind, d.h. Tipps
     * abgegeben wurden, die nicht der Loesung entsprechen
     * 
     * @return fehler vorhanden
     */
    public boolean hasErrors() {
        /**
         * Methode in Spielfeld verschoben, um Spielfeld besser kapseln zu
         * koennen.
         */
        // TODO checken ob es einen unterschied macht ob man im edit oder
        // spielmodus ist wenn man nach fehlern prueft
        return currentSpielfeld.hasErrors();
    }

    /**
     * Setzt den Spielmodus des aktuellen Spiels
     * 
     * @param spielmodus
     *            Spielmodus des Spiels
     */
    public void setSpielmodus(final SpielmodusEnumeration spielmodus) {
        if (null != spielmodus) {

            this.spielmodus = spielmodus;
            fireSpielmodusChanged(spielmodus);
        }
    }

    private void fireSpielsteinChanged(final Spielfeld spielfeld, final int x,
            final int y, Spielstein newStein) {

        /** Gibt ein Array zurueck, das nicht null ist */
        final Object[] currentListeners = listeners.getListenerList();
        for (int i = currentListeners.length - 2; i >= 0; i -= 2) {
            if (currentListeners[i] == ISpielListener.class) {
                ((ISpielListener) currentListeners[i + 1]).spielsteinChanged(
                        this, spielfeld, x, y, newStein);
            }
        }
    }

    /**
     * Benachrichtigt alle Listener dieses Spiel ueber einen neuen Wert an den
     * uebergeben Koordinaten. Implementation ist glaube ich aus JComponent oder
     * Component kopiert.
     * 
     * @param newSpielfeld
     *            - Der neue Status, der an die Listener mitgeteilt wird.
     */
    private void fireSpielfeldChanged(Spielfeld newSpielfeld) {

        /** Gibt ein Array zurueck, das nicht null ist */
        final Object[] currentListeners = listeners.getListenerList();
        for (int i = currentListeners.length - 2; i >= 0; i -= 2) {
            if (currentListeners[i] == ISpielListener.class) {
                ((ISpielListener) currentListeners[i + 1]).spielfeldChanged(
                        this, newSpielfeld);
            }
        }
    }

    private void fireSpielmodusChanged(final SpielmodusEnumeration newSpielmodus) {

        /** Gibt ein Array zurueck, das nicht null ist */
        final Object[] currentListeners = listeners.getListenerList();
        for (int i = currentListeners.length - 2; i >= 0; i -= 2) {
            if (currentListeners[i] == ISpielListener.class) {
                ((ISpielListener) currentListeners[i + 1]).spielmodusChanged(
                        this, newSpielmodus);
            }
        }
    }

    /**
     * Fuegt listener zu der Liste hinzu.
     * 
     * @param listener
     *            ISpielfeldListener
     */
    public void addSpielListener(final ISpielListener listener) {
        listeners.add(ISpielListener.class, listener);
    }

    /**
     * Entfernt listener von der Liste.
     * 
     * @param listener
     *            ISpielsteinListener
     */
    public void removeSpielListener(final ISpielListener listener) {
        listeners.remove(ISpielListener.class, listener);
    }

    /**
     * Gibt die Dateiendung eines zu ladenen oder zu sichernden Spiels bzw.
     * Puzzles anhand des Spielmodus zurück
     * 
     * @param spielmodus
     *            Spielmodus des Spiels
     * @return Dateiendung (.puz oder .sav)
     */
    private static String getDateiendung(SpielmodusEnumeration spielmodus) {
        // Preconditions.checkNotNull(spielmodus);
        switch (spielmodus) {
        case SPIELEN:
            return GlobaleKonstanten.SPIELSTAND_DATEITYP;
        case EDITIEREN:
            return GlobaleKonstanten.PUZZLE_DATEITYP;
        }
        return null;
    }

    /**
     * Gibt das Verzeichnis der puz/sav Dateien je nach Spielmodus zurueck
     * 
     * @param spielmodus
     *            Spielmodus des Spiels
     * @return Verzeichnis fuer puz/sav Dateien
     */
    private static File getVerzeichnis(SpielmodusEnumeration spielmodus) {
        switch (spielmodus) {
        case SPIELEN:
            return GlobaleKonstanten.DEFAULT_SPIEL_SAVE_DIR;
        case EDITIEREN:
            return GlobaleKonstanten.DEFAULT_PUZZLE_SAVE_DIR;
        }
        return null;
    }

    /**
     * Gibt den Namen des SaveGames zum Spiel zurueck
     * 
     * @return Spielname
     */
    public String getSaveName() {
        return saveName;
    }

    /**
     * Setzt den Names des SaveGames
     * 
     * @param saveName
     *            Name des SaveGames
     */
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public Stack<Aktion> getSpielZuege() {
        return spielZuege;
    }

    public Stack<Integer> getTrackingPunkte() {
        return trackingPunkte;
    }

    public int getLetzterFehlerfreierSpielzug() {
        return letzterFehlerfreierSpielzug;
    }

    public void setLetzterFehlerfreierSpielzug(int letzterFehlerfreierSpielzug) {
        this.letzterFehlerfreierSpielzug = letzterFehlerfreierSpielzug;
    }

    private class InnerSpielfeldListener implements ISpielfeldListener {

        @Override
        public void spielsteinChanged(Spielfeld spielfeld, int x, int y,
                Spielstein changedStein) {
            fireSpielsteinChanged(spielfeld, x, y, changedStein);
        }
    }

}
