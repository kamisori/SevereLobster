package severeLobster.backend.spiel;

import infrastructure.constants.GlobaleKonstanten;
import infrastructure.constants.enums.SpielmodusEnumeration;
import infrastructure.exceptions.SpielNichtLoeschbarException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Ueberprueft, ob ein Spiel korrekt gespeichert wird und anschliessend wieder
 * ausgelesen werden kann.
 * 
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public class Speichern_und_Laden_eines_Spiels_ist_erfolgreich_Test {

    private Spiel spiel;
    private Spielfeld spielfeld;

    private Spiel erstelles_puzzle;
    private Spielfeld erstelltes_spielfeld;

    private final Spielstein spielsteinStern = Stern.getInstance();
    private final Spielstein spielsteinAusschluss = Ausschluss.getInstance();
    private final Spielstein spielsteinKeinStern = KeinStein.getInstance();

    @Before
    public void setUp() {
        spiel = new Spiel(SpielmodusEnumeration.SPIELEN);
        spiel.initializeNewSpielfeld(10, 9);
        spielfeld = spiel.getSpielfeld();

        spielfeld.setSpielstein(0, 0, spielsteinStern);
        spielfeld.setSpielstein(0, 1, spielsteinAusschluss);

        erstelles_puzzle = new Spiel(SpielmodusEnumeration.EDITIEREN);
        erstelles_puzzle.initializeNewSpielfeld(10, 9);
        erstelltes_spielfeld = erstelles_puzzle.getSpielfeld();
        erstelltes_spielfeld.setSpielstein(0, 0, spielsteinStern);
        erstelltes_spielfeld.setSpielstein(0, 1, spielsteinKeinStern);

    }

    @Test
    public void ein_gespeichertes_Spiel_speichert_seine_Attribute_mit()
            throws IOException {
        spiel.save("testSpiel01");
        Spiel geladenesSpiel = Spiel.load("testSpiel01", SpielmodusEnumeration.SPIELEN);

        assertThat(geladenesSpiel.getSpielmodus(),
                is(SpielmodusEnumeration.SPIELEN));
        assertThat(geladenesSpiel.getSpielfeld().getSchwierigkeitsgrad(),
                is(spielfeld.getSchwierigkeitsgrad()));
        assertThat(geladenesSpiel.getSpielfeld().getSpielstein(0, 0),
                instanceOf(spielsteinStern.getClass()));
        assertThat(geladenesSpiel.getSpielfeld().getSpielstein(0, 1),
                instanceOf(spielsteinAusschluss.getClass()));
    }


    @Test
    public void ein_gespeichertes_Puzzle_speichert_seine_Attribute_mit()
            throws IOException {
        erstelles_puzzle.save("testSpiel01");
        Spiel geladenesSpiel = Spiel.load("testSpiel01", SpielmodusEnumeration.EDITIEREN);

        assertThat(geladenesSpiel.getSpielmodus(),
                is(SpielmodusEnumeration.EDITIEREN));
        assertThat(geladenesSpiel.getSpielfeld().getSchwierigkeitsgrad(),
                is(erstelltes_spielfeld.getSchwierigkeitsgrad()));
        assertThat(geladenesSpiel.getSpielfeld().getSpielstein(0, 0),
                instanceOf(spielsteinStern.getClass()));
        assertThat(geladenesSpiel.getSpielfeld().getSpielstein(0, 1),
                instanceOf(spielsteinKeinStern.getClass()));
    }

    @Test
    (expected = IOException.class)
    public void ein_nicht_vorhandenes_Spiel_kann_nicht_geladen_werden_und_gibt_NULL_zurueck() throws IOException {
        Spiel.load("testSpiel02", SpielmodusEnumeration.SPIELEN);
    }

    @Test
    (expected = IOException.class)
    public void ein_nicht_vorhandenes_Puzzle_kann_nicht_geladen_werden_und_gibt_NULL_zurueck() throws IOException {
        Spiel.load("testSpiel02", SpielmodusEnumeration.EDITIEREN);
    }

    @After
    public void tearDown() throws SpielNichtLoeschbarException {
        boolean success = true;

        File spieldatei = new File("testSpiel01"
                + GlobaleKonstanten.SPIELSTAND_DATEITYP);
        File puzzledatei = new File("testSpiel01"
                + GlobaleKonstanten.PUZZLE_ERSTELLEN_DATEITYP);

        if (spieldatei.exists()) {
            success = spieldatei.delete();
        }
        if (puzzledatei.exists()) {
            success = puzzledatei.delete();
        }
        if (!success) {
            throw new SpielNichtLoeschbarException();
        }
    }
}
