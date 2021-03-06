package severeLobster.backend.spiel;

import infrastructure.constants.GlobaleKonstanten;
import infrastructure.constants.enums.SpielmodusEnumeration;
import infrastructure.exceptions.LoesungswegNichtEindeutigException;
import infrastructure.exceptions.SpielNichtLoeschbarException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Unit-Test, ob ein vorhandenes Puzzle gespielt werden kann
 * 
 * @author Lars Schlegelmilch
 */
public class Neues_Spiel_starten_Test {

    private Spiel erstelles_puzzle;
    private Spielfeld erstelltes_spielfeld;

    private final Spielstein spielsteinStern = Stern.getInstance();
    private final Spielstein spielsteinKeinStern = KeinStein.getInstance();
    private final Spielstein pfeil = Pfeil.getWestPfeil();

    @Before
    public void setUp() throws IOException, LoesungswegNichtEindeutigException {
        erstelles_puzzle = new Spiel();
        erstelles_puzzle.setSpielmodus(SpielmodusEnumeration.EDITIEREN);
        erstelles_puzzle.initializeNewSpielfeld(2, 2);
        erstelltes_spielfeld = erstelles_puzzle.getSpielfeld();
        erstelltes_spielfeld.setSpielstein(0, 1, spielsteinStern);
        erstelltes_spielfeld.setSpielstein(1, 1, pfeil);

        erstelles_puzzle.saveSpiel("neuesTestspiel01");
        erstelles_puzzle.gebeSpielFrei("neuesTestspiel01");
    }

    @Test
    public void ein_neues_spiel_befindet_sich_im_spielmodus()
            throws IOException, LoesungswegNichtEindeutigException {
        Spiel neuesSpiel = Spiel.newSpiel("neuesTestspiel01");
        assertTrue(neuesSpiel.getSpielmodus().equals(
                SpielmodusEnumeration.SPIELEN));
    }

    @After
    public void tearDown() throws SpielNichtLoeschbarException {
        boolean success = true;

        File puzzledatei = new File(GlobaleKonstanten.DEFAULT_PUZZLE_SAVE_DIR,
                "neuesTestspiel01" + "." + GlobaleKonstanten.PUZZLE_DATEITYP);
        if (puzzledatei.exists()) {
            success = puzzledatei.delete();
        }
        if (!success) {
            throw new SpielNichtLoeschbarException();
        }
    }
}
