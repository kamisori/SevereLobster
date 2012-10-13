package severeLobster.backend.spiel;

import infrastructure.constants.GlobaleKonstanten;
import infrastructure.constants.enums.SpielmodusEnumeration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
<<<<<<< HEAD
 * Ueberprueft, ob ein Spiel korrekt gespeichert wird und anschliessend wieder
=======
 * ueberprueft, ob ein Spiel korrekt gespeichert wird und anschliessend wieder
>>>>>>> origin/master
 * ausgelesen werden kann.
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public class Speichern_und_Laden_eines_Spiels_ist_erfolgreich_Test {

    private Spiel spiel;
    private Spielfeld spielfeld;
    private Spielstein spielsteinStern;
    private Spielstein spielsteinAusschluss;

    @Before
    public void setUp() {
        spiel = new Spiel();
        spiel.initializeNewSpielfeld(10, 9);
        spielfeld = spiel.getSpielfeld();
        spielsteinStern = Stern.getInstance();
        spielsteinAusschluss = Ausschluss.getInstance();

        spiel.setSpielmodus(SpielmodusEnumeration.EDITIEREN);
        spielfeld.setSpielstein(0, 0, spielsteinStern);
        spiel.setSpielmodus(SpielmodusEnumeration.SPIELEN);
        spielfeld.setSpielstein(0, 0, spielsteinAusschluss);

    }

    @Test
    public void ein_gespeichertes_Spiel_speichert_seine_Attribute_mit()
            throws FileNotFoundException, IOException {
        spiel.save("testSpiel01");
        Spiel geladenesSpiel = Spiel.load("testSpiel01");

        assertThat(geladenesSpiel.getSpielmodus(), is(spiel.getSpielmodus()));
        assertThat(geladenesSpiel.getSpielfeld().getSchwierigkeitsgrad(),
                is(spielfeld.getSchwierigkeitsgrad()));
        geladenesSpiel.setSpielmodus(SpielmodusEnumeration.EDITIEREN);
        assertThat(geladenesSpiel.getSpielfeld().getSpielstein(0, 0),
                instanceOf(spielsteinStern.getClass()));
        geladenesSpiel.setSpielmodus(SpielmodusEnumeration.SPIELEN);
        assertThat(geladenesSpiel.getSpielfeld().getSpielstein(0, 0),
                instanceOf(spielsteinAusschluss.getClass()));
    }

    @Test
    public void ein_nicht_vorhandenes_Spiel_kann_nicht_geladen_werden_und_gibt_NULL_zurueck()
            throws FileNotFoundException, IOException {
        IOException ioEx = null;
        try {
            Spiel geladenesSpiel = Spiel.load("testSpiel02");
        } catch (IOException e) {
            ioEx = e;
        }
        assertThat(ioEx, instanceOf(IOException.class));
    }

    @After
    public void tearDown() {
        File file = new File("testSpiel01"
                + GlobaleKonstanten.SPIELSTAND_DATEITYP);
        boolean success = true;
        if (file.exists()) {
            success = file.delete();
        }
        if (!success) {
            System.out.println("Fehler beim tearDown des Tests.");
        }
    }
}
