package severeLobster.backend.spiel.test;

import infrastructure.constants.enums.SpielmodusEnumeration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import severeLobster.backend.spiel.Ausschluss;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.Spielfeld;
import severeLobster.backend.spiel.Spielstein;
import severeLobster.backend.spiel.SpielsteinState;
import severeLobster.backend.spiel.Stern;

import java.io.File;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Überprüft, ob ein Spiel korrekt gespeichert wird und anschließend wieder ausgelesen werden kann.
 *
 * @author Lars Schlegelmilch
 */
public class Speichern_und_Laden_eines_Spiels_ist_erfolgreich_Test {

    private Spiel spiel;
    private Spielfeld spielfeld;
    private Spielstein spielstein;
    private SpielsteinState spielsteinStateStern;
    private SpielsteinState spielsteinStateAusschluss;

    @Before
    public void setUp() {
        spielsteinStateStern = new Stern();
        spielsteinStateAusschluss = new Ausschluss();
        spielstein = new Spielstein();
        spielstein.setRealState(spielsteinStateStern);
        spielstein.setVisibleState(spielsteinStateAusschluss);
        spielfeld = new Spielfeld(10, 9);
        spielfeld.setSpielstein(0, 0, spielstein);
        spiel = new Spiel(spielfeld, SpielmodusEnumeration.SPIELEN);
    }

    @Test
    public void ein_gespeichertes_Spiel_speichert_seine_Attribute_mit() {
        spiel.save("testSpiel01");
        Spiel geladenesSpiel = Spiel.load("testSpiel01");

        assertThat(geladenesSpiel.getSpielmodus(), is(spiel.getSpielmodus()));
        assertThat(geladenesSpiel.getSpielfeld().getSchwierigkeitsgrad(), is(spielfeld.getSchwierigkeitsgrad()));
        assertThat(geladenesSpiel.getSpielfeld().getSpielstein(0, 0).getRealState(), instanceOf(spielstein.getRealState().getClass()));
        assertThat(geladenesSpiel.getSpielfeld().getSpielstein(0, 0).getVisibleState(), instanceOf(spielstein.getVisibleState().getClass()));
    }

    @Test
    public void ein_nicht_vorhandenes_Spiel_kann_nicht_geladen_werden_und_gibt_NULL_zurueck() {
        Spiel geladenesSpiel = Spiel.load("testSpiel02");

        assertThat(geladenesSpiel, nullValue());
    }

    @After
    public void tearDown() {
        File file = new File("testSpiel01.sav");
        boolean success = true;
        if (file.exists()) {
            success = file.delete();
        }
        if (!success) {
            System.out.println("Fehler beim tearDown des Tests.");
        }
    }
}
