package severeLobster.backend.spiel;

import infrastructure.constants.enums.SchwierigkeitsgradEnumeration;

import java.io.Serializable;

/**
 * Spielfeld eines Spiels - Besteht aus einem 2Dimensionalem-Spielstein Koordinatensysten
 *
 * @author Lars Schlegelmilch
 */
public class Spielfeld implements Serializable {

    private Spielstein[][] koordinaten;

    public Spielfeld() {
    }

    public Spielfeld(int laenge, int breite) {
        setGroesse(laenge, breite);
    }

    /**
     * Bestimmt Anhang der der Groesse des Spielfeldes und der Anzahl an Spielsteinen einen Schwierigkeitsgrad
     *
     * @return Schwierigkeitsgrad des Spielfeldes
     */
    public SchwierigkeitsgradEnumeration getSchwierigkeitsgrad() {
        //TODO Anhand von Groesse und Spielsteinen einen Schwierigkeitsgrad ermitteln
        return SchwierigkeitsgradEnumeration.LEICHT;
    }

    public Spielstein getSpielstein(int x, int y) {
        return koordinaten[x][y];
    }

    public void setSpielstein(int x, int y, Spielstein spielstein) {
        koordinaten[x][y] = spielstein;
    }

    public void setGroesse(int laenge, int breite) {
        koordinaten = new Spielstein[laenge][breite];
    }
}
