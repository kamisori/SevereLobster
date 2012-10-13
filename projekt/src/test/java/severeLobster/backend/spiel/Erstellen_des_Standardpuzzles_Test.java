package severeLobster.backend.spiel;

import static org.junit.Assert.assertTrue;
import infrastructure.constants.GlobaleKonstanten;
import infrastructure.constants.enums.SpielmodusEnumeration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Dieser Unittest erstellt ein Standardpuzzle, welches spielbar ist und eine
 * eindeutige Loesung hat.
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public class Erstellen_des_Standardpuzzles_Test {

    private Spiel spiel;

    private Spielfeld spielfeld;

    private void spielsteineSetzen() {
        spielfeld.setSpielstein(0, 1, Stern.getInstance());

        spielfeld.setSpielstein(0, 2, Pfeil.getSuedPfeil());

        spielfeld.setSpielstein(0, 4, Pfeil.getOstPfeil());

        spielfeld.setSpielstein(0, 5, Pfeil.getOstPfeil());

        spielfeld.setSpielstein(1, 0, Pfeil.getNordOstPfeil());

        spielfeld.setSpielstein(1, 2, Stern.getInstance());

        spielfeld.setSpielstein(1, 3, Stern.getInstance());

        spielfeld.setSpielstein(1, 4, Stern.getInstance());

        spielfeld.setSpielstein(1, 5, Stern.getInstance());

        spielfeld.setSpielstein(2, 0, Stern.getInstance());

        spielfeld.setSpielstein(2, 1, Pfeil.getOstPfeil());

        spielfeld.setSpielstein(2, 2, Pfeil.getNordWestPfeil());

        spielfeld.setSpielstein(3, 0, Pfeil.getNordWestPfeil());

        spielfeld.setSpielstein(3, 1, Stern.getInstance());

        spielfeld.setSpielstein(3, 2, Stern.getInstance());

        spielfeld.setSpielstein(4, 1, Pfeil.getNordWestPfeil());

        spielfeld.setSpielstein(4, 5, Stern.getInstance());

        spielfeld.setSpielstein(5, 1, Stern.getInstance());

        spielfeld.setSpielstein(5, 3, Pfeil.getSuedWestPfeil());

        spielfeld.setSpielstein(5, 5, Pfeil.getSuedPfeil());
    }

    @Before
    public void setUp() {
        spiel = new Spiel(SpielmodusEnumeration.EDITIEREN);
        spiel.initializeNewSpielfeld(6, 6);
        spielfeld = spiel.getSpielfeld();
        spielsteineSetzen();

    }

    @Test
    public void sichern_des_standardspiels_ist_erfolgreich()
            throws FileNotFoundException, IOException {
        spiel.save("Standardspiel01");
        File file = new File("Standardspiel01"
                + GlobaleKonstanten.SPIELSTAND_DATEITYP);

        assertTrue(file.exists());
    }
}
