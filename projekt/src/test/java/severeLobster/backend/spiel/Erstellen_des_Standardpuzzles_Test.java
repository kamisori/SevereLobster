package severeLobster.backend.spiel;

import infrastructure.constants.GlobaleKonstanten;
import infrastructure.constants.enums.SpielmodusEnumeration;
import infrastructure.exceptions.SpielNichtLoeschbarException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static infrastructure.constants.enums.PfeilrichtungEnumeration.NORDOST;
import static infrastructure.constants.enums.PfeilrichtungEnumeration.NORDWEST;
import static infrastructure.constants.enums.PfeilrichtungEnumeration.OST;
import static infrastructure.constants.enums.PfeilrichtungEnumeration.SUED;
import static infrastructure.constants.enums.PfeilrichtungEnumeration.SUEDWEST;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static severeLobster.backend.spiel.helper.matchers.PfeilMatcher.pfeil;
import static severeLobster.backend.spiel.helper.matchers.SpielMatcher.spiel;

/**
 * Dieser Unittest erstellt ein Standardpuzzle, welches spielbar ist und eine
 * eindeutige Loesung hat.
 *
 * @author Lars Schlegelmilch, Lutz Kleiber
 */
public class Erstellen_des_Standardpuzzles_Test {

    private Spiel standardspiel;

    private Spielfeld spielfeld;

    private void spielsteineSetzen() {
        spielfeld.setSpielstein(0, 4, Stern.getInstance());

        spielfeld.setSpielstein(0, 3, Pfeil.getSuedPfeil());

        spielfeld.setSpielstein(0, 1, Pfeil.getOstPfeil());

        spielfeld.setSpielstein(0, 0, Pfeil.getOstPfeil());

        spielfeld.setSpielstein(1, 5, Pfeil.getNordOstPfeil());

        spielfeld.setSpielstein(1, 3, Stern.getInstance());

        spielfeld.setSpielstein(1, 2, Stern.getInstance());

        spielfeld.setSpielstein(1, 1, Stern.getInstance());

        spielfeld.setSpielstein(1, 0, Stern.getInstance());

        spielfeld.setSpielstein(2, 5, Stern.getInstance());

        spielfeld.setSpielstein(2, 4, Pfeil.getOstPfeil());

        spielfeld.setSpielstein(2, 3, Pfeil.getNordWestPfeil());

        spielfeld.setSpielstein(3, 5, Pfeil.getNordWestPfeil());

        spielfeld.setSpielstein(3, 4, Stern.getInstance());

        spielfeld.setSpielstein(3, 3, Stern.getInstance());

        spielfeld.setSpielstein(4, 4, Pfeil.getNordWestPfeil());

        spielfeld.setSpielstein(4, 0, Stern.getInstance());

        spielfeld.setSpielstein(5, 4, Stern.getInstance());

        spielfeld.setSpielstein(5, 2, Pfeil.getSuedWestPfeil());

        spielfeld.setSpielstein(5, 0, Pfeil.getSuedPfeil());
    }

    @Before
    public void setUp() {
        standardspiel = new Spiel(SpielmodusEnumeration.EDITIEREN);
        standardspiel.initializeNewSpielfeld(6, 6);
        spielfeld = standardspiel.getSpielfeld();
        spielsteineSetzen();

    }

    @Test
    public void sichern_des_editierten_standardspiels_ist_erfolgreich()
            throws IOException {
        File file = new File("Standardspiel01"
                + GlobaleKonstanten.PUZZLE_ERSTELLEN_DATEITYP);

        boolean success;
        if (file.exists()) {
            success = file.delete();
            if (!success) {
                throw new SpielNichtLoeschbarException();
            }
        }
        standardspiel.save("Standardspiel01");
        file = new File("Standardspiel01"
                + GlobaleKonstanten.PUZZLE_ERSTELLEN_DATEITYP);
        assertTrue(file.exists());
    }

