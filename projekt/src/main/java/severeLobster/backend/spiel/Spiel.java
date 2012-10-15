package severeLobster.backend.spiel;

import infrastructure.constants.GlobaleKonstanten;
import infrastructure.constants.enums.SchwierigkeitsgradEnumeration;
import infrastructure.constants.enums.SpielmodusEnumeration;

import javax.swing.event.EventListenerList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Spiel - Besteht aus einem Spielfeld von Spielsteinen. Stellt ein laufendes
 * Spiel dar und beinhaltet einen aktuellen Spielstand, der gespeichert und
 * geladen werden kann. Instanzen dieser Klasse sind in ihrem Zustand komplett
 * unabhaengig voneinander.
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber, Paul Bruell
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
     * @param spielmodus Spielmodus des Spiels
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
     * Zwischenloesung um eine Primaeraktion auszufuehren.
     * 
     * @param x X-Achsenwert
     * @param y Y-Achsenwert
     */
    public void primaerAktion(int x, int y) {
        currentSpielfeld.setSpielstein(x, y, Stern.getInstance());
        if (spielmodus == SpielmodusEnumeration.SPIELEN) {
            if (hasErrors()) {
                System.out.println("Die Loesung enthaellt Fehler.");
            }
        }
    }

    /**
     * Zwischenloesung um eine Sekundaeraktion auszufuehren.
     * 
     * @param x x-Achsenwert
     * @param y y-Achsenwer
     */
    public void sekundaerAktion(int x, int y) {
        if (spielmodus == SpielmodusEnumeration.SPIELEN) {
            currentSpielfeld.setSpielstein(x, y, Ausschluss.getInstance());
            if (hasErrors()) {
                System.out.println("Die Loesung enthaellt Fehler.");
            }
        } else {
            currentSpielfeld.setSpielstein(x, y, Pfeil.getNordPfeil());
        }
    }

    /**
     * Gibt den Schwierigkeitsgrad des Spielfeldes zurueck
     * @return Schwierigkeitsgrad
     */
    public SchwierigkeitsgradEnumeration getSchwierigkeitsgrad() {
        return getSpielfeld().getSchwierigkeitsgrad();
    }

    public void setSpielfeld(Spielfeld spielfeld) {
        currentSpielfeld = spielfeld;
    }

    public Spielfeld getSpielfeld() {
        return currentSpielfeld;
    }

    /***
     * Setzt ein neues, leeres Spielfeld fuer dieses Spiel. Benachrichtigt
     * listener dieser Instanz ueber spielfeldChanged().
     * 
     * @param x Laenge der x-Achse
     * @param y Laenge der y-Achse
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
     * @param spielname
     *            Name der Datei (ohne Datei-Endung)
     */
    public void save(String spielname) {
        String dateiendung = getDateiendung(getSpielmodus());
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(spielname + dateiendung);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    outputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Laedt ein Spiel aus .sav-Dateien
     * 
     * @param spielname
     *            Name der Datei (ohne Dateiendung)
     */
    public static Spiel load(String spielname, SpielmodusEnumeration spielmodus) throws IOException {
        String dateiendung = getDateiendung(spielmodus);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(spielname + dateiendung);
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    inputStream);

            return (Spiel) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException();
        } finally {
            if (inputStream != null) {
                inputStream.close();
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
     * Gibt die Dateiendung eines zu ladenen
     * oder zu sichernden Spiels bzw. Puzzles
     * anhand des Spielmodus zurück
     *
     * @param spielmodus Spielmodus des Spiels
     * @return Dateiendung (.psav oder .sav)
     */
    private static String getDateiendung(SpielmodusEnumeration spielmodus) {
        switch (spielmodus) {
            case SPIELEN:
                return GlobaleKonstanten.SPIELSTAND_DATEITYP;
            case EDITIEREN:
                return GlobaleKonstanten.PUZZLE_ERSTELLEN_DATEITYP;
        }
        return null;
    }

    private class InnerSpielfeldListener implements ISpielfeldListener {

        @Override
        public void spielsteinChanged(Spielfeld spielfeld, int x, int y,
                Spielstein changedStein) {
            fireSpielsteinChanged(spielfeld, x, y, changedStein);
        }
    }

}
