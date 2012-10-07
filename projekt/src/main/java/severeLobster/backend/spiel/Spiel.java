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

    public int getAnzahlZuege() {
        return anzahlZuege;
    }

    private int anzahlZuege;

    /**
     * Ein Spiel hat ein Spielfeld und einen Modus
     *
     * @param spielfeld  Spielfeld des Spiels
     * @param spielmodus Spielmodus des Spiels - Wird das Spiel gerade erstellt oder gespielt?
     */
    public Spiel(Spielfeld spielfeld, SpielmodusEnumeration spielmodus) {
        anzahlZuege = 0;
        this.spielfeld = spielfeld;
        this.spielmodus = spielmodus;
    }

    /**
     * Tippt einen Spielstein in einem Koordinatensystem
     *
     * @param x              X-Achsenwert auf dem sich der Spielstein befinden soll
     * @param y              Y-Achsenwert auf dem sich der Spielstein befinden soll
     * @param spielsteinTipp Spielstein, der getippt wird.
     * @return Ist der Tipp richtig?
     */
    public boolean spielsteinTippen(int x, int y, Spielstein spielsteinTipp) {
        Spielstein spielfeldSpielstein = spielfeld.getSpielstein(x, y);

        return true; //TODO Tipp überprüfen
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
     * @param spielname Name der Datei (ohne .sav-Endung)
     */
    public void save(String spielname) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(spielname + GlobaleKonstanten.SPIELSTAND_DATEITYP);
            ObjectOutputStream o = new ObjectOutputStream(outputStream);
            o.writeObject(this);
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
     * Läd ein Spiel aus .sav-Dateien
     *
     * @param spielname Name der Datei (ohne .sav-Endung)
     */
    public static Spiel load(String spielname) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(spielname + GlobaleKonstanten.SPIELSTAND_DATEITYP);
            ObjectInputStream o = new ObjectInputStream(inputStream);

            return (Spiel) o.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
