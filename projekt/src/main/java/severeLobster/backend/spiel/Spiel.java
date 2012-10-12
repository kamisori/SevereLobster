package severeLobster.backend.spiel;

import infrastructure.constants.GlobaleKonstanten;
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

/**
 * Spiel - Besteht aus einem Spielfeld von Spielsteinen
 * 
 * @author Lars Schlegelmilch
 */
public class Spiel implements Serializable {

    private Spielfeld spielfeld;
    private SpielmodusEnumeration spielmodus;
    private int anzahlZuege;

    /**
     * Gibt die Anzahl an bereits versuchten Spielzuegen zurueck
     * @return Anzahl Spielzuege
     */
    public int getAnzahlZuege() {
        return anzahlZuege;
    }

    /**
     * Ein Spiel hat ein Spielfeld und einen Modus
     * 
     * @param spielfeld
     *            Spielfeld des Spiels
     * @param spielmodus
     *            Spielmodus des Spiels - Wird das Spiel gerade erstellt oder
     *            gespielt?
     */
    public Spiel(Spielfeld spielfeld, SpielmodusEnumeration spielmodus) {
        anzahlZuege = 0;
        this.spielfeld = spielfeld;
        this.spielmodus = spielmodus;
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
        Spielstein spielfeldSpielstein = spielfeld.getSpielstein(x, y);

        return true; // TODO Tipp ueberpruefen
    }

    public Spielfeld getSpielfeld() {
        return spielfeld;
    }

    public SpielmodusEnumeration getSpielmodus() {
        return spielmodus;
    }

    /**
     * Speichert das aktuelle Spiel als .sav-Datei
     * 
     * @param spielname
     *            Name der Datei (ohne .sav-Endung)
     */
    public void save(String spielname) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(spielname
                    + GlobaleKonstanten.SPIELSTAND_DATEITYP);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Laedt ein Spiel aus .sav-Dateien
     * 
     * @param spielname
     *            Name der Datei (ohne .sav-Endung)
     */
    public static Spiel load(String spielname) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(spielname
                    + GlobaleKonstanten.SPIELSTAND_DATEITYP);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            return (Spiel) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ueberprueft ob das Spielfeld geloest wurde (Sieg)
     * 
     * @return sieg
     */
    public boolean isSolved() {
        for (int i = 0; i < spielfeld.getBreite(); i++) {

            for (int k = 0; k < spielfeld.getHoehe(); k++) {
                Spielstein currentItem = spielfeld.getSpielstein(i, k);
                if (currentItem.getVisibleState() instanceof Stern
                        && !(currentItem.getRealState() instanceof Stern)) {
                    // System.out.println("kein Stern, Stern getippt");
                    return false;
                } else if ((currentItem.getVisibleState() instanceof NullState || currentItem
                        .getVisibleState() instanceof Ausschluss)
                        && !(currentItem.getRealState() instanceof NullState)) {
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
        for (int i = 0; i < spielfeld.getBreite(); i++) {

            for (int k = 0; k < spielfeld.getHoehe(); k++) {
                Spielstein currentItem = spielfeld.getSpielstein(i, k);
                if (currentItem.getVisibleState() instanceof Ausschluss
                        && currentItem.getRealState() instanceof Stern) {
                    return true;
                } else if (currentItem.getVisibleState() instanceof Stern
                        && currentItem.getRealState() instanceof NullState) {
                    return true;
                }

            }
        }
        return false;
    }

}
