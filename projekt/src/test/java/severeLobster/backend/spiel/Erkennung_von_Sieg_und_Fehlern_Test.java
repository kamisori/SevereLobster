package severeLobster.backend.spiel;

import infrastructure.constants.enums.SpielmodusEnumeration;
import infrastructure.exceptions.LoesungswegNichtEindeutigException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 
 * @author Christian Lobach, Lutz Kleiber
 * 
 */
public class Erkennung_von_Sieg_und_Fehlern_Test {

    private Spiel testSpiel;
    private Spielfeld spielfeld;

    @Before
    public void setUp() throws Exception {
        testSpiel = new Spiel();
        testSpiel.setSpielmodus(SpielmodusEnumeration.SPIELEN);
        testSpiel.initializeNewSpielfeld(4, 4);
        spielfeld = testSpiel.getSpielfeld();
    }

    /**
     * Prueft ob ein Spielfeld geloest wurde, bei dem das Getippte exakt dem
     * realen Spielfeld entspricht
     * 
     * @throws LoesungswegNichtEindeutigException
     */
    @Test
    public void erfolgreich_beendetes_spiel_erkennen()
            throws LoesungswegNichtEindeutigException {

        // Stern da, Stern getippt
        switchToEditor();
        spielfeld.setSpielstein(0, 0, Stern.getInstance());
        // kein Stern, nichts getippt
        spielfeld.setSpielstein(0, 1, KeinStein.getInstance());
        // kein Stern, getippt dass dort keiner ist
        spielfeld.setSpielstein(1, 0, KeinStein.getInstance());
        // Pfeil, Pfeil muss auch angezeigt werden
        spielfeld.setSpielstein(1, 1, Pfeil.getNordWestPfeil());

        switchToSpielen();
        spielfeld.setSpielstein(0, 0, Stern.getInstance());
        spielfeld.setSpielstein(0, 1, KeinStein.getInstance());
        spielfeld.setSpielstein(1, 0, Ausschluss.getInstance());
        spielfeld.setSpielstein(1, 1, Pfeil.getNordWestPfeil());

        assertTrue(testSpiel.isSolved());

    }

    private void switchToSpielen() throws LoesungswegNichtEindeutigException {
        this.testSpiel.setSpielmodus(SpielmodusEnumeration.SPIELEN);
    }

    private void switchToEditor() throws LoesungswegNichtEindeutigException {
        this.testSpiel.setSpielmodus(SpielmodusEnumeration.EDITIEREN);
    }

    /**
     * Prueft ob ein Spielfeld geloest wurde. Hier wurde explizit ein Ausschluss
     * verwendet.
     * 
     * @throws LoesungswegNichtEindeutigException
     */
    @Test
    public void erfolgreich_beendetes_spiel_mit_ausschluss_erkennen() throws LoesungswegNichtEindeutigException {

        // Stern da, Stern getippt
        switchToEditor();
        spielfeld.setSpielstein(0, 0, Stern.getInstance());
        // kein Stern, nichts getippt
        switchToEditor();
        spielfeld.setSpielstein(0, 1, KeinStein.getInstance());
        // kein Stern, getippt dass dort keiner ist
        switchToEditor();
        spielfeld.setSpielstein(1, 0, KeinStein.getInstance());
        // Pfeil, Pfeil muss auch angezeigt werden
        switchToEditor();
        spielfeld.setSpielstein(1, 1, Pfeil.getNordWestPfeil());

        switchToSpielen();
        spielfeld.setSpielstein(0, 0, Stern.getInstance());
        spielfeld.setSpielstein(0, 1, KeinStein.getInstance());
        spielfeld.setSpielstein(1, 0, Ausschluss.getInstance());
        spielfeld.setSpielstein(1, 1, Pfeil.getNordWestPfeil());

        assertTrue(testSpiel.isSolved());

    }

    /**
     * Prueft ob Fehler vorliegen. Hier wurde ein Stern noch nicht gefunden. Die
     * Methode muss false zurueckgeben, da noch kein Fehler vorliegt.
     * @throws LoesungswegNichtEindeutigException 
     */
    @Test
    public void stern_noch_nicht_getippt_erkennen() throws LoesungswegNichtEindeutigException {

        // Stern da, kein Stern getippt
        // (hier darf kein Fehler auftreten, da noch nicht ausgeschlossen wurde,
        // dass hier ein Stern liegt)
        switchToEditor();
        spielfeld.setSpielstein(0, 0, Stern.getInstance());
        // kein Stern, nichts getippt
        spielfeld.setSpielstein(0, 1, KeinStein.getInstance());
        // kein Stern, getippt dass dort keiner ist
        spielfeld.setSpielstein(1, 0, KeinStein.getInstance());
        // Pfeil, Pfeil muss auch angezeigt werden
        spielfeld.setSpielstein(1, 1, Pfeil.getNordWestPfeil());

        switchToSpielen();
        spielfeld.setSpielstein(0, 0, KeinStein.getInstance());
        spielfeld.setSpielstein(0, 1, KeinStein.getInstance());
        spielfeld.setSpielstein(1, 0, Ausschluss.getInstance());
        spielfeld.setSpielstein(1, 1, Pfeil.getNordWestPfeil());

        assertFalse(testSpiel.hasErrors());

    }

    /**
     * Prueft ob Fehler vorliegen. Hier wurde ein Stern getippt, wo keiner ist.
     * @throws LoesungswegNichtEindeutigException 
     */
    @Test
    public void stern_zuviel_erkennen() throws LoesungswegNichtEindeutigException {

        // Stern da, Stern getippt
        switchToEditor();
        spielfeld.setSpielstein(0, 0, Stern.getInstance());
        // kein Stern, Stern getippt (hier tritt der Fehler auf)
        spielfeld.setSpielstein(0, 1, KeinStein.getInstance());
        // kein Stern, getippt dass dort keiner ist
        spielfeld.setSpielstein(1, 0, KeinStein.getInstance());
        // Pfeil, Pfeil muss auch angezeigt werden
        spielfeld.setSpielstein(1, 1, Pfeil.getNordWestPfeil());

        switchToSpielen();
        spielfeld.setSpielstein(0, 0, Stern.getInstance());
        spielfeld.setSpielstein(0, 1, Stern.getInstance());
        spielfeld.setSpielstein(1, 0, Ausschluss.getInstance());
        spielfeld.setSpielstein(1, 1, Pfeil.getNordWestPfeil());

        assertTrue(testSpiel.hasErrors());

    }

}
