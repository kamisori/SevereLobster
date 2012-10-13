package severeLobster.backend.spiel;

import infrastructure.constants.GlobaleKonstanten;
import infrastructure.constants.enums.SchwierigkeitsgradEnumeration;
import infrastructure.constants.enums.SpielmodusEnumeration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.swing.event.EventListenerList;

/**
 * Spiel - Besteht aus einem Spielfeld von Spielsteinen. Stellt ein laufendes
 * Spiel dar und beinhaltet einen aktuellen Spielstand, der gespeichert und
 * geladen werden kann. Instanzen dieser Klasse sind in ihrem Zustand komplett
 * unabhaengig voneinander.
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public class Spiel implements Serializable, IGotSpielModus {

    private final EventListenerList listeners = new EventListenerList();
    /** Spielfeld wird vom Spiel erstellt oder geladen. */
    private Spielfeld currentSpielfeld;
    private SpielmodusEnumeration spielmodus = SpielmodusEnumeration.SPIELEN;
    private int anzahlZuege;
    private transient final ISpielfeldListener innerSpielfeldListener = new InnerSpielfeldListener();

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
     */
    public Spiel(SpielmodusEnumeration spielmodus) {

        this.spielmodus = spielmodus;
        this.currentSpielfeld = new Spielfeld(this, 10, 10);
        currentSpielfeld.addSpielfeldListener(innerSpielfeldListener);
        anzahlZuege = 0;
    }

    /**
     * Gibt die Anzahl an bereits versuchten Spielzuegen zurueck
     * 
     * @return Anzahl Spielzuege
     */
    public int getAnzahlZuege() {
        return anzahlZuege;
    }

    /**
     * Tippt einen Spielstein in einem Koordinatensystem
     * 
     * @param x
     *            X-Achsenwert auf dem sich der Spielstein befinden soll
     * @param y
     *            Y-Achsenwert auf dem sich der Spielstein befinden soll
     * @param spielsteinTipp
     *            Spielstein, der getippt wird.
     * @return Ist der Tipp richtig?
     */
    public boolean spielsteinTippen(int x, int y, Spielstein spielsteinTipp) {
        Spielstein spielfeldSpielstein = currentSpielfeld.getSpielstein(x, y);

        return true; // TODO Tipp ueberpruefen
    }

    public SchwierigkeitsgradEnumeration getSchwierigkeitsgrad() {
        return getSpielfeld().getSchwierigkeitsgrad();
    }

    public Spielfeld getSpielfeld() {
        return currentSpielfeld;
    }

    /***
     * Setzt ein neues, leeres Spielfeld fuer dieses Spiel. Benachrichtigt
     * listener dieser Instanz ueber spielfeldChanged().
     * 
     * @param x
     * @param y
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
     * Speichert das aktuelle Spiel als .sav-Datei
     * 
     * @param spielname
     *            Name der Datei (ohne .sav-Endung)
     * @throws FileNotFoundException
     *             , IOException
     */
    public void save(String spielname) throws FileNotFoundException,
            IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(spielname
                    + GlobaleKonstanten.SPIELSTAND_DATEITYP);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    outputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * Laedt ein Spiel aus .sav-Dateien
     * 
     * @param spielname
     *            Name der Datei (ohne .sav-Endung)
     * @throws FileNotFoundException
     *             , IOException
     */
    public static Spiel load(String spielname) throws FileNotFoundException,
            IOException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(spielname
                    + GlobaleKonstanten.SPIELSTAND_DATEITYP);
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    inputStream);

            return (Spiel) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Loaded instance is not of type Spiel");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
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
        return currentSpielfeld.hasErrors();
    }

    public void setSpielmodus(final SpielmodusEnumeration spielen) {
        if (null != spielen) {

            this.spielmodus = spielen;
            fireSpielmodusChanged(spielen);
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
     * @param newState
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

    private class InnerSpielfeldListener implements ISpielfeldListener {

        @Override
        public void spielsteinChanged(Spielfeld spielfeld, int x, int y,
                Spielstein changedStein) {
            fireSpielsteinChanged(spielfeld, x, y, changedStein);
        }
    }

}