    @Test
    public void das_gesicherte_spiel_ist_das_standardpuzzle_im_editiermodus() throws IOException {
        Spiel geladenesSpiel = Spiel.load("Standardspiel01", SpielmodusEnumeration.EDITIEREN);
        Spielfeld geladenesSpielfeld = geladenesSpiel.getSpielfeld();

        assertThat(geladenesSpiel,
                spiel(equalTo(SpielmodusEnumeration.EDITIEREN),
                        equalTo(standardspiel.getSchwierigkeitsgrad())));
        assertThat(geladenesSpielfeld.getSpielstein(0, 5), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(0, 4), instanceOf(Stern.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(0, 3), pfeil(equalTo(SUED)));
        assertThat(geladenesSpielfeld.getSpielstein(0, 2), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(0, 1), pfeil(equalTo(OST)));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(0, 0), pfeil(equalTo(OST)));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(1, 5), pfeil(equalTo(NORDOST)));
        assertThat(geladenesSpielfeld.getSpielstein(1, 4), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(1, 3), instanceOf(Stern.class));
        assertThat(geladenesSpielfeld.getSpielstein(1, 2), instanceOf(Stern.class));
        assertThat(geladenesSpielfeld.getSpielstein(1, 1), instanceOf(Stern.class));
        assertThat(geladenesSpielfeld.getSpielstein(1, 0), instanceOf(Stern.class));
        assertThat(geladenesSpielfeld.getSpielstein(2, 5), instanceOf(Stern.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(2, 4), pfeil(equalTo(OST)));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(2, 3), pfeil(equalTo(NORDWEST)));
        assertThat(geladenesSpielfeld.getSpielstein(2, 2), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(2, 1), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(2, 0), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(3, 5), pfeil(equalTo(NORDWEST)));
        assertThat(geladenesSpielfeld.getSpielstein(3, 4), instanceOf(Stern.class));
        assertThat(geladenesSpielfeld.getSpielstein(3, 3), instanceOf(Stern.class));
        assertThat(geladenesSpielfeld.getSpielstein(3, 2), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(3, 1), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(3, 0), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(4, 5), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(4, 4), pfeil(equalTo(NORDWEST)));
        assertThat(geladenesSpielfeld.getSpielstein(4, 3), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(4, 2), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(4, 1), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(4, 0), instanceOf(Stern.class));
        assertThat(geladenesSpielfeld.getSpielstein(5, 5), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(5, 4), instanceOf(Stern.class));
        assertThat(geladenesSpielfeld.getSpielstein(5, 3), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(5, 2), pfeil(equalTo(SUEDWEST)));
        assertThat(geladenesSpielfeld.getSpielstein(5, 1), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(5, 0), pfeil(equalTo(SUED)));
    }

    @Test
    public void das_erstellte_standardspiel_kann_zum_spielen_freigegeben_werden() throws IOException {
        Spiel geladenesSpiel = Spiel.load("Standardspiel01", SpielmodusEnumeration.EDITIEREN);
        geladenesSpiel.setSpielmodus(SpielmodusEnumeration.SPIELEN);
        geladenesSpiel.save("Standardspiel01");

        File file = new File("Standardspiel01"
                + GlobaleKonstanten.SPIELSTAND_DATEITYP);
        assertTrue(file.exists());
    }

    @Test
    public void das_gesicherte_spiel_ist_das_standardpuzzle_im_spielmodus() throws IOException {
        Spiel geladenesSpiel = Spiel.load("Standardspiel01", SpielmodusEnumeration.SPIELEN);
        Spielfeld geladenesSpielfeld = geladenesSpiel.getSpielfeld();
        assertThat(geladenesSpiel,
                spiel(equalTo(SpielmodusEnumeration.SPIELEN),
                        equalTo(standardspiel.getSchwierigkeitsgrad())));
        assertThat(geladenesSpielfeld.getSpielstein(0, 5), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(0, 4), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(0, 3), pfeil(equalTo(SUED)));
        assertThat(geladenesSpielfeld.getSpielstein(0, 2), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(0, 1), pfeil(equalTo(OST)));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(0, 0), pfeil(equalTo(OST)));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(1, 5), pfeil(equalTo(NORDOST)));
        assertThat(geladenesSpielfeld.getSpielstein(1, 4), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(1, 3), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(1, 2), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(1, 1), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(1, 0), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(2, 5), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(2, 4), pfeil(equalTo(OST)));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(2, 3), pfeil(equalTo(NORDWEST)));
        assertThat(geladenesSpielfeld.getSpielstein(2, 2), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(2, 1), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(2, 0), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(3, 5), pfeil(equalTo(NORDWEST)));
        assertThat(geladenesSpielfeld.getSpielstein(3, 4), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(3, 3), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(3, 2), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(3, 1), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(3, 0), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(4, 5), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(4, 4), pfeil(equalTo(NORDWEST)));
        assertThat(geladenesSpielfeld.getSpielstein(4, 3), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(4, 2), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(4, 1), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(4, 0), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(5, 5), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(5, 4), instanceOf(KeinStein.class));
        assertThat(geladenesSpielfeld.getSpielstein(5, 3), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(5, 2), pfeil(equalTo(SUEDWEST)));
        assertThat(geladenesSpielfeld.getSpielstein(5, 1), instanceOf(KeinStein.class));
        assertThat((Pfeil) geladenesSpielfeld.getSpielstein(5, 0), pfeil(equalTo(SUED)));
    }
}
