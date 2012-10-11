package severeLobster.backend.spiel;

import infrastructure.constants.enums.PfeilrichtungEnumeration;
import infrastructure.constants.enums.SpielmodusEnumeration;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Dieser Unittest erstellt ein Standardpuzzle, welches spielbar ist und eine eindeutige Loesung hat.
 *
 * @author Lars Schlegelmilch
 */
public class Erstellen_des_Standardpuzzles_Test {

    private Spielfeld spielfeld = new Spielfeld(6, 6);
    private Spiel spiel;

    private void spielsteineSetzen() {
        Spielstein stern1 = new Spielstein();
        stern1.setRealState(new Stern());
        spielfeld.setSpielstein(0,1, stern1);

        Spielstein pfeilRunter1 = new Spielstein();
        pfeilRunter1.setRealState(new Pfeil(PfeilrichtungEnumeration.SUED));
        pfeilRunter1.setVisibleState(new Pfeil(PfeilrichtungEnumeration.SUED));
        spielfeld.setSpielstein(0, 2, pfeilRunter1);

        Spielstein pfeilOst1 = new Spielstein();
        pfeilOst1.setRealState(new Pfeil(PfeilrichtungEnumeration.OST));
        pfeilOst1.setVisibleState(new Pfeil(PfeilrichtungEnumeration.OST));
        spielfeld.setSpielstein(0, 4, pfeilOst1);

        Spielstein pfeilOst2 = new Spielstein();
        pfeilOst2.setRealState(new Pfeil(PfeilrichtungEnumeration.OST));
        pfeilOst2.setVisibleState(new Pfeil(PfeilrichtungEnumeration.OST));
        spielfeld.setSpielstein(0, 5, pfeilOst2);

        Spielstein pfeilNordOst1 = new Spielstein();
        pfeilNordOst1.setRealState(new Pfeil(PfeilrichtungEnumeration.NORDOST));
        pfeilNordOst1.setVisibleState(new Pfeil(PfeilrichtungEnumeration.NORDOST));
        spielfeld.setSpielstein(1, 0, pfeilNordOst1);

        Spielstein stern2 = new Spielstein();
        stern2.setRealState(new Stern());
        spielfeld.setSpielstein(1, 2, stern2);

        Spielstein stern3 = new Spielstein();
        stern3.setRealState(new Stern());
        spielfeld.setSpielstein(1, 3, stern3);

        Spielstein stern4 = new Spielstein();
        stern4.setRealState(new Stern());
        spielfeld.setSpielstein(1, 4, stern4);

        Spielstein stern5 = new Spielstein();
        stern5.setRealState(new Stern());
        spielfeld.setSpielstein(1, 5, stern5);

        Spielstein stern6 = new Spielstein();
        stern6.setRealState(new Stern());
        spielfeld.setSpielstein(2, 0, stern6);

        Spielstein pfeilOst3 = new Spielstein();
        pfeilOst3.setRealState(new Pfeil(PfeilrichtungEnumeration.OST));
        pfeilOst3.setVisibleState(new Pfeil(PfeilrichtungEnumeration.OST));
        spielfeld.setSpielstein(2, 1, pfeilOst3);

        Spielstein pfeilNordWest1 = new Spielstein();
        pfeilNordWest1.setRealState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
        pfeilNordWest1.setVisibleState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
        spielfeld.setSpielstein(2, 2, pfeilNordWest1);


        Spielstein pfeilNordWest2 = new Spielstein();
        pfeilNordWest2.setRealState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
        pfeilNordWest2.setVisibleState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
        spielfeld.setSpielstein(3, 0, pfeilNordWest2);

        Spielstein stern7 = new Spielstein();
        stern7.setRealState(new Stern());
        spielfeld.setSpielstein(3, 1, stern7);

        Spielstein stern8 = new Spielstein();
        stern8.setRealState(new Stern());
        spielfeld.setSpielstein(3, 2, stern8);

        Spielstein pfeilNordWest3 = new Spielstein();
        pfeilNordWest3.setRealState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
        pfeilNordWest3.setVisibleState(new Pfeil(PfeilrichtungEnumeration.NORDWEST));
        spielfeld.setSpielstein(4, 1, pfeilNordWest3);

        Spielstein stern9 = new Spielstein();
        stern9.setRealState(new Stern());
        spielfeld.setSpielstein(4, 5, stern9);

        Spielstein stern10 = new Spielstein();
        stern10.setRealState(new Stern());
        spielfeld.setSpielstein(5, 1, stern10);

        Spielstein pfeilSuedWest1 = new Spielstein();
        pfeilSuedWest1.setRealState(new Pfeil(PfeilrichtungEnumeration.SUEDWEST));
        pfeilSuedWest1.setVisibleState(new Pfeil(PfeilrichtungEnumeration.SUEDWEST));
        spielfeld.setSpielstein(5, 3, pfeilSuedWest1);

        Spielstein pfeilSued1 = new Spielstein();
        pfeilSued1.setRealState(new Pfeil(PfeilrichtungEnumeration.SUED));
        pfeilSued1.setVisibleState(new Pfeil(PfeilrichtungEnumeration.SUED));
        spielfeld.setSpielstein(5, 5, pfeilSued1);
    }

    @Before
    public void setUp() {
        spielsteineSetzen();
        spiel = new Spiel(spielfeld, SpielmodusEnumeration.SPIELEN);
    }

    @Test
    public void sichern_des_standardspiels_ist_erfolgreich() {
        spiel.save("Standardspiel01");
        File file = new File("Standardspiel01.sav");

        assertTrue(file.exists());
    }
}
