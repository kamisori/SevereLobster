package severeLobster.backend.spiel;

import infrastructure.constants.enums.SpielmodusEnumeration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Christian Lobach
 */
public class Loesungs_Strategien_Test implements IGotSpielModus {

    private Spiel testSpiel;
    private Spielfeld spielfeld;

    @Before
    public void setUp() throws Exception {
        testSpiel = new Spiel(SpielmodusEnumeration.EDITIEREN);
        testSpiel.initializeNewSpielfeld(4, 4);
        spielfeld = testSpiel.getSpielfeld();
    }

    /**
     * Testet SolvingStepPossibleStars, welche mögliche Sterne als solche markieren soll
     */
    @Test
    public void moegliche_Sterne_markieren() {

        spielfeld.setSpielstein(1, 0, Pfeil.getOstPfeil()); // Pfeil 1
        spielfeld.setSpielstein(0, 1, Pfeil.getSuedPfeil()); // Pfeil 2
        spielfeld.setSpielstein(3, 1, Pfeil.getSuedWestPfeil()); // Pfeil 3

        SolvingStep step = new SolvingStepPossibleStars();
        spielfeld.setGotSpielModus(this);

        spielfeld = step.execute(spielfeld);

        // Wegen Pfeil 1
        assertEquals(KeinStein.getInstance(), spielfeld.getSpielstein(0, 0));
        assertEquals(Pfeil.getOstPfeil(), spielfeld.getSpielstein(1, 0));
        assertEquals(MoeglicherStern.getInstance(), spielfeld.getSpielstein(2, 0));
        assertEquals(MoeglicherStern.getInstance(), spielfeld.getSpielstein(3, 0));


        // Wegen Pfeil 2
        assertEquals(Pfeil.getSuedPfeil(), spielfeld.getSpielstein(0, 1));
        assertEquals(MoeglicherStern.getInstance(), spielfeld.getSpielstein(0, 2));
        assertEquals(MoeglicherStern.getInstance(), spielfeld.getSpielstein(0, 3));

        // Wegen Pfeil 3
        assertEquals(Pfeil.getSuedWestPfeil(), spielfeld.getSpielstein(3, 1));
        assertEquals(MoeglicherStern.getInstance(), spielfeld.getSpielstein(2, 2));
        assertEquals(MoeglicherStern.getInstance(), spielfeld.getSpielstein(1, 3));

    }

    /**
     * Testet SolvingStepExcludeImpossibles
     */
    @Test
    public void ausschluss_setzen_wo_kein_stern_sein_kann() {

        // Pfeile im Editiermodus setzen
        spielfeld.setSpielstein(1, 0, Pfeil.getOstPfeil());
        spielfeld.setSpielstein(0, 1, Pfeil.getSuedPfeil());
        spielfeld.setSpielstein(3, 1, Pfeil.getSuedWestPfeil());

        // Mögliche Sterne händisch im Lösungsmodus setzen
        spielfeld.setGotSpielModus(this);

        spielfeld.setSpielstein(2, 0, MoeglicherStern.getInstance());
        spielfeld.setSpielstein(3, 0, MoeglicherStern.getInstance());

        spielfeld.setSpielstein(0, 2, MoeglicherStern.getInstance());
        spielfeld.setSpielstein(2, 2, MoeglicherStern.getInstance());

        spielfeld.setSpielstein(0, 3, MoeglicherStern.getInstance());
        spielfeld.setSpielstein(1, 3, MoeglicherStern.getInstance());


        SolvingStep step = new SolvingStepExcludeImpossibles();
        spielfeld = step.execute(spielfeld);

        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(0, 0));

        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(1, 1));
        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(2, 1));

        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(1, 2));
        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(3, 2));

        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(2, 3));
        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(3, 3));

    }

    @Test
    public void null_Spalten_ausschliessen() {
        // Pfeile im Editiermodus setzen
        spielfeld.setSpielstein(1, 0, Pfeil.getOstPfeil());
        spielfeld.setSpielstein(0, 1, Pfeil.getSuedPfeil());
        spielfeld.setSpielstein(3, 1, Pfeil.getSuedWestPfeil());

        // GotSpielmodus setzen, damit Spielmodus LOESEN ist
        spielfeld.setGotSpielModus(this);

        // Sterne setzen
        spielfeld.setSpielstein(2,0, Stern.getInstance());
        spielfeld.setSpielstein(0,2, Stern.getInstance());
        spielfeld.setSpielstein(2,2, Stern.getInstance());

        SolvingStep step = new SolvingStepCheckZeroColumns();
        spielfeld = step.execute(spielfeld);

        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(1,1));
        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(1,2));
        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(1,3));

        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(3,0));
        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(3,2));
        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(3,3));

    }


    @Test
    public void null_Zeilen_ausschliessen() {
        // Pfeile im Editiermodus setzen
        spielfeld.setSpielstein(1, 0, Pfeil.getOstPfeil());
        spielfeld.setSpielstein(0, 1, Pfeil.getSuedPfeil());
        spielfeld.setSpielstein(3, 1, Pfeil.getSuedWestPfeil());

        // GotSpielmodus setzen, damit Spielmodus LOESEN ist
        spielfeld.setGotSpielModus(this);

        // Sterne setzen
        spielfeld.setSpielstein(2,0, Stern.getInstance());
        spielfeld.setSpielstein(0,2, Stern.getInstance());
        spielfeld.setSpielstein(2,2, Stern.getInstance());

        SolvingStep step = new SolvingStepCheckZeroRows();
        spielfeld = step.execute(spielfeld);

        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(1,1));
        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(2,1));

        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(0,3));
        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(1,3));
        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(2,3));
        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(3,3));
    }

    @Test
    public void ein_Stern_ein_Pfeil_in_Spalte(){
        // Pfeile im Editiermodus setzen
        spielfeld.setSpielstein(1, 0, Pfeil.getOstPfeil());
        spielfeld.setSpielstein(0, 1, Pfeil.getSuedPfeil());
        spielfeld.setSpielstein(3, 1, Pfeil.getSuedWestPfeil());

        // GotSpielmodus setzen, damit Spielmodus LOESEN ist
        spielfeld.setGotSpielModus(this);

        // Sterne setzen
        spielfeld.setSpielstein(2,0, Stern.getInstance());
        spielfeld.setSpielstein(0,2, Stern.getInstance());
        spielfeld.setSpielstein(2,2, Stern.getInstance());

        SolvingStep step = new SolvingStepExcludeBehindInColumn();
        spielfeld = step.execute(spielfeld);

        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(0,0));

    }

    @Test
    public void ein_Stern_ein_Pfeil_in_Zeile(){
        // Pfeile im Editiermodus setzen
        spielfeld.setSpielstein(1, 0, Pfeil.getOstPfeil());
        spielfeld.setSpielstein(0, 1, Pfeil.getSuedPfeil());
        spielfeld.setSpielstein(3, 1, Pfeil.getSuedWestPfeil());

        // GotSpielmodus setzen, damit Spielmodus LOESEN ist
        spielfeld.setGotSpielModus(this);

        // Sterne setzen
        spielfeld.setSpielstein(2,0, Stern.getInstance());
        spielfeld.setSpielstein(0,2, Stern.getInstance());
        spielfeld.setSpielstein(2,2, Stern.getInstance());

        SolvingStep step = new SolvingStepExcludeBehindInRow();
        spielfeld = step.execute(spielfeld);

        assertEquals(Ausschluss.getInstance(), spielfeld.getSpielstein(0,0));

    }


    @Override
    public SpielmodusEnumeration getSpielmodus() {
        return SpielmodusEnumeration.LOESEN;
    }
}
